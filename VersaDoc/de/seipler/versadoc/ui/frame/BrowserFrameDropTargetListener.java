package de.seipler.versadoc.ui.frame;

import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;

/**
 * @author Georg Seipler
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class BrowserFrameDropTargetListener implements DropTargetListener {

  private JInternalFrame frame;

  /**
   * Default Constructor.
   */
  public BrowserFrameDropTargetListener(JInternalFrame frame) {
    this.frame = frame;
  }

  /**
   * @see java.awt.dnd.DropTargetListener#dragEnter(DropTargetDragEvent)
   */
  public void dragEnter(DropTargetDragEvent dtde) {
    try {
      frame.setIcon(false);
      frame.setSelected(true);
    } catch (PropertyVetoException ignored) { }
  }

  /**
   * @see java.awt.dnd.DropTargetListener#dragOver(DropTargetDragEvent)
   */
  public void dragOver(DropTargetDragEvent dtde) {
  }

  /**
   * @see java.awt.dnd.DropTargetListener#dropActionChanged(DropTargetDragEvent)
   */
  public void dropActionChanged(DropTargetDragEvent dtde) {
  }

  /**
   * @see java.awt.dnd.DropTargetListener#dragExit(DropTargetEvent)
   */
  public void dragExit(DropTargetEvent dte) {
  }

  /**
   * @see java.awt.dnd.DropTargetListener#drop(DropTargetDropEvent)
   */
  public void drop(DropTargetDropEvent dtde) {
  }

}
