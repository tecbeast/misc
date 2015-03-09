package de.seipler.network.chat;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Georg Seipler
 */
public abstract class ChatCommand {
  
  public static final int JOIN = 1;
  public static final int LEAVE = 2;
  public static final int TALK = 3;
  public static final int USERS = 4;
  
  private String[] fData;
  
  protected ChatCommand(String[] pData) {
    fData = pData;
  }

  protected ChatCommand(byte[] pBytes) {
    if (pBytes == null) {
      throw new ChatCommandException("Unable to create ChatCommand: byte array is null.");
    }
    if ((int)pBytes[0] != getType()) {
      throw new ChatCommandException("Unable to create ChatCommand: wrong type.");
    }
    fData = extractDataFromByteArray(pBytes);
  }
  
  public abstract int getType();
  
  protected String[] getData() {
    return fData;
  }
  
  public String getUser() {
    return getData()[0];
  }
  
  public byte[] toByteArray() {
    int length = 1;
    for (int i = 0; i < fData.length; i++) {
      length += (fData[i].length() + 1);
    }
    byte[] bytes = new byte[length];
    int pos = 0;
    bytes[pos++] = (byte) getType();
    for (int i = 0; i < fData.length; i++) {
      byte[] dataBytes = fData[i].getBytes();
      bytes[pos++] = (byte) dataBytes.length;
      System.arraycopy(dataBytes, 0, bytes, pos, dataBytes.length);
      pos += dataBytes.length;
    }
    return bytes;
  }
  
  private String[] extractDataFromByteArray(byte[] pBytes) {
    List dataList = new ArrayList();    
    int pos = 1;
    while (pos < pBytes.length) {
      int dataLength = pBytes[pos++];
      dataList.add(new String(pBytes, pos, dataLength));
      pos += dataLength;
    }
    return (String[]) dataList.toArray(new String[dataList.size()]);
  }
  
}
