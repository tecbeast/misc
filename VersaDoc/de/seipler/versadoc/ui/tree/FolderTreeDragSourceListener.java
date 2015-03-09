package de.seipler.versadoc.ui.tree;

import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;

/**
 * @author Georg Seipler
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class FolderTreeDragSourceListener implements DragSourceListener {

  /**
   * @see java.awt.dnd.DragSourceListener#dragEnter(DragSourceDragEvent)
   */
  public void dragEnter(DragSourceDragEvent dsde) {
    /*
    DragSourceContext context = dsde.getDragSourceContext();
    int dropAction = dsde.getDropAction();
    if ((dropAction & DnDConstants.ACTION_COPY) != 0) {
      context.setCursor(DragSource.DefaultCopyDrop);
    } else if ((dropAction & DnDConstants.ACTION_MOVE) != 0) {
      context.setCursor(DragSource.DefaultMoveDrop);
    } else {
      context.setCursor(DragSource.DefaultCopyNoDrop);
    }
    */
  }

  /**
   * @see java.awt.dnd.DragSourceListener#dragOver(DragSourceDragEvent)
   */
  public void dragOver(DragSourceDragEvent dsde) {
  }

  /**
   * @see java.awt.dnd.DragSourceListener#dropActionChanged(DragSourceDragEvent)
   */
  public void dropActionChanged(DragSourceDragEvent dsde) {
  }

  /**
   * @see java.awt.dnd.DragSourceListener#dragExit(DragSourceEvent)
   */
  public void dragExit(DragSourceEvent dse) {
  }

  /**
   * @see java.awt.dnd.DragSourceListener#dragDropEnd(DragSourceDropEvent)
   */
  public void dragDropEnd(DragSourceDropEvent dsde) {
  }

}
