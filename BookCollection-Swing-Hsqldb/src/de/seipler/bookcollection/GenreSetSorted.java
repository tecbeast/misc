package de.seipler.bookcollection;

import de.seipler.bookcollection.util.SortedArraySet;

/**
 * 
 * @author Georg Seipler
 */
public class GenreSetSorted {
  
  private SortedArraySet set;
  
  public GenreSetSorted() {
    this.set = new SortedArraySet();
  }

  public int add(Genre genre) {
    return set.add(genre);
  }

  public int findPosition(Genre genre) {
    return set.findPosition(genre);
  }
  
  public Genre get(int index) {
    return (Genre) set.get(index);
  }

  public int remove(Genre genre) {
    return set.remove(genre);
  }

  public int size() {
    return set.size();
  }

  public String[] toStringArray() {
    return set.toStringArray();
  }
  
}
