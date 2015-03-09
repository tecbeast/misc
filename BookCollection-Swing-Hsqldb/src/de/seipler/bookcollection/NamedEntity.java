package de.seipler.bookcollection;


/**
 * 
 * @author Georg Seipler
 */
public abstract class NamedEntity extends Entity implements Comparable {
  
  private String name;
  
  public NamedEntity() {
    this(ID_UNDEFINED);
  }
  
  public NamedEntity(int id) {
    super(id);
    setName("");
  }
  
  public String getName() {
    return this.name;
  }
  
  public int compareTo(Object obj) {
    int result = 0;
    if (obj instanceof NamedEntity) {
      NamedEntity anotherEntity = (NamedEntity) obj;
      result = getName().compareToIgnoreCase(anotherEntity.getName());
    }
    return result;
  }

  public boolean equals(Object obj) {
    boolean result = false;
    if (obj instanceof NamedEntity) {
      NamedEntity anotherEntity = (NamedEntity) obj;
      if (
        ((getName() == null) && (anotherEntity.getName() != null))
        || (getName().compareToIgnoreCase(anotherEntity.getName()) != 0)
      ) {
        result = false;
      } else {
        result = true;
      }
    }
    return result;
  }

  public String toString() {
    return getName();
  }

  public void setName(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Parameter name must not be null");
    }
    this.name = name;
  }

}
