package de.seipler.versadoc.ui.table;

import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;

/**
 * @author Georg Seipler
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class DocumentTableDragGestureListener implements DragGestureListener {

  /**
   * @see java.awt.dnd.DragGestureListener#dragGestureRecognized(DragGestureEvent)
   */
  public void dragGestureRecognized(DragGestureEvent dge) {
    DocumentTable table = (DocumentTable) dge.getComponent();
    int tableRow = table.rowAtPoint(dge.getDragOrigin());
    DocumentTableEntry selection = (DocumentTableEntry) table.getValueAt(tableRow, 0);
    if (selection != null) {
      dge.startDrag(DragSource.DefaultCopyDrop, selection, new DocumentTableDragSourceListener());
    }
  }

}
