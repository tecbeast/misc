package de.seipler.bookcollection;

import de.seipler.bookcollection.util.SortedArraySet;

/**
 * 
 * @author Georg Seipler
 */
public class EditionSetSorted {
  
  private SortedArraySet set;
  
  public EditionSetSorted() {
    this.set = new SortedArraySet();
  }

  public int add(Edition edition) {
    return set.add(edition);
  }

  public int findPosition(Edition edition) {
    return set.findPosition(edition);
  }
  
  public Edition get(int index) {
    return (Edition) set.get(index);
  }

  public int remove(Edition edition) {
    return set.remove(edition);
  }

  public int size() {
    return set.size();
  }

  public String[] toStringArray() {
    return set.toStringArray();
  }
  
}
