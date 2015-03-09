package de.seipler.test.descriptors.xml;

import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Georg Seipler
 */
public class XMLFormatUtil {
  
  public static final DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
  public static final String lineSeparator = System.getProperty("line.separator", "\n");
  public static final String encoding = "UTF-8";
  
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

  /**
   * 
   */
  public static void writeXMLHeaderTo(Writer out) throws IOException{
    StringBuffer buffer = new StringBuffer(40);    
    buffer.append("<?xml version=\"1.0\" encoding=\"");
    buffer.append(encoding);
    buffer.append("\"?>");
    buffer.append(lineSeparator);
    out.write(buffer.toString());
  }

  /**
   * 
   */
  public static void writeElementTo(Writer out, int indentation, String name, int value) throws IOException {
    if (value < 0) {
      writeElementTo(out, indentation, name, null);
    } else {
      writeElementTo(out, indentation, name, Integer.toString(value));
    }
  }

  /**
   * 
   */
  public static void writeElementTo(Writer out, int indentation, String name, String value) throws IOException {
    StringBuffer buffer = new StringBuffer(80);
    indent(buffer, indentation);
    if ((value == null) || (value.length() == 0)) {
      buffer.append('<');
      buffer.append(name);
      buffer.append("/>");
    } else {
      buffer.append('<');
      buffer.append(name);
      buffer.append('>');
      buffer.append(toXML(value));
      buffer.append("</");
      buffer.append(name);
      buffer.append('>');
    }
    buffer.append(lineSeparator);
    out.write(buffer.toString());
  }

  /**
   * 
   */
  public static void writeElementHeaderTo(Writer out, int indentation, String name) throws IOException {
    writeElementHeaderTo(out, indentation, name, null, null);
  }

  /**
   * 
   */
  public static void writeElementHeaderTo(Writer out, int indentation, String name, String attribute, String value) throws IOException {
    StringBuffer buffer = new StringBuffer(80);
    indent(buffer, indentation);
    buffer.append('<');
    buffer.append(name);
    if ((attribute != null) && (attribute.length() > 0)) {
      buffer.append(' ');
      buffer.append(attribute);
      buffer.append("=\"");
      if (value != null) { buffer.append(value); }
      buffer.append('"');
    }
    buffer.append('>');
    buffer.append(lineSeparator);    
    out.write(buffer.toString());
  }

  /**
   * 
   */
  public static void writeElementFooterTo(Writer out, int indentation, String name) throws IOException {
    StringBuffer buffer = new StringBuffer(80);
    indent(buffer, indentation);
    buffer.append("</");
    buffer.append(name);
    buffer.append('>');
    buffer.append(lineSeparator);    
    out.write(buffer.toString());
  }

  /**
   * 
   */
  public static void indent(StringBuffer buffer, int steps) {
    for (int i = 0; i < steps; i++) { buffer.append("  ");  }
  }

  /**
   * 
   */
  public static long fromTimestamp(String timestamp) {
    try {
      Date date = dateFormat.parse(timestamp);
      return date.getTime();
    } catch (ParseException pe) {
      return -1;
    }
  }

  /**
   * 
   */
  public static String toTimestamp(long nrOfMilliseconds) {
    if (nrOfMilliseconds < 0) {
      return null;
    } else {
      return dateFormat.format(new Date(nrOfMilliseconds));
    }
  }

}
