package de.seipler.network.chat;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NioChatServer implements Runnable {
  
  // The host:port combination to listen on
  private InetAddress fHostAddress;
  private int fPort;

  // The channel on which we'll accept connections
  private ServerSocketChannel fServerChannel;

  // The selector we'll be monitoring
  private Selector fSelector;

  // The buffer into which we'll read data when it's available
  private ByteBuffer fReadBuffer;

  private NioChatWorker fWorker;

  // A list of PendingChange instances
  private List fPendingChanges;

  // Maps a SocketChannel to a list of ByteBuffer instances
  private Map fPendingData;
  
  private Map fAcceptedChannelsToUser;

  public NioChatServer(InetAddress hostAddress, int port, NioChatWorker pWorker) throws IOException {
    fHostAddress = hostAddress;
    fPort = port;
    fWorker = pWorker;
    fReadBuffer = ByteBuffer.allocate(8192);
    fPendingChanges = Collections.synchronizedList(new LinkedList());
    fPendingData = Collections.synchronizedMap(new HashMap());
    fAcceptedChannelsToUser = Collections.synchronizedMap(new HashMap());
    fSelector = initSelector();
  }
  
  public void send(SocketChannel socket, byte[] data) {
    
    // And queue the data we want written
    synchronized (fPendingData) {
      List queue = (List) fPendingData.get(socket);
      if (queue == null) {
        queue = new ArrayList();
        fPendingData.put(socket, queue);
      }
      queue.add(ByteBuffer.wrap(data));
    }

    // Indicate we want the interest ops set changed
    addChangeRequest(new ChangeRequest(socket, ChangeRequest.CHANGEOPS, SelectionKey.OP_WRITE));
    
  }

  public void run() {
    
    while (true) {
      
      try {
        
        synchronized (fPendingChanges) {
          if (!fPendingChanges.isEmpty()) {
            ChangeRequest change = (ChangeRequest) fPendingChanges.remove(0);
            switch (change.type) {
              case ChangeRequest.CHANGEOPS:
                SelectionKey key = change.socket.keyFor(fSelector);
                key.interestOps(change.ops);
                break;
            }
          }
        }

        // Wait for an event one of the registered channels
        fSelector.select();

        // Iterate over the set of keys for which events are available
        Iterator selectedKeys = fSelector.selectedKeys().iterator();
        while (selectedKeys.hasNext()) {
          SelectionKey key = (SelectionKey) selectedKeys.next();
          selectedKeys.remove();

          if (!key.isValid()) {
            continue;
          }

          // Check what event is available and deal with it
          if (key.isAcceptable()) {
            this.accept(key);
          } else if (key.isReadable()) {
            this.read(key);
          } else if (key.isWritable()) {
            this.write(key);
          }
        }
      
      } catch (Exception e) {
        e.printStackTrace();
      }
      
    }
    
  }

  private void accept(SelectionKey key) throws IOException {
    
    // For an accept to be pending the channel must be a server socket channel.
    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();

    // Accept the connection and make it non-blocking
    SocketChannel socketChannel = serverSocketChannel.accept();
    // Socket socket = socketChannel.socket();
    socketChannel.configureBlocking(false);

    // Register the new SocketChannel with our Selector, indicating
    // we'd like to be notified when there's data waiting to be read
    socketChannel.register(fSelector, SelectionKey.OP_READ);
    
  }

  private void read(SelectionKey key) throws IOException {

    SocketChannel socketChannel = (SocketChannel) key.channel();

    // Clear out our read buffer so it's ready for new data
    fReadBuffer.clear();

    // Attempt to read off the channel
    int numRead;
    try {
      numRead = socketChannel.read(fReadBuffer);
    } catch (IOException e) {
      // The remote forcibly closed the connection, cancel
      // the selection key and close the channel.
      removeChannel(socketChannel);
      return;
    }

    if (numRead == -1) {
      // Remote entity shut the socket down cleanly. Do the
      // same from our end and cancel the channel.
      removeChannel(socketChannel);
      return;
    }

    byte[] bytes = new byte[numRead];
    System.arraycopy(fReadBuffer.array(), 0, bytes, 0, numRead);
    ChatCommand chatCommand = ChatCommandFactory.getInstance().fromByteArray(bytes);
    if (chatCommand.getType() == ChatCommand.JOIN) {
      fAcceptedChannelsToUser.put(socketChannel, chatCommand.getUser());
    }
    
    fWorker.processCommand(this, socketChannel, chatCommand);
    
  }
  
  public SocketChannel[] getAcceptedChannels() {
    synchronized (fAcceptedChannelsToUser) {
      Set acceptedChannels = fAcceptedChannelsToUser.keySet();
      return (SocketChannel[]) acceptedChannels.toArray(new SocketChannel[acceptedChannels.size()]);  
    }
  }
  
  public String getUserForChannel(SocketChannel pSocketChannel) {
    synchronized (fAcceptedChannelsToUser) {
      return (String) fAcceptedChannelsToUser.get(pSocketChannel);
    }
  }
  
  private void removeChannel(SocketChannel pSocketChannel) throws IOException {
    
    pSocketChannel.close();
    pSocketChannel.keyFor(fSelector).cancel();
    fSelector.wakeup();
    
    String user = (String) fAcceptedChannelsToUser.get(pSocketChannel);
    fAcceptedChannelsToUser.remove(pSocketChannel);
    if (user != null) {
      ChatCommandLeave leaveCommand = new ChatCommandLeave(user);
      fWorker.processCommand(this, pSocketChannel, leaveCommand);
    }
    
  }
  
  private void write(SelectionKey key) throws IOException {
    SocketChannel socketChannel = (SocketChannel) key.channel();

    synchronized (fPendingData) {
      List queue = (List) fPendingData.get(socketChannel);

      // Write until there's not more data ...
      while (!queue.isEmpty()) {
        ByteBuffer buf = (ByteBuffer) queue.get(0);
        socketChannel.write(buf);
        if (buf.remaining() > 0) {
          // ... or the socket's buffer fills up
          break;
        }
        queue.remove(0);
      }

      if (queue.isEmpty()) {
        // We wrote away all data, so we're no longer interested
        // in writing on this socket. Switch back to waiting for data.
        addChangeRequest(new ChangeRequest(socketChannel, ChangeRequest.CHANGEOPS, SelectionKey.OP_READ));
      }
    }
    
  }

  private Selector initSelector() throws IOException {
    // Create a new selector
    Selector socketSelector = SelectorProvider.provider().openSelector();

    // Create a new non-blocking server socket channel
    fServerChannel = ServerSocketChannel.open();
    fServerChannel.configureBlocking(false);

    // Bind the server socket to the specified address and port
    InetSocketAddress isa = new InetSocketAddress(fHostAddress, fPort);
    fServerChannel.socket().bind(isa);

    // Register the server socket channel, indicating an interest in 
    // accepting new connections
    fServerChannel.register(socketSelector, SelectionKey.OP_ACCEPT);

    return socketSelector;
    
  }
  
  private void addChangeRequest(ChangeRequest pChangeRequest) {
    synchronized (fPendingChanges) {
      fPendingChanges.add(pChangeRequest);
    }
    // wake up our selecting thread so it can make the required changes
    fSelector.wakeup();
  }
  
}
