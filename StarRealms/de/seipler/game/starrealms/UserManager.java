package de.seipler.game.starrealms;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Properties;

/**
 *  UserManager handles user names, passwords and emailaddresses via a property file.
 */
public class UserManager {

  private final static String header = "StarRealms User Administration";

  private Properties properties;
  private String configFile;

  /**
   *
   */
  public UserManager(String configFile) throws IOException {
    if (configFile == null) {
      Class myClass = this.getClass();
      String myPath = myClass.getName().replace('.', File.separatorChar);
      URL propertyURL = myClass.getClassLoader().getResource(myPath + ".properties");
      if (propertyURL != null) {
        this.configFile = propertyURL.getFile();
      }
    } else {
      this.configFile = configFile;
    }
    load();
  }

  /**
   *
   */
  public void addUser(String user, String password, String email) {
    setUserPassword(user, password);
    setUserEmail(user, email);
  }

  /**
   *
   */
  public String getUserEmail(String user) {
    return properties.getProperty("user." + user + ".email");
  }

  /**
   *
   */
  public String getUserPassword(String user) {
    return properties.getProperty("user." + user + ".password");
  }

  /**
   *
   */
  private void load() throws IOException {
    if (configFile == null) {
      throw new IOException("filename is null");
    }
    FileInputStream in = new FileInputStream(configFile);
    properties = new Properties();
    properties.load(in);
  }

  /**
   *
   */
  public void removeUser(String user) {
    properties.remove("user." + user + ".email");
    properties.remove("user." + user + ".password");
  }

  /**
   *
   */
  public void save() throws IOException {
    if (configFile == null) {
      throw new IOException("filename is null");
    }
    OutputStream out = new FileOutputStream(configFile);
    // Java 1:
    // properties.save(out, header);
    // Java 2:
    properties.store(out, header);
    out.close();
  }

  /**
   *
   */
  public void setUserEmail(String user, String email) {
    // Java 1:
    // properties.put("user." + user + ".email", email);
    // Java 2:
    properties.setProperty("user." + user + ".email", email);
  }

  /**
   *
   */
  public void setUserPassword(String user, String password) {
    // Java 1:
    // properties.put("user." + user + ".password", password);
    // Java 2:
    properties.setProperty("user." + user + ".password", password);
  }
  
}
