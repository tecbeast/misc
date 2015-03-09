package de.seipler.network.chat;

/**
 * 
 * @author Georg Seipler
 */
public class ChatCommandJoin extends ChatCommand {
  
  public ChatCommandJoin(String pUser) {
    super(new String[] { pUser });
  }
  
  protected ChatCommandJoin(byte[] pBytes) {
    super(pBytes);
  }
  
  public int getType() {
    return JOIN;
  }

}
