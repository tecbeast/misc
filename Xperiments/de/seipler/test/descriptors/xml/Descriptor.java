package de.seipler.test.descriptors.xml;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

/**
 * 
 * @author Georg Seipler
 */
public abstract class Descriptor {

  protected static String lineSeparator = System.getProperty("line.separator", "\n");

  private File file;
  private long lastModified;
  private String author;

  
  /**
   * Default Constructor. 
   */
  protected Descriptor() {
    setLastModified(-1);
  }

  /**
   * Returns the author.
   */
  public String getAuthor() {
    return this.author;
  }

  /**
   * Returns the File.
   */
  public File getFile() {
    return this.file;
  }

  /**
   * Returns the type. 
   */
  public abstract String getType();

  /**
   * Returns the lastModified.
   */
  public long getLastModified() {
    return this.lastModified;
  }

  /**
   * 
   */
  protected String removeIllegalChars(String source) {
    if (source == null) { return null; }
    int i = 0;
    boolean noNeedForConversion = true;
    while (noNeedForConversion && (i < source.length())) {
      switch(source.charAt(i)) {
        case '\0': noNeedForConversion = false; break;
          default: i++; break;
      }      
    }
    if (noNeedForConversion) { return source; }
    StringBuffer buffer = new StringBuffer(source.substring(0, i));
    while (i < source.length()) {
      switch(source.charAt(i)) {
        case '\0': break;
          default: buffer.append(source.charAt(i)); break;
      }
      i++;
    }
    return buffer.toString();
  }

  /**
   * Sets the author.
   */
  protected void setAuthor(String author) {
    this.author = author;
  }

  /**
   * Sets the File.
   */
  protected void setFile(File file) {
    this.file = file;
  }

  /**
   * Sets the lastModified.
   */
  protected void setLastModified(long lastModified) {
    this.lastModified = lastModified;
  }

  /**
   * 
   */
  public abstract void writeTo(Writer out, int indentation) throws IOException;

}
