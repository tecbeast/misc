package de.seipler.bookcollection;

import de.seipler.bookcollection.util.SortedArraySet;

/**
 * 
 * @author Georg Seipler
 */
public class AuthorSetSorted {
  
  private SortedArraySet set;
  
  public AuthorSetSorted() {
    this.set = new SortedArraySet();
  }
  
  public int add(Author author) {
    return set.add(author);
  }

  public int findPosition(Author author) {
    return set.findPosition(author);
  }
  
  public Author get(int index) {
    return (Author) set.get(index);
  }
  
  public int remove(Author author) {
    return set.remove(author);
  }

  public int size() {
    return set.size();
  }

  public String[] toStringArray() {
    return set.toStringArray();
  }

}
