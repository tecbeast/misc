package de.seipler.bookcollection;

import de.seipler.bookcollection.util.SortedArraySet;

/**
 * 
 * @author Georg Seipler
 */
public class LanguageSetSorted {
  
  private SortedArraySet set;
  
  public LanguageSetSorted() {
    this.set = new SortedArraySet();
  }

  public int add(Language language) {
    return set.add(language);
  }

  public int findPosition(Language language) {
    return set.findPosition(language);
  }
  
  public Language get(int index) {
    return (Language) set.get(index);
  }

  public int remove(Language language) {
    return set.remove(language);
  }

  public int size() {
    return set.size();
  }

  public String[] toStringArray() {
    return set.toStringArray();
  }
  
}
