package de.seipler.util.file.java;

import java.io.*;

/**
 * Base Class for code structures (methods, classes, interfaces) in Java.
 * 
 * @author Georg Seipler
 */
public abstract class JavaCode {

  private File sourceFile = null;
  private JavaModifier modifier = null;
  private String name = null;
  private int startLine = 0;
  private int endLine = 0;
  private int block = 0;


  /**
   * Returns the block level of this structure.
   * The outer class is block 0, inner classes and methods
   * would be block 1 or higher.
   */
  public int getBlock() {
    return this.block;
  }

  /**
   * Returns the last line of this structure.
   */  
  public int getEndLine() {
    return this.endLine;
  }

  /**
   * Returns the collected modfiers for this structure.
   */
  public JavaModifier getModifier() {
    return this.modifier;
  }

  /**
   * Returns the name of this structure.
   */ 
  public String getName() {
    return this.name;
  }

  /**
   * Returns the sourcefile underlying this structure.
   */
  public File getSource() {
    return this.sourceFile;
  }
  
  /**
   * Returns the first line of this structure.
   */  
  public int getStartLine() {
    return this.startLine;
  }

  /**
   * Defines the block level of this structure.
   */
  public void setBlock(int block) {
    this.block = block;
  }
  
  /**
   * Defines the last line of this structure.
   */  
  public void setEndLine(int endLine) {
    this.endLine = endLine;
  }

  /**
   * Defines the modifiers for this structure.
   */
  protected void setModifier(JavaModifier modifier) {
    if (modifier == null) { modifier = new JavaModifier(); }
    this.modifier = modifier;
  }

  /**
   * Defines the name of this structure.
   */
  protected void setName(String name) {
    this.name = name;
  }

  /**
   * Defines the sourcefile for this structure.
   * 
   * @exception IllegalArgumentException if source is null or a directory.
   */  
  protected void setSource(File source) {
    if ((source != null) && !source.isDirectory()) {
      this.sourceFile = source;
    } else {
      throw new IllegalArgumentException("invalid sourcefile");
    }
  }
  
  /**
   * Defines the first line of this structure.
   */  
  public void setStartLine(int startLine) {
    this.startLine = startLine;
  }

}
