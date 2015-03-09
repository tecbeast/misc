package de.seipler.network.chat;

/**
 * 
 * @author Georg Seipler
 */
public class ChatCommandTalk extends ChatCommand {
  
  public ChatCommandTalk(String pUser, String pTalk) {
    super(new String[] { pUser , pTalk });
  }
  
  protected ChatCommandTalk(byte[] pBytes) {
    super(pBytes);
  }

  public int getType() {
    return TALK;
  }
  
  public String getTalk() {
    return getData()[1];
  }
  
}
