package de.seipler.bookcollection.entity;


/**
 * 
 * @author Georg Seipler
 */
public class Rating extends SecondaryEntity implements Comparable {
  
  private String name;
  private int value;
  
  public Rating() {
    setName("");
  }
  
  public String getName() {
    return this.name;
  }
  
  public int getValue() {
    return this.value;
  }
  
  public int compareTo(Object obj) {
    int result = 0;
    if (obj instanceof Rating) {
      Rating anotherRating = (Rating) obj;
      if (getValue() > anotherRating.getValue()) {
        return -1;
      } else if (getValue() < anotherRating.getValue()) {
        return 1;
      } else {
        return 0;
      }
    }
    return result;
  }

  public boolean equals(Object obj) {
    boolean result = false;
    if (obj instanceof Rating) {
      Rating anotherRating = (Rating) obj;
      if (
        ((getName() == null) && (anotherRating.getName() != null))
        || (getName().compareToIgnoreCase(anotherRating.getName()) != 0)
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
  
  public void setValue(int value) {
    this.value = value;
  }
  
  public String getDescription() {
    return "Rating: " + toString();
  }
  
  public String toString() {
    return getName();
  }
  
}
