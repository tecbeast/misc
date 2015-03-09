package de.seipler.bookcollection;

/**
 * 
 * @author Georg Seipler
 */
public abstract class Entity {
  
  public static final int ID_UNDEFINED = -1;
  
  private int id;
  private boolean changed;
  
  public Entity(int id) {
    setId(id);
    setChanged(false);
  }

  public boolean isChanged() {
    return this.changed;
  }

  public int getId() {
    return this.id;
  }

  public void setChanged(boolean changed) {
    this.changed = changed;
  }

  protected void setId(int id) {
    this.id = id;
  }

}
