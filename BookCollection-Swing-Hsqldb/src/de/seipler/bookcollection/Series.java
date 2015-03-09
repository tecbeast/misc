package de.seipler.bookcollection;


/**
 * 
 * @author Georg Seipler
 */
public class Series extends Entity implements Comparable {
  
  private String name;
  private int booksTotal;
  
  public Series() {
    this(ID_UNDEFINED);
  }
  
  public Series(int id) {
    super(id);
    setName("");
    setBooksTotal(0);
  }
  
  public String getName() {
    return this.name;
  }
  
  public int getBooksTotal() {
    return this.booksTotal;
  }

  public int compareTo(Object obj) {
    int result = 0;
    if (obj instanceof Series) {
      Series anotherSeries = (Series) obj;
      result = getName().compareToIgnoreCase(anotherSeries.getName());
      if (result == 0) {
        if (getBooksTotal() < anotherSeries.getBooksTotal()) {
          result = -1;
        } else if (getBooksTotal() > anotherSeries.getBooksTotal()) {
          result = 1;
        }
      }
    }
    return result;
  }

  public boolean equals(Object obj) {
    boolean result = false;
    if (obj instanceof Series) {
      Series anotherSeries = (Series) obj;
      if (
        ((getName() == null) && (anotherSeries.getName() != null))
        || (getName().compareToIgnoreCase(anotherSeries.getName()) != 0)
        || (getBooksTotal() != anotherSeries.getBooksTotal())
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

  public void setBooksTotal(int total) {
    this.booksTotal = total;
  }

}
