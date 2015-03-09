package de.seipler.bookcollection;

import de.seipler.bookcollection.util.SortedArraySet;

/**
 * 
 * @author Georg Seipler
 */
public class CategorySetSorted {
  
  private SortedArraySet set;
  
  public CategorySetSorted() {
    this.set = new SortedArraySet();
  }

  public int add(Category category) {
    return set.add(category);
  }

  public int findPosition(Category category) {
    return set.findPosition(category);
  }
  
  public Category get(int index) {
    return (Category) set.get(index);
  }

  public int remove(Category category) {
    return set.remove(category);
  }

  public int size() {
    return set.size();
  }

  public String[] toStringArray() {
    return set.toStringArray();
  }
  
}
