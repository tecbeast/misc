package de.seipler.util.file.java;

import java.io.*;
import java.util.*;

/**
 * Structure representing a method definition in a Java sourcefile.
 * 
 * @see JavaFileParser
 * @see JavaFileStructure
 *
 * @author Georg Seipler
 */
public class JavaMethod extends JavaCode {

  private int codeStartLine;
  private String returnValue;
  private List throwList;
  private List parameterList;
  private List parameterNameList;  

  /**
   * Convenience constructor for extension.
   */
  protected JavaMethod() {
    super();
    this.throwList = new ArrayList();
    this.parameterList = new ArrayList();
    this.parameterNameList = new ArrayList();
  }
 
  /**
   * Default Constructor.
   * 
   * @param source the sourcefile for this structure.
   * @param name the name of the method definition.
   * @param modifier the collected modifiers of this structure.
   * @exception IllegalArgumentException if source is null or a directory.
   */
  public JavaMethod(File source, String name, JavaModifier modifier) {
    this();
    setSource(source);
    setName(name);
    setModifier(modifier);
  }

  /**
   * Adds the typename of a parameter for this method.
   */
  public void addParameter(String parameter) {
    this.parameterList.add(parameter);
  }

  /**
   * Adds a list of parameter typenames for this method.
   */
  protected void addParameters(List parameterList) {
    this.parameterList.addAll(parameterList);
  }

  /**
   * Adds the typename of a parameter for this method.
   */
  public void addParameterName(String parameterName) {
    this.parameterNameList.add(parameterName);
  }

  /**
   * Adds a list of parameter typenames for this method.
   */
  protected void addParameterNames(List parameterNameList) {
    this.parameterNameList.addAll(parameterNameList);
  }

  /**
   * Adds the name of a Throwable produced by this method (<code>throws</code> clause).
   */
  public void addThrowable(String name) {
    this.throwList.add(name);
  }

  /**
   * Returns the list of parameter typenames of this method.
   */
  public List getParameters() {
    return this.parameterList;
  }
  
  /**
   * Returns the list of parameter typenames of this method.
   */
  public List getParameterNames() {
    return this.parameterNameList;
  }
  
  /**
   * Returns the return typename of this method definition.
   */
  public String getReturnValue() {
    return this.returnValue;
  }
  
  /**
   * Returns the signature of this method definition.
   * A Java method signature consists of the methodname and parameterlist.
   */
  public String getSignature() {
    StringBuffer buffer = new StringBuffer();
    buffer.append(getName());
    buffer.append("(");
    boolean firstParameter = true;
    Iterator parameterIterator = parameterList.iterator();
    while (parameterIterator.hasNext()) {
      if (firstParameter) {
        firstParameter = false;
      } else {
        buffer.append(", ");
      }
      buffer.append(parameterIterator.next());
    }
    buffer.append(")");
    return buffer.toString();
  }

  /**
   * Returns the list of Throwables produced by this method (<code>throws</code> clause).
   */  
  public List getThrows() {
    return this.throwList;
  }

  /**
   * Defines the typename of the returnvalue of this method definition.
   */  
  protected void setReturnValue(String returnValue) {
    this.returnValue = returnValue;
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
    if (returnValue != null) {
      buffer.append(returnValue); buffer.append(" ");
    }
    buffer.append(getSignature());
    if (throwList.size() > 0) {
      buffer.append(" throws");
      Iterator throwIterator = throwList.iterator();
      while (throwIterator.hasNext()) {
        buffer.append(" "); buffer.append(throwIterator.next());
      }
    }
    return buffer.toString();
  }

  /**
   * Returns the codeStartLine.
   * @return int
   */
  public int getCodeStartLine() {
    return codeStartLine;
  }

  /**
   * Sets the codeStartLine.
   * @param codeStartLine The codeStartLine to set
   */
  protected void setCodeStartLine(int codeStartLine) {
    this.codeStartLine = codeStartLine;
  }

}
