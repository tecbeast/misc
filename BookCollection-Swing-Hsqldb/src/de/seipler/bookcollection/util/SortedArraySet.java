package de.seipler.bookcollection.util;


/**
 * 
 * @author Georg Seipler
 */
public class SortedArraySet {
  
  private Object[] sortedArray;
  private int size;
  private int resizeStep;
  
  /**
   * Default Constructor (Set in natural order).
   */
  public SortedArraySet() {
    this(16, 16);
  }

  /**
   * Default Constructor (Set in natural order).
   */
  public SortedArraySet(int initialSize, int resizeStep) {
    this.resizeStep = resizeStep;
    sortedArray = new Object[initialSize];
  }
  
  /**
   * Returns position of added Object within the Array (numbered from 1)
   * if this is a new Object to the Array. If an equal Object has been
   * in the Array already, returns negative position number;
   */
  public int add(Comparable obj) {
    if (obj == null) {
      return 0;
    }
    if (size == 0) {
      sortedArray[size++] = obj;
      return 1;
    }
    int pos = findInsertionPoint(obj) * -1;
    if (pos > 0) {
      resizeIfNecessary();
      System.arraycopy(sortedArray, pos - 1, sortedArray, pos, size - pos + 1);
      sortedArray[pos - 1] = obj;
      size++;
    }
    return pos;
  }

  /**
   * Returns old position of removed Object within the Set (numbered from 1).
   * Returns 0 if Object has not been found within the Set.
   */
  public int remove(Comparable obj) {
    if ((obj == null) || (size == 0)) {
      return 0;
    }
    int pos = findPosition(obj);
    if (pos <= size) {
      size--;
      System.arraycopy(sortedArray, pos, sortedArray, pos - 1, size - pos + 1);
    }
    return pos;
  }
  
  /**
   * Returns position of Object within the Set (numbered from 1).
   * Returns 0 if Object has not been found within the Set. 
   */
  public int findPosition(Comparable obj) {
    int pos = findInsertionPoint(obj);
    if (pos < 0) {
      pos = 0;
    }
    return pos;
  }

  /**
   * Get Object at given position (numbered from 1) within the Set.
   */
  public Object get(int index) {
    if ((index < 1) || (index > size)) {
      throw new IllegalArgumentException("Invalid parameter index"); 
    }
    return sortedArray[index - 1];
  }

  // Primitive Implementation - better to do a binary search.
  // Unable to user Arrays.binarySearch() because of resizing array and trailing nulls.  
  private int findInsertionPoint(Comparable obj) {
    boolean insertion = false;
    boolean equality = false;
    int pos = 0;
    while ((pos < size) && !(equality || insertion)) {
      if (obj.compareTo(sortedArray[pos]) < 0) {
        insertion = true;
      } else if (obj.compareTo(sortedArray[pos]) == 0) {
        equality = true;
      } else {
        pos++;
      }
    }
    pos++;
    if (!equality) {
      pos *= -1;
    }
    return pos;
  }

  private void resizeIfNecessary() {
    if (size >= sortedArray.length) {
      Object[] newArray = new Object[sortedArray.length + resizeStep];
      System.arraycopy(sortedArray, 0, newArray, 0, sortedArray.length);
      this.sortedArray = newArray;
    }
  }
  
  /**
   * Returns the size of the Set.
   */
  public int size() {
    return this.size;  
  }

  /**
   * Converts the Set to an Array.
   */
  public String[] toStringArray() {
    String[] result = new String[size];
    for (int i = 0; i < size; i++) {
      result[i] = sortedArray[i].toString();
    }
    return result;
  }

}
