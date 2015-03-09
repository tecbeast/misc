package de.seipler.network.chat;

import java.net.InetAddress;

import javax.swing.JFrame;

/**
 * 
 * @author Georg Seipler
 */
public class SimpleChat {

  public static void main(String[] args) {
    
    if ((args == null) || (args.length < 1)) {
      showUsageAndExit();
    }

    String mode = args[0];
    if ("server".equalsIgnoreCase(mode)) {

      if (args.length != 2) {
        showUsageAndExit();
      }
      
      String port = args[1];

      try {
        
        NioChatWorker worker = new NioChatWorker();
        new Thread(worker).start();
        new Thread(new NioChatServer(null, Integer.parseInt(port), worker)).start();
        
      } catch (Exception e) {
        e.printStackTrace(System.err);
      }
      
    } else if ("client".equalsIgnoreCase(mode)) {

      if (args.length != 4) {
        showUsageAndExit();
      }
      
      String hostaddress = args[1];
      String port = args[2];
      String user = args[3];
      
      try {
        
        NioChatClient nioClient = new NioChatClient(InetAddress.getByName(hostaddress), Integer.parseInt(port));
        Thread t = new Thread(nioClient);
        t.setDaemon(true);
        t.start();
        
        JFrame myFrame = new SimpleChatClientUi(nioClient, user);
        myFrame.setVisible(true);
        
      } catch (Exception e) {
        e.printStackTrace(System.err);
      }
      
    } else {
      
      showUsageAndExit();
      
    }
    
    
  }
  
  private static void showUsageAndExit() {
    System.out.println("Usage as server: java -jar SimpleChat.jar server <port>");
    System.out.println("Usage as client: java -jar SimpleChat.jar client <hostaddress> <port> <user>");
    System.exit(0);
  }
  
}
