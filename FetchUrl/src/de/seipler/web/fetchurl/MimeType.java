package de.seipler.web.fetchurl;

import java.util.HashMap;
import java.util.Map;


/**
 * 
 * @author Georg Seipler
 */
public final class MimeType {
  
  public static int MAX_LENGTH = 5;
  
  private Map extensionPerType;
  
  public MimeType() {
    this.extensionPerType = new HashMap();
    
    extensionPerType.put("image/bmp", "bmp");
    extensionPerType.put("image/gif", "gif");
    extensionPerType.put("image/jpeg", "jpg");
    extensionPerType.put("image/png", "png");
    extensionPerType.put("image/tiff", "tif");
    extensionPerType.put("text/css", "css");
    extensionPerType.put("text/html", "htm");
    extensionPerType.put("text/plain", "txt");
    extensionPerType.put("text/richtext", "rtx");
    extensionPerType.put("text/rtf", "rtf");
    extensionPerType.put("text/sgml", "sgm");
    extensionPerType.put("text/xml", "xml");
  }
  
  /**
   * 
   */
  public String getExtension(String type) {
    return (String) this.extensionPerType.get(type);
  }

}

