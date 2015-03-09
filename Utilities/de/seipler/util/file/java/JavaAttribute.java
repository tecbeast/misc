package de.seipler.util.file.java;

import java.io.*;

/**
 * Structure representing an attribute definition in a Java sourcefile.
 * 
 * @see JavaFileParser
 * @see JavaFileStructure
 *
 * @author Georg Seipler
 */
public class JavaAttribute extends JavaCode {

  private String type;

  /**
   * Convenience constructor for extension.
   */
  protected JavaAttribute() {
    super();
  }
 
  /**
   * Default Constructor.
   * 
   * @param source the sourcefile for this structure.
   * @param name the name of the class or interface definition.
   * @param modifier the collected modifiers of this structure.
   * @exception IllegalArgumentException if source is null or a directory.
   */
  public JavaAttribute(File source, String name, String type, JavaModifier modifier) {
    this();
    setSource(source);
    setName(name);
    setType(type);
    setModifier(modifier);
  }

  /**
   * Returns the typename of this attribute;
   */
  public String getType() {
    return this.type;
  }

  /**
   * Adds the typename of this attribute.
   */
  protected void setType(String type) {
    this.type = type;
  }

  /**
   * String representation of this structure.
   * Shows complete header information as well as start and end line.
   */
  public String toString() {
    StringBuffer buffer = new StringBuffer();
    buffer.append(getStartLine());
    buffer.append(" - ");
    buffer.append(getEndLine());
    buffer.append(": ");
    String modifier = getModifier().toString();
    if (modifier.length() > 0) {
      buffer.append(modifier);
      buffer.append(" ");
    }
    buffer.append(getType());
    buffer.append(" ");
    buffer.append(getName());    
    return buffer.toString();
  }

}
