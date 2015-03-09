package de.seipler.util.file.java;

/**
 * Container for Java visibilities, options and modifier.
 * 
 * @author Georg Seipler
 */
public class JavaModifier {

  /** <code>public</code> visibility */  
  public final static int PUBLIC    = 1;
  /** <code>protected</code> visibility */  
  public final static int PROTECTED = 2;
  /** <code>package</code> visibility */  
  public final static int PACKAGE   = 3;
  /** <code>private</code> visibility */  
  public final static int PRIVATE   = 4;
    
  private int visibility = 0;

  private boolean isAbstract = false;
  private boolean isFinal = false;
  private boolean isStatic = false;


  /**
   * Default Constructor.
   * Starting with <code>package</code> visibility and no additional modifiers.
   */
  public JavaModifier() {
    setAbstract(false);
    setFinal(false);
    setStatic(false);
    setVisibility(PACKAGE);
  }

  /**
   * Accessor to visibility (represented by integer constants, see above).
   */
  public int getVisibility() {
    return this.visibility;
  }

  /**
   * Returns <code>true</code> if modifier is <code>abstract</code>.
   */
  public boolean isAbstract() {
    return this.isAbstract;
  }  
  
  /**
   * Returns <code>true</code> if modifier is <code>final</code>.
   */  
  public boolean isFinal() {
    return this.isFinal;
  }
  
  /**
   * Returns <code>true</code> if modifier is <code>static</code>.
   */
  public boolean isStatic() {
    return this.isStatic;
  }

  /**
   * Set or remove <code>abstract</code> modifier.
   */
  public void setAbstract(boolean isAbstract) {
    this.isAbstract = isAbstract;
  }
  
  /**
   * Set or remove <code>final</code> modifier.
   */
  public void setFinal(boolean isFinal) {
    this.isFinal = isFinal;
  }
  
  /**
   * Set or remove <code>static</code> modifier.
   */
  public void setStatic(boolean isStatic) {
    this.isStatic = isStatic;
  }

  /**
   * Define visibility through integer constant (see above).
   */
  public void setVisibility(int visibility) {
    if ((visibility >= PUBLIC) && (visibility <= PRIVATE)) {
      this.visibility = visibility;
    } else {
      throw new IllegalArgumentException("Unknown visibility " + visibility);
    }
  }

  /**
   * String representation of this modifier container.
   * Suitable to build header information for classes or methods.
   */  
  public String toString() {
    StringBuffer buffer = new StringBuffer();
    switch (visibility) {
      case    PUBLIC: buffer.append("public"); break;
      case   PACKAGE: break;
      case PROTECTED: buffer.append("protected"); break;
      case   PRIVATE: buffer.append("private"); break;
    }
    if (isAbstract) { buffer.append(" abstract"); }
    if (isFinal) { buffer.append(" final"); }
    if (isStatic) { buffer.append(" static"); }
    if ((buffer.length() > 0) && (buffer.charAt(0) == ' ')) {
      buffer.delete(0, 1);
    }
    return buffer.toString();
  }

}
