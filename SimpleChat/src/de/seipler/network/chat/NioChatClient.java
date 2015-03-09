package de.seipler.network.chat;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class NioChatClient implements Runnable {

  // The host:port combination to connect to
  private InetAddress fHostAddress;

  private int fPort;

  // The selector we'll be monitoring
  private Selector fSelector;

  // The buffer into which we'll read data when it's available
  private ByteBuffer fReadBuffer;

  // A list of PendingChange instances
  private List fPendingChanges;

  // Maps a SocketChannel to a list of ByteBuffer instances
  private List fPendingData;

  private IChatCommandHandler fChatCommandHandler;
  
  private SocketChannel fSocketChannel;  

  public NioChatClient(InetAddress pHostAddress, int pPort) throws IOException {
    fHostAddress = pHostAddress;
    fPort = pPort;
    fSelector = SelectorProvider.provider().openSelector();
    fPendingChanges = Collections.synchronizedList(new LinkedList());
    fPendingData = Collections.synchronizedList(new LinkedList());
    fReadBuffer = ByteBuffer.allocate(8192);
  }

  public void send(ChatCommand pChatCommand) throws IOException {

    // queue the data we want written
    synchronized (fPendingData) {
      fPendingData.add(ByteBuffer.wrap(pChatCommand.toByteArray()));
    }
    
    addChangeRequest(new ChangeRequest(fSocketChannel, ChangeRequest.CHANGEOPS, SelectionKey.OP_WRITE));
    
  }

  public void run() {
    while (true) {
      try {
        // Process any pending changes
        synchronized (fPendingChanges) {
          if (!fPendingChanges.isEmpty()) {
            ChangeRequest change = (ChangeRequest) fPendingChanges.remove(0);
            switch (change.type) {
              case ChangeRequest.CHANGEOPS:
                SelectionKey key = change.socket.keyFor(fSelector);
                key.interestOps(change.ops);
                break;
              case ChangeRequest.REGISTER:
                change.socket.register(fSelector, change.ops);
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
          if (key.isConnectable()) {
            this.finishConnection(key);
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
      key.cancel();
      socketChannel.close();
      return;
    }

    if (numRead == -1) {
      // Remote entity shut the socket down cleanly. Do the
      // same from our end and cancel the channel.
      key.channel().close();
      key.cancel();
      return;
    }

    // Make a correctly sized copy of the data before handing it
    // to the client
    byte[] rspData = new byte[numRead];
    System.arraycopy(fReadBuffer.array(), 0, rspData, 0, numRead);
    
    ChatCommand chatCommand = ChatCommandFactory.getInstance().fromByteArray(rspData);    
    fChatCommandHandler.handleChatCommand(chatCommand);
    
  }

  private void write(SelectionKey key) throws IOException {

    SocketChannel socketChannel = (SocketChannel) key.channel();

    synchronized (fPendingData) {
      
      // Write until there's not more data ...
      while (!fPendingData.isEmpty()) {
        ByteBuffer buf = (ByteBuffer) fPendingData.remove(0);
        socketChannel.write(buf);
        if (buf.remaining() > 0) {
          // ... or the socket's buffer fills up
          break;
        }
      }

      if (fPendingData.isEmpty()) {
        addChangeRequest(new ChangeRequest(fSocketChannel, ChangeRequest.CHANGEOPS, SelectionKey.OP_READ));
      }
      
    }
    
  }

  private void finishConnection(SelectionKey key) throws IOException {
    
    SocketChannel socketChannel = (SocketChannel) key.channel();

    // Finish the connection. If the connection operation failed
    // this will raise an IOException.
    try {
      socketChannel.finishConnect();
    } catch (IOException e) {
      // Cancel the channel's registration with our selector
      System.out.println(e);
      key.cancel();
      return;
    }

    addChangeRequest(new ChangeRequest(fSocketChannel, ChangeRequest.REGISTER, SelectionKey.OP_READ));
    
  }

  public void initiateConnection(IChatCommandHandler pChatCommandHandler) throws IOException {
    
    fChatCommandHandler = pChatCommandHandler;

    // Create a non-blocking socket channel
    fSocketChannel = SocketChannel.open();
    fSocketChannel.configureBlocking(false);
    
    // Kick off connection establishment
    fSocketChannel.connect(new InetSocketAddress(this.fHostAddress, this.fPort));

    // Queue a channel registration since the caller is not the
    // selecting thread. As part of the registration we'll register
    // an interest in connection events. These are raised when a channel
    // is ready to complete connection establishment.
    addChangeRequest(new ChangeRequest(fSocketChannel, ChangeRequest.REGISTER, SelectionKey.OP_CONNECT));
    // wake up our selecting thread so it can make the required changes
    fSelector.wakeup();
    
  }
  
  private void addChangeRequest(ChangeRequest pChangeRequest) {
    synchronized (fPendingChanges) {
      fPendingChanges.add(pChangeRequest);
    }
    // wake up our selecting thread so it can make the required changes
    fSelector.wakeup();
  }
  
}