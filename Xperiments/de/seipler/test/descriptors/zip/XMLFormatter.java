package de.seipler.test.descriptors.zip;

/**
 * @author Georg Seipler
 */
public class XMLFormatter {

  /**
   * 
   */  
  public static String toXML(String source) {
    
    if (source == null) { return null; }
    
    int i = 0;
    boolean noNeedForConversion = true;
    while (noNeedForConversion && (i < source.length())) {
      switch(source.charAt(i)) {
        case '&':
        case '<':
        case '>':
        case '"':
        case '\'': noNeedForConversion = false; break;
        default: i++; break;
      }      
    }
    
    if (noNeedForConversion) { return source; }
    
    StringBuffer buffer = new StringBuffer(source.substring(0, i));
    while (i < source.length()) {
      switch(source.charAt(i)) {
        case '&': buffer.append("&amp;"); break;
        case '<': buffer.append("&lt;"); break;
        case '>': buffer.append("&gt;"); break;
        case '"': buffer.append("&quot;"); break;
        case '\'': buffer.append("&apos;"); break;
        default: buffer.append(source.charAt(i)); break;
      }
      i++;
    }
    return buffer.toString();
    
  }

}
