package de.seipler.util.file.java;

import java.io.*;
import java.util.*;

/**
 * Container representing the structure of a java sourcefile.
 * Used to get context information by giving a line number and
 * asking for class and method structurs for that line. Also
 * keeps track of filename, packagename, importdefinitions and
 * number of lines.
 * 
 * @see JavaFileParser
 * @see JavaClass
 * @see JavaMethod
 * 
 * @author Georg Seipler
 */
public class JavaFile {
  
  /** A copy of the <code>line.separator</code> system property. */
  public final static String lineSeparator;

  static {
    if (System.getProperty("line.separator") != null) {
      lineSeparator = System.getProperty("line.separator");
    } else {
      lineSeparator = "\n";
    }
  }
  
  private File sourceFile;
  private String packageName;
  private List imports;
  private int importsStartLine;
  private int importsEndLine;
  private int nrOfLines;
  private List classStructures;
  private List classStartLines;
  private List methodStructures;
  private List methodStartLines;
  private List attributeStructures;
  private List attributeStartLines;
  
  
  /**
   * Default Constructor.
   * 
   * @param source file underlying this structure.
   * @exception IllegalArgumentException if source is null or a directory.
   */
  public JavaFile(File source) {
    setSource(source);
    this.imports = new ArrayList();
    this.classStructures = new LinkedList();
    this.classStartLines = new LinkedList();
    this.methodStructures = new LinkedList();
    this.methodStartLines = new LinkedList();
    this.attributeStructures = new LinkedList();
    this.attributeStartLines = new LinkedList();
  }

  /**
   * Adds an import line.
   */
  public void addImport(String name) {
    this.imports.add(name);
  }

  /**
   * Adds an attribute.
   * Lookup is later by startline (endline is checked for validity).
   * For consistency the sourcefile of the given methodStructure is set
   * to the sourcefile of this fileStructure.
   * 
   * @exception IllegalArgumentException if there is already an attribute
   *   starting at the same line.
   */
  public void addAttribute(JavaAttribute structure) {
    Integer startLineInteger = new Integer(structure.getStartLine());
    int position = Collections.binarySearch(attributeStartLines, startLineInteger);
    if (position < 0) {
      attributeStartLines.add(-(position + 1), startLineInteger);
      attributeStructures.add(-(position + 1), structure);
    } else {
      throw new IllegalArgumentException("there is already an attribute defined for line " + startLineInteger.intValue());
    }
    structure.setSource(sourceFile);
  }

  /**
   * Adds a class.
   * Lookup is later by startline (endline is checked for validity).
   * For consistency the sourcefile of the given classStructure is set
   * to the sourcefile of this fileStructure, the same holds true for
   * the packageName (if already defined at that point).
   * 
   * @exception IllegalArgumentException if there is already a class
   *   starting at the same line.
   */
  public void addClass(JavaClass structure) {
    Integer startLineInteger = new Integer(structure.getStartLine());
    int position = Collections.binarySearch(classStartLines, startLineInteger);
    if (position < 0) {
      classStartLines.add(-(position + 1), startLineInteger);
      classStructures.add(-(position + 1), structure);
    } else {
      throw new IllegalArgumentException("there is already a class defined for line " + startLineInteger.intValue());
    }
    structure.setSource(sourceFile);
    if (packageName != null) { structure.setPackage(packageName); }
  }

  /**
   * Adds a method.
   * Lookup is later by startline (endline is checked for validity).
   * For consistency the sourcefile of the given methodStructure is set
   * to the sourcefile of this fileStructure.
   * 
   * @exception IllegalArgumentException if there is already a method
   *   starting at the same line.
   */
  public void addMethod(JavaMethod structure) {
    Integer startLineInteger = new Integer(structure.getStartLine());
    int position = Collections.binarySearch(methodStartLines, startLineInteger);
    if (position < 0) {
      methodStartLines.add(-(position + 1), startLineInteger);
      methodStructures.add(-(position + 1), structure);
    } else {
      throw new IllegalArgumentException("there is already a method defined for line " + startLineInteger.intValue());
    }
    structure.setSource(sourceFile);
  }

  /**
   * Returns the attributeStructure for the given line or <code>null</code>
   * if there is no valid attribute for that line. If the fileStructure is
   * properly built that means the given line is either packagedefinition,
   * importdefinition or a comment or empty line.
   * 
   * @see isImportsContext(int)
   */
  public JavaAttribute getAttributeContext(int line) {
    JavaAttribute attributeStructure = null;
    int position = Collections.binarySearch(attributeStartLines, new Integer(line));
    if (position < 0) {
      attributeStructure = (JavaAttribute) attributeStructures.get(-(position + 2));
      if (attributeStructure.getEndLine() < line) { attributeStructure = null; }
    } else {
      attributeStructure = (JavaAttribute) attributeStructures.get(position);
    }
    return attributeStructure;
  }

  /**
   * Returns a List of all JavaAttributes inside this FileStructure ordered by startLines.
   */
  public List getAttributes() {
    return this.attributeStructures;
  }

  /**
   * Returns the classStructure for the given line or <code>null</code>
   * if there is no valid structure for that line. If the fileStructure is
   * properly built that means the given line is either packagedefinition,
   * importdefinition or a comment or empty line.
   * 
   * @see isImportsContext(int)
   */
  public JavaClass getClassContext(int line) {
    JavaClass classStructure = null;
    int position = Collections.binarySearch(classStartLines, new Integer(line));
    if (position < 0) {
      if (position < -1) {
        classStructure = (JavaClass) classStructures.get(-(position + 2));
        if (classStructure.getEndLine() < line) { classStructure = null; }
      } else {
        classStructure = null;
      }
    } else {
      classStructure = (JavaClass) classStructures.get(position);
    }
    return classStructure;    
  }

  /**
   * Returns a List of all JavaClasses inside this FileStructure ordered by startLines.
   */
  public List getClasses() {
    return this.classStructures;
  }

  /**
   * Returns the list of imports.
   */
  public List getImports() {
    return this.imports;
  }

  /**
   * Return the last line of the import definitions or 0 if not defined.
   */
  public int getImportsEndLine() {
    return this.importsEndLine;
  }
  
  /**
   * Return the first line of the import definitions or 0 if not defined.
   */
  public int getImportsStartLine() {
    return this.importsStartLine;
  } 

  /**
   * Returns the methodStructure for the given line or <code>null</code>
   * if there is no valid structure for that line. If the fileStructure is
   * properly built that means the given line is either classdefinition,
   * packagedefinition, importdefinition or a comment or empty line.
   * 
   * @see getClassContext(int)
   * @see isImportsContext(int)
   */
  public JavaMethod getMethodContext(int line) {
    JavaMethod methodStructure = null;
    int position = Collections.binarySearch(methodStartLines, new Integer(line));
    if (position < 0) {
      if (position < -1) {
        methodStructure = (JavaMethod) methodStructures.get(-(position + 2));
        if (methodStructure.getEndLine() < line) { methodStructure = null; }
      } else {
        methodStructure = null;
      }
    } else {
      methodStructure = (JavaMethod) methodStructures.get(position);
    }
    return methodStructure;    
  }

  /**
   * Returns a List of all JavaMethods inside this FileStructure ordered by startLines.
   */
  public List getMethods() {
    return this.methodStructures;
  }

  /**
   * Returns the total number of lines for this sourcefile or 0 if not defined.
   */
  public int getNrOfLines() {
    return this.nrOfLines;
  }
  
  /**
   * Returns the packagename of this sourcefile or <code>null</code> if not defined.
   */
  public String getPackage() {
    return this.packageName;
  }

  /**
   * Returns the sourcefile for this structure definition.
   */
  public File getSource() {
    return this.sourceFile;
  }

  /**
   * Returns the <code>true</code> if the given line is inside the
   * import definitions, <code>false</code> otherwise.
   */
  public boolean isImportsContext(int line) {
    return ((line >= this.importsStartLine) && (line <= this.importsEndLine));
  }

  /**
   * Defines the last line of import defintions.
   */
  public void setImportsEndLine(int line) {
    this.importsEndLine = line;
  }
  
  /**
   * Defines the first line of import defintions.
   */
  public void setImportsStartLine(int line) {
    this.importsStartLine = line;
  }

  /**
   * Defines the total number of lines.
   */  
  public void setNrOfLines(int nrOfLines) {
    this.nrOfLines = nrOfLines;
  }

  /**
   * Defines the packagename of this structure.
   * For consistency this name is set in all classStructures already
   * defined in this fileStructure as well as future additions.
   */
  public void setPackage(String name) {
    this.packageName = name;
    Iterator classIterator = classStructures.iterator();
    while (classIterator.hasNext()) {
      ((JavaClass) classIterator.next()).setPackage(name);
    }
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
   * String representation of this structure.
   */  
  public String toString() {
    StringBuffer buffer = new StringBuffer();
    buffer.append("file ");
    buffer.append(sourceFile.getPath());
    buffer.append(lineSeparator);
    if (packageName != null) {
      buffer.append("package ");
      buffer.append(packageName);
      buffer.append(lineSeparator);
    }
    if (imports != null) {        
      Iterator importIterator = imports.iterator();
      while (importIterator.hasNext()) {
        buffer.append("import ");
        buffer.append(importIterator.next());
        buffer.append(lineSeparator);
      }
    }
    Iterator classStructureIterator = classStructures.iterator();
    while (classStructureIterator.hasNext()) {
      buffer.append(classStructureIterator.next());
      buffer.append(lineSeparator);
    }
    Iterator attributeStructureIterator = attributeStructures.iterator();
    while (attributeStructureIterator.hasNext()) {
      buffer.append(attributeStructureIterator.next());
      buffer.append(lineSeparator);
    }
    Iterator methodStructureIterator = methodStructures.iterator();
    while (methodStructureIterator.hasNext()) {
      buffer.append(methodStructureIterator.next());
      buffer.append(lineSeparator);
    }
    buffer.append(nrOfLines);
    buffer.append(" lines total");
    return buffer.toString();    
  }

}
