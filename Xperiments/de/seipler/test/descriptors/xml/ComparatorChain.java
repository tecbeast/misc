package de.seipler.test.descriptors.xml;

import java.util.Comparator;

/**
 * @author Georg Seipler
 */
public class ComparatorChain implements Comparator {
  
  private Comparator[] comparators;
  
  /**
   * 
   */
  public ComparatorChain(Comparator[] comparators) {
    this.comparators = comparators;
  }

  /**
   * 
   */
  public int compare(Object o1, Object o2) {
    int result = 0;
    for (int i = 0; i < comparators.length; i++) {
      result = comparators[i].compare(o1, o2);
      if (result != 0) { break; }
    }
    return result;
  }

}
