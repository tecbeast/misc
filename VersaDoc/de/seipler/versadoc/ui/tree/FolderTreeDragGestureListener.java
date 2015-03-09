package de.seipler.versadoc.ui.tree;

import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

/**
 * @author Georg Seipler
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class FolderTreeDragGestureListener implements DragGestureListener {

  /**
   * @see java.awt.dnd.DragGestureListener#dragGestureRecognized(DragGestureEvent)
   */
  public void dragGestureRecognized(DragGestureEvent dge) {
    JTree tree = (JTree) dge.getComponent();
    TreePath path = tree.getSelectionPath();
    if (path != null) {
      FolderTreeNode selection = (FolderTreeNode) path.getLastPathComponent();
      dge.startDrag(DragSource.DefaultCopyDrop, selection, new FolderTreeDragSourceListener());
    }
  }

}
