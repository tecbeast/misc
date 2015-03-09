package de.seipler.network.chat;

/**
 * 
 * @author Georg Seipler
 */
public class ChatCommandFactory {
  
  private static final ChatCommandFactory _INSTANCE = new ChatCommandFactory();
  
  public static ChatCommandFactory getInstance() {
    return _INSTANCE;
  }
  
  private ChatCommandFactory() {
    super();
  }
  
  public ChatCommand fromByteArray(byte[] pBytes) {
    ChatCommand chatCommand = null;
    if ((pBytes != null) && (pBytes.length > 0)) {
      switch ((int) pBytes[0]) {
        case ChatCommand.JOIN:
          chatCommand = new ChatCommandJoin(pBytes);
          break;
        case ChatCommand.LEAVE:
          chatCommand = new ChatCommandLeave(pBytes);
          break;
        case ChatCommand.TALK:
          chatCommand = new ChatCommandTalk(pBytes);
          break;
        case ChatCommand.USERS:
          chatCommand = new ChatCommandUsers(pBytes);
          break;
        default:
          throw new ChatCommandException("Unable to create ChatCommand: unknown command type.");
      }
    } else {
      throw new ChatCommandException("Unable to create ChatCommand: byte array is null or empty.");
    }
    return chatCommand;
  }

}
