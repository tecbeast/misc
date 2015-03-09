package de.seipler.bookcollection;

import de.seipler.bookcollection.util.SortedArraySet;

/**
 * 
 * @author Georg Seipler
 */
public class SeriesSetSorted {
  
  private SortedArraySet set;
  
  public SeriesSetSorted() {
    this.set = new SortedArraySet();
  }

  public int add(Series series) {
    return set.add(series);
  }

  public int findPosition(Series series) {
    return set.findPosition(series);
  }
  
  public Series get(int index) {
    return (Series) set.get(index);
  }

  public int remove(Series series) {
    return set.remove(series);
  }

  public int size() {
    return set.size();
  }

  public String[] toStringArray() {
    return set.toStringArray();
  }

}
