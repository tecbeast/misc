package de.seipler.bookcollection.entity;


/**
 * 
 * @author Georg Seipler
 */
public class Language extends SecondaryEntity implements Comparable {
  
  private String name;
  
  public Language() {
    setName("");
  }
  
  public String getName() {
    return this.name;
  }
  
  public int compareTo(Object obj) {
    int result = 0;
    if (obj instanceof Language) {
      Language anotherLanguage = (Language) obj;
      result = getName().compareToIgnoreCase(anotherLanguage.getName());
    }
    return result;
  }

  public boolean equals(Object obj) {
    boolean result = false;
    if (obj instanceof Language) {
      Language anotherLanguage = (Language) obj;
      if (
        ((getName() == null) && (anotherLanguage.getName() != null))
        || (getName().compareToIgnoreCase(anotherLanguage.getName()) != 0)
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
    return "Language: " + toString();
  }
  
  public String toString() {
    return getName();
  }
  
}
