package de.seipler.bookcollection.entity;


/**
 * 
 * @author Georg Seipler
 */
public class Edition extends SecondaryEntity implements Comparable {
  
  private String name;
  
  public Edition() {
    setName("");
  }
  
  public String getName() {
    return this.name;
  }
  
  public int compareTo(Object obj) {
    int result = 0;
    if (obj instanceof Edition) {
      Edition anotherEdition = (Edition) obj;
      result = getName().compareToIgnoreCase(anotherEdition.getName());
    }
    return result;
  }

  public boolean equals(Object obj) {
    boolean result = false;
    if (obj instanceof Edition) {
      Edition anotherEdition = (Edition) obj;
      if (
        ((getName() == null) && (anotherEdition.getName() != null))
        || (getName().compareToIgnoreCase(anotherEdition.getName()) != 0)
      ) {
        result = false;
      } else {
        result = true;
      }
    }
    return result;
  }

  public void setName(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Parameter name must not be null");
    }
    this.name = name;
  }
  
  public String getDescription() {
    return "Edition: " + toString();
  }
  
  public String toString() {
    return getName();
  }
  
}
