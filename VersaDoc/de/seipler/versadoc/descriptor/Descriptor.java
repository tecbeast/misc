package de.seipler.versadoc.descriptor;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

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
   * Sets the author.
   */
  public void setAuthor(String author) {
    this.author = author;
  }

  /**
   * Sets the File.
   */
  public void setFile(File file) {
    this.file = file;
  }

  /**
   * Sets the lastModified.
   */
  public void setLastModified(long lastModified) {
    this.lastModified = lastModified;
  }

  /**
   * 
   */
  public abstract void writeTo(OutputStream out, int indentation) throws IOException;

}
