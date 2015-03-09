package de.seipler.bookcollection.model;

import javax.swing.tree.DefaultMutableTreeNode;

import de.seipler.bookcollection.entity.Entity;

/**
 * 
 * @author Georg Seipler
 */
public class EntityTreeNode extends DefaultMutableTreeNode {

  private boolean expanded;
  private Entity entity;

  public EntityTreeNode(Entity entity) {
    this.entity = entity;
  }

  public void expand() {
    if (!expanded) {
      Entity[] children = entity.getChildren();
      for (int i = 0; i < children.length; i++) {
        add(new EntityTreeNode(children[i]));
      }
      expanded = true;
    }
  }

  public String toString() {
    return entity.getDescription();
  }

}
