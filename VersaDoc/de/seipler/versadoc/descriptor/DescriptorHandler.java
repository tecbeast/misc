package de.seipler.versadoc.descriptor;

import java.io.File;

/**
 * 
 * @author Georg Seipler
 */
public abstract class DescriptorHandler {

  private File file;

  /**
   *
   */
  public void characters(char[] ch, int start, int length) {
  }

  /**
   *
   */
  public void endElement(String name) {
  }

  /**
   *
   */
  public void startElement(String name) {
  }

  /**
   * 
   */
  public void setAttribute(String name, String value) {
  }

  /**
   * 
   */
  public void startDescriptor() {
  }
  
  /**
   * 
   */
  public void endDescriptor() {
  }

  /**
   * 
   */
  public abstract Descriptor getDescriptor();
  
  /**
   * 
   */
  public abstract String getType();

  /**
   * Returns the directory.
   * @return File
   */
  public File getFile() {
    return this.file;
  }

  /**
   * Sets the directory.
   * @param directory The directory to set
   */
  public void setFile(File file) {
    this.file = file;
  }

}
