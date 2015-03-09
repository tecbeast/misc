package de.seipler.bookcollection.entity;

import de.seipler.util.collection.SortedArraySet;

/**
 * 
 * @author Georg Seipler
 */
public abstract class SecondaryEntity extends Entity {
  
  protected SortedArraySet parents;
  
  public SecondaryEntity() {
    this.parents = new SortedArraySet();
  }

  public boolean addParent(PrimaryEntity parent) {
    return parents.add(parent);
  }
  
  public Entity[] getChildren() {
    PrimaryEntity[] result = new PrimaryEntity[parents.size()];
    return (Entity[]) parents.toArray(result);
  }

}
