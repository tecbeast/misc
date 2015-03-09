package de.seipler.util.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.SortedSet;


/**
 * 
 * @author Georg Seipler
 */
public class SortedArraySet implements SortedSet {
  
  public static final int DEFAULT_CAPACITY = 16;

  protected List contents;
  protected List listeners;  
  protected Comparator comparator;
  
  /**
   * Default Constructor.
   */
  public SortedArraySet() {
    this(null, DEFAULT_CAPACITY);  // Set will be sorted via Comparable interface
  }

  /**
   * Default Constructor.
   */
  public SortedArraySet(Comparator comparator) {
    this(comparator, DEFAULT_CAPACITY);  // Set will be sorted via Comparable interface
  }

  /**
   * Default Constructor.
   */
  public SortedArraySet(Comparator comparator, int initialCapacity) {
    this.comparator = comparator;
    this.contents = new ArrayList(initialCapacity);
    this.listeners = new ArrayList();
  }
  
  /**
   * Adds a ContentChangeListener.
   */
  public void addListener(ContentChangeListener listener) {
    listeners.add(listener);
  }
  
  /**
   * Removes a ContentChangeListener.
   */
  public void removeListener(ContentChangeListener listener) {
    listeners.remove(listener);
  }
  
  /**
   * @see java.util.Collection#add(java.lang.Object)
   */
  public boolean add(Object obj) {
    int index = findInsertion((Comparable) obj);
    if (index >= 0) {
      contents.add(index, obj);
      if (listeners.size() > 0) {
        fireListeners(new ContentChangeEvent(this, ContentChangeEvent.OBJECT_ADDED, index));
      }
      return true;
    } else {
      return false;
    }
  }

  /**
   * @see java.util.Collection#remove(java.lang.Object)
   */
  public boolean remove(Object obj) {
    boolean successful = false;
    int index = find(obj);
    if (index >= 0) {
      contents.remove(index);
      if (listeners.size() > 0) {
        fireListeners(new ContentChangeEvent(this, ContentChangeEvent.OBJECT_REMOVED, index));
      }
      successful = true;
    }
    return successful;
  }
  
  /**
   * Returns position of Object within the Set (numbered from 0).
   * Returns -1 if Object has not been found within the Set. 
   */
  public int find(Object obj) {
    int index = -1;
    if (obj != null) {
      index = contents.indexOf(obj); 
    }
    return index;
  }

  /**
   * Returns Object at given index (numbered from 0) within the Set.
   */
  public Object get(int index) {
    return contents.get(index);
  }

  /**
   * Updates the given object within the Set.
   */
  public void update(Object obj) {
    int index = find(obj);
    if ((index >= 0) && (listeners.size() > 0)) {
      fireListeners(new ContentChangeEvent(this, ContentChangeEvent.OBJECT_UPDATED, index));
    }
  }

  /**
   * @see java.util.Collection#size()
   */
  public int size() {
    return contents.size();  
  }
  
  /**
   * @see java.util.Collection#addAll(java.util.Collection)
   */
  public boolean addAll(Collection c) {
    boolean changed = false;
    Iterator iterator = c.iterator();
    while (iterator.hasNext()) {
      changed |= add(iterator.next());
    }
    return changed;
  }

  /**
   * @see java.util.Collection#clear()
   */
  public void clear() {
    for (int i = 0; i < size(); i++) {
      remove(get(i));
    }
  }

  /**
   * @see java.util.Collection#contains(java.lang.Object)
   */
  public boolean contains(Object o) {
    return contents.contains(o);
  }

  /**
   * @see java.util.Collection#containsAll(java.util.Collection)
   */
  public boolean containsAll(Collection c) {
    return contents.containsAll(c);
  }

  /**
   * @see java.util.Collection#isEmpty()
   */
  public boolean isEmpty() {
    return contents.isEmpty();
  }

  /**
   * @see java.util.Collection#iterator()
   */
  public Iterator iterator() {
    return contents.iterator();
  }

  /**
   * @see java.util.Collection#removeAll(java.util.Collection)
   */
  public boolean removeAll(Collection c) {
    boolean changed = false;
    Iterator iterator = c.iterator();
    while (iterator.hasNext()) {
      changed |= remove(iterator.next());
    }
    return changed;
  }

  /**
   * @see java.util.Collection#retainAll(java.util.Collection)
   */
  public boolean retainAll(Collection c) {
    boolean changed = false;
    Iterator iterator = c.iterator();
    while (iterator.hasNext()) {
      Object o = iterator.next();
      if (!contains(o)) {
        changed |= remove(o);
      }
    }
    return changed;    
  }

  /**
   * @see java.util.Collection#toArray()
   */
  public Object[] toArray() {
    return contents.toArray();
  }

  /**
   * @see java.util.Collection#toArray(java.lang.Object[])
   */
  public Object[] toArray(Object[] a) {
    return contents.toArray(a);
  }
  
  /**
   * @see java.util.SortedSet#comparator()
   */
  public Comparator comparator() {
    return this.comparator;
  }

  /**
   * @see java.util.SortedSet#first()
   */
  public Object first() {
    if (size() == 0) {
      throw new NoSuchElementException("There is no first element in an empty set.");
    }
    return get(0);
  }

  /**
   * @see java.util.SortedSet#headSet(java.lang.Object)
   */
  public SortedSet headSet(Object toElement) {
    int toPosition = find(toElement);
    if (toPosition < 0) {
      throw new IllegalArgumentException("Parameter toElement is not contained in the set.");
    }
    return subSet(0, toPosition);
  }

  /**
   * @see java.util.SortedSet#last()
   */
  public Object last() {
    if (size() == 0) {
      throw new NoSuchElementException("There is no last element in an empty set.");
    }
    return get(size() - 1);
  }

  /**
   * @see java.util.SortedSet#subSet(java.lang.Object, java.lang.Object)
   */
  public SortedSet subSet(Object fromElement, Object toElement) {
    int fromPosition = find(fromElement);
    if (fromPosition < 0) {
      throw new IllegalArgumentException("Parameter fromElement is not contained in the set.");
    }
    int toPosition = find(toElement);
    if (toPosition < 0) {
      throw new IllegalArgumentException("Parameter toElement is not contained in the set.");
    }
    return subSet(fromPosition, toPosition);
  }

  /**
   * @see java.util.SortedSet#tailSet(java.lang.Object)
   */
  public SortedSet tailSet(Object fromElement) {
    int fromPosition = find(fromElement);
    if (fromPosition < 0) {
      throw new IllegalArgumentException("Parameter fromElement is not contained in the set.");
    }
    return subSet(fromPosition, size());
  }

  /**
   * Notifies all listeners that the content has changed in some way.
   */
  protected void fireListeners(ContentChangeEvent event) {
    Iterator iterator = listeners.iterator();
    while (iterator.hasNext()) {
      ContentChangeListener listener = (ContentChangeListener) iterator.next();
      listener.handleContentChange(event);
    }
  }
  
  /**
   * Find the insertion point for the given object.
   * Note: primitive implementation - better to do a binary search ! 
   */
  protected int findInsertion(Comparable obj) {
    int index = contents.size();
    if (index > 0) {
      for (int i = 0; i < contents.size(); i++) {
        if (obj.compareTo(contents.get(i)) < 0) {
          index = i;
          break;
        }
        if (obj.compareTo(contents.get(i)) == 0) {
          index = -1;
          break;
        }
      }
    }
    return index;
  }
  
  /**
   * Returns a slice of the set including fromPosition excluding toPosition. 
   */
  protected SortedArraySet subSet(int fromPosition, int toPosition) {
    SortedArraySet result = new SortedArraySet(comparator());
    for (int i = fromPosition; i < toPosition; i++) {
      result.add(get(i));
    }
    return result;
  }
  
}
