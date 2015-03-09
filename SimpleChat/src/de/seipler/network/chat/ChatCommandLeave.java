package de.seipler.network.chat;

/**
 * 
 * @author Georg Seipler
 */
public class ChatCommandLeave extends ChatCommand {

  public ChatCommandLeave(String pUser) {
    super(new String[] { pUser });
  }
  
  protected ChatCommandLeave(byte[] pBytes) {
    super(pBytes);
  }
  
  public int getType() {
    return LEAVE;
  }

}
