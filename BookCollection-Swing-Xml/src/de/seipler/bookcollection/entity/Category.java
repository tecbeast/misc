package de.seipler.bookcollection.entity;

/**
 * 
 * @author Georg Seipler
 */
public class Category extends SecondaryEntity implements Comparable {
  
  private String name;
  
  public Category() {
    setName("");
  }
  
  public String getName() {
    return this.name;
  }
  
  public int compareTo(Object obj) {
    int result = 0;
    if (obj instanceof Category) {
      Category anotherCategory = (Category) obj;
      result = getName().compareToIgnoreCase(anotherCategory.getName());
    }
    return result;
  }

  public boolean equals(Object obj) {
    boolean result = false;
    if (obj instanceof Category) {
      Category anotherCategory = (Category) obj;
      if (
        ((getName() == null) && (anotherCategory.getName() != null))
        || (getName().compareToIgnoreCase(anotherCategory.getName()) != 0)
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
    return "Category: " + toString();
  }

  public String toString() {
    return getName();
  }
  
}
