package de.seipler.bookcollection;

import de.seipler.bookcollection.util.SortedArraySet;

/**
 * 
 * @author Georg Seipler
 */
public class PublisherSetSorted {
  
  private SortedArraySet set;
  
  public PublisherSetSorted() {
    this.set = new SortedArraySet();
  }

  public int add(Publisher publisher) {
    return set.add(publisher);
  }

  public int findPosition(Publisher publisher) {
    return set.findPosition(publisher);
  }
  
  public Publisher get(int index) {
    return (Publisher) set.get(index);
  }

  public int remove(Publisher publisher) {
    return set.remove(publisher);
  }

  public int size() {
    return set.size();
  }

  public String[] toStringArray() {
    return set.toStringArray();
  }

}
