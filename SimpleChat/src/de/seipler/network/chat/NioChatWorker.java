package de.seipler.network.chat;

import java.nio.channels.SocketChannel;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class NioChatWorker implements Runnable {
  
  private List fQueue;
  
  public NioChatWorker() {
    fQueue = Collections.synchronizedList(new LinkedList()); 
  }
  
  public void processCommand(NioChatServer pServer, SocketChannel pSocketChannel, ChatCommand pChatCommand) {
    
    ChatCommand userCommand = pChatCommand;
    SocketChannel[] channels = pServer.getAcceptedChannels();

    if (pChatCommand.getType() == ChatCommand.JOIN) {
      int j = 1;
      String[] users = new String[channels.length];           
      for (int i = 0; i < channels.length; i++) {
        String user = pServer.getUserForChannel(channels[i]);
        if (pSocketChannel.equals(channels[i])) {
          users[0] = user;
        } else {
          users[j++] = user;
        }
      }
      userCommand = new ChatCommandUsers(users);
    }
    
    byte[] commandBytes = pChatCommand.toByteArray();
    synchronized (fQueue) {
      for (int i = 0; i < channels.length; i++) {
        if (pSocketChannel.equals(channels[i])) {
          fQueue.add(new NioServerDataEvent(pServer, channels[i], userCommand.toByteArray()));
        } else {
          fQueue.add(new NioServerDataEvent(pServer, channels[i], commandBytes));
        }
      }
      fQueue.notify();
    }
    
  }
  
  public void run() {
    NioServerDataEvent dataEvent;
    while (true) {
      // Wait for data to become available
      synchronized (fQueue) {
        while (fQueue.isEmpty()) {
          try {
            fQueue.wait();
          } catch (InterruptedException e) {
          }
        }
        dataEvent = (NioServerDataEvent) fQueue.remove(0);
      }
      // Return to sender
      dataEvent.server.send(dataEvent.socket, dataEvent.data);
    }
  }
  
}
