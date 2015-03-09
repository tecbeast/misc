package de.seipler.util.collection;

import java.util.EventObject;

/**
 * 
 * @author Georg Seipler
 */
public class ContentChangeEvent extends EventObject {
  
  public static final int OBJECT_ADDED = 1;
  public static final int OBJECT_REMOVED = 2;
  public static final int OBJECT_UPDATED = 2;
  
  private int index;
  private int change;

  public ContentChangeEvent(Object source, int change, int index) {
    super(source);
    this.change = change;
    this.index = index;
  }

  public int getChange() {
    return this.change;
  }

  public int getIndex() {
    return this.index;
  }

}
