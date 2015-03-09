package de.seipler.bookcollection.entity;


/**
 * 
 * @author Georg Seipler
 */
public class Genre extends SecondaryEntity implements Comparable {
  
  private String name;
  
  public Genre() {
    setName("");
  }
  
  public String getName() {
    return this.name;
  }
  
  public int compareTo(Object obj) {
    int result = 0;
    if (obj instanceof Genre) {
      Genre anotherGenre = (Genre) obj;
      result = getName().compareToIgnoreCase(anotherGenre.getName());
    }
    return result;
  }

  public boolean equals(Object obj) {
    boolean result = false;
    if (obj instanceof Genre) {
      Genre anotherGenre = (Genre) obj;
      if (
        ((getName() == null) && (anotherGenre.getName() != null))
        || (getName().compareToIgnoreCase(anotherGenre.getName()) != 0)
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
    return "Genre: " + toString();
  }
  
  public String toString() {
    return getName();
  }
  
}
