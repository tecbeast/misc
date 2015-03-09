package de.seipler.bookcollection.entity;


/**
 * 
 * @author Georg Seipler
 */
public class Publisher extends SecondaryEntity implements Comparable {
  
  private String name;
  
  public Publisher() {
    setName("");
  }
  
  public String getName() {
    return this.name;
  }
  
  public int compareTo(Object obj) {
    int result = 0;
    if (obj instanceof Publisher) {
      Publisher anotherPublisher = (Publisher) obj;
      result = getName().compareToIgnoreCase(anotherPublisher.getName());
    }
    return result;
  }

  public boolean equals(Object obj) {
    boolean result = false;
    if (obj instanceof Publisher) {
      Publisher anotherPublisher = (Publisher) obj;
      if (
        ((getName() == null) && (anotherPublisher.getName() != null))
        || (getName().compareToIgnoreCase(anotherPublisher.getName()) != 0)
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
    return "Publisher: " + toString();
  }

  public String toString() {
    return getName();
  }
  
}
