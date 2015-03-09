package de.seipler.util.file.java;

import java.io.*;
import java.util.*;

/**
 * Structure representing a class or interface definition in a Java sourcefile.
 * 
 * @see JavaFileParser
 * @see JavaFileStructure
 *
 * @author Georg Seipler
 */
public class JavaClass extends JavaCode {
  
  private boolean isInterface = false;
  private String packageName = null;
  private String superClass = null;
  private List interfaceList = null;
  

  /**
   * Convenience constructor for extension.
   */
  protected JavaClass() {
    super();
    this.interfaceList = new ArrayList();
  }
 
  /**
   * Default Constructor.
   * 
   * @param source the sourcefile for this structure.
   * @param name the name of the class or interface definition.
   * @param modifier the collected modifiers of this structure.
   * @param isInterface wheter this structure represents a class or an interface.
   * @exception IllegalArgumentException if source is null or a directory.
   */
  public JavaClass(File source, String name, JavaModifier modifier, boolean isInterface) {
    this();
    setSource(source);
    setName(name);
    setModifier(modifier);
    setIsInterface(isInterface);
  }

  /**
   * Adds the name of an implemented interface.
   */
  public void addInterface(String name) {
    this.interfaceList.add(name);
  }

  /**
   * Returns full qualified name for this class structure (packagename.classname).
   */
  public String getFullName() {
    StringBuffer buffer = new StringBuffer();
    if (packageName != null) {
      buffer.append(packageName);
      buffer.append(".");
    }
    buffer.append(getName());
    return buffer.toString();
  }

  /**
   * Returns list of implemented interfaces.
   */
  public List getInterfaces() {
    return this.interfaceList;
  }
  
  /**
   * Returns packagename of this structure.
   */
  public String getPackage() {
    return this.packageName;
  }

  /**
   * Returns extended class or interface (<code>null</code> if derived from <code>Object</code>).
   */  
  public String getSuperClass() {
    return this.superClass;
  }

  /**
   * Returns <code>true</code> if this structure represents an interface.
   */
  public boolean isInterface() {
    return this.isInterface;
  }

  /**
   * Defines this structure as an interface instead of a class.
   */
  protected void setIsInterface(boolean isInterface) {
    this.isInterface = isInterface;
  }

  /**
   * Defines the packagename of this structure.
   */
  public void setPackage(String name) {
    this.packageName = name;
  }

  /**
   * Defines the extended class or interface.
   */
  public void setSuperClass(String name) {
    this.superClass = name;
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
    if (isInterface) {
      buffer.append("interface ");
    } else {
      buffer.append("class ");
    }
    buffer.append(getName());
    if (superClass != null) {
      buffer.append(" extends "); buffer.append(superClass);
    }
    if (interfaceList.size() > 0) {
      buffer.append(" implements");
      Iterator interfaceIterator = interfaceList.iterator();
      while (interfaceIterator.hasNext()) {
        buffer.append(" "); buffer.append(interfaceIterator.next());
      }
    }
    return buffer.toString();
  }
  
}
