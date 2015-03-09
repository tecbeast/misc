package de.seipler.web.fetchurl;

/**
 * Represents an HTML attribute.
 * 
 * @author Georg Seipler
 */
public class Attribute {

  private String name = null;
  private String value = null;


  /**
   * 
   */
  public Attribute(String name) {
    this(name, null);
  }
  
  /**
   * 
   */
  public Attribute(String name, String value) {
    setName(name);
    setValue(value);
  } 

  /**
   * 
   */
  public boolean equals(Object object) {
    if (!(object instanceof Attribute)) { return false; }
    Attribute attribute = (Attribute) object;
    return (this.name.equals(attribute.getName()) && (((this.value == null) && (attribute.getValue() == null)) || this.value.equals(attribute.getValue())));
  }

  /**
   * 
   */
  public String getName() {
    return this.name;
  }

  /**
   * 
   */
  public String getValue() {
    return this.value;
  }

  /**
   * 
   */
  public boolean isData() {
    return (this.name.toLowerCase().equals("src"));
  }
  
  /**
   * 
   */
  public boolean isLink() {
    return (this.name.toLowerCase().equals("href"));
  }

  /**
   * 
   */
  private void setName(String name) {
    if (name == null) {
      throw new IllegalArgumentException("an Attribute must have a name");
    }
    this.name = name;
  }

  /**
   * 
   */
  public void setValue(String value) {
    this.value = value;
  }

  /**
   * 
   */
  public String toString() {
    StringBuffer buffer = new StringBuffer();
    buffer.append(name);
    if (this.value != null) {
      buffer.append('=');
      buffer.append('"');
      buffer.append(this.value);
      buffer.append('"');
    }
    return buffer.toString();   
  }
  
}