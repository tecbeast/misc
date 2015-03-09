package de.seipler.bookcollection.entity;


/**
 * 
 * @author Georg Seipler
 */
public class Status extends SecondaryEntity implements Comparable {
  
  private String name;
  
  public Status() {
    setName("");
  }
  
  public String getName() {
    return this.name;
  }
  
  public int compareTo(Object obj) {
    int result = 0;
    if (obj instanceof Status) {
      Status anotherStatus = (Status) obj;
      result = getName().compareToIgnoreCase(anotherStatus.getName());
    }
    return result;
  }

  public boolean equals(Object obj) {
    boolean result = false;
    if (obj instanceof Status) {
      Status anotherStatus = (Status) obj;
      if (
        ((getName() == null) && (anotherStatus.getName() != null))
        || (getName().compareToIgnoreCase(anotherStatus.getName()) != 0)
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
    return "Status: " + toString();
  }
  
  public String toString() {
    return getName();
  }
  
}
