package com.balancedbytes.tools.xbmc;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.UserInfo;

public class SftpConnector {
  
  private String fHost;
  private int fPort;
  private String fUser;
  private MyUserInfo fUserInfo;
  private Session fSession;
  private ChannelSftp fChannel;

  public SftpConnector(String host, int port, String user, String password) {
    fHost = host;
    fPort = port;
    fUser = user;
    fUserInfo = new MyUserInfo(password);
  }
  
  public void connect() throws JSchException {
    JSch jsch = new JSch();
    fSession = jsch.getSession(fUser, fHost, fPort);
    fSession.setUserInfo(fUserInfo);
    fSession.connect();
    fChannel = (ChannelSftp) fSession.openChannel("sftp");
    fChannel.connect();
  }
  
  public void disconnect() {
    if (fSession != null) {
      fSession.disconnect();
    }
  }
  
  public List<LsEntry> ls(String path) throws SftpException {
    if ((fChannel == null) || (path == null)) {
      return null;
    }
    List<LsEntry> entries = new ArrayList<>();
    Vector<?> result = fChannel.ls(path);
    if (result != null) {
      for (int i = 0; i < result.size(); i++) {
        Object obj = result.elementAt(i);
        if (obj instanceof LsEntry) {
          entries.add((LsEntry) obj);
        }
      }
    }
    return entries;
  }
  
  public static class MyUserInfo implements UserInfo {

    private String fPassword;
    
    public MyUserInfo(String password) {
      fPassword = password;
    }
    
    public String getPassword() {
      return fPassword;
    }

    public boolean promptYesNo(String str) {
      return true;
    }

    public String getPassphrase() {
      return null;
    }

    public boolean promptPassphrase(String message) {
      return true;
    }

    public boolean promptPassword(String message) {
      return true;
    }

    public void showMessage(String message) {
    }

  }
  
  public static void main(String[] args) {
    SftpConnector sftp = new SftpConnector("192.168.178.32", 22, "root", "openelec");
    try {
      sftp.connect();
      List<LsEntry> lsEntries = sftp.ls("/var/media/Xbmc 01/Filme/FSK 00");
      for (LsEntry lsEntry : lsEntries) {
        System.out.println(lsEntry.getFilename());
      }
      sftp.disconnect();
    } catch (Exception any) {
      any.printStackTrace(System.err);
    }
  }

}
