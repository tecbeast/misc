package de.seipler.network.chat;

/**
 * 
 * @author Georg Seipler
 */
public class ChatCommandUsers extends ChatCommand {
  
  public ChatCommandUsers(String[] pUsers) {
    super(pUsers);
  }
  
  protected ChatCommandUsers(byte[] pBytes) {
    super(pBytes);
  }
  
  public String[] getAllUsers() {
    return getData();
  }

  public int getType() {
    return USERS;
  }
  
}
