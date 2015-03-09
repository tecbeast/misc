package de.seipler.bookcollection.entity;

/**
 * 
 * @author Georg Seipler
 */
public abstract class Entity {
  
  private String id;
  private boolean changed;
  
  public Entity() {
    setChanged(true);
  }

  public boolean isChanged() {
    return this.changed;
  }

  public String getId() {
    return this.id;
  }

  public void setChanged(boolean changed) {
    this.changed = changed;
  }

  public void setId(String id) {
    this.id = id;
  }

  public abstract String getDescription();
  
  public abstract Entity[] getChildren();
  
}
