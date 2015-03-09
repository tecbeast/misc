package de.seipler.games.bloodbowl;

/**
 * 
 * @author Georg Seipler
 */
public class Coach {
  
  private String fName;
  private String fUrl;

  public String getName() {
    return fName;
  }

  public void setName(String pName) {
    fName = pName;
  }

  public String getUrl() {
    return fUrl;
  }

  public void setUrl(String pUrl) {
    fUrl = pUrl;
  }
  
  public String toString() {
    String newLine = System.getProperty("line.separator", "\n");
    StringBuffer buffer = new StringBuffer();
    buffer.append("[Coach]");
    buffer.append(newLine);
    buffer.append("Name: ");
    buffer.append(getName());
    buffer.append(newLine);
    buffer.append("Url: ");
    buffer.append(getUrl());
    buffer.append(newLine);
    return buffer.toString();
  }

}
