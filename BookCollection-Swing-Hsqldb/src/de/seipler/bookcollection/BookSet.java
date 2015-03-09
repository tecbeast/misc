package de.seipler.bookcollection;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 
 * @author Georg Seipler
 */
public class BookSet {
  
  private Set set;
  
  public BookSet() {
    this.set = new HashSet();
  }

  public boolean add(Book book) {
    return set.add(book);
  }

  public boolean remove(Book book) {
    return set.remove(book);
  }
  
  public Iterator iterator() {
    return set.iterator();
  }

  public int size() {
    return set.size();
  }

}
