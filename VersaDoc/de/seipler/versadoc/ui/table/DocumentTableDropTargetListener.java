package de.seipler.versadoc.ui.table;

import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;

/**
 * 
 * @author Georg Seipler
 */
public class DocumentTableDropTargetListener implements DropTargetListener {

  /**
   * 
   */
  public DocumentTableDropTargetListener() {
    super();
  }

  /**
   * @see java.awt.dnd.DropTargetListener#dragEnter(DropTargetDragEvent)
   */
  public void dragEnter(DropTargetDragEvent dtde) {
  }

  /**
   * @see java.awt.dnd.DropTargetListener#dragExit(DropTargetEvent)
   */
  public void dragExit(DropTargetEvent dte) {
  }

  /**
   * @see java.awt.dnd.DropTargetListener#dragOver(DropTargetDragEvent)
   */
  public void dragOver(DropTargetDragEvent dtde) {
  }

  /**
   * @see java.awt.dnd.DropTargetListener#drop(DropTargetDropEvent)
   */
  public synchronized void drop(DropTargetDropEvent dtde) {
    /*
    Point location = dtde.getLocation();
    TreePath path = tree.getPathForLocation(location.x, location.y);
    if (path != null) {
      FolderTreeNode node = (FolderTreeNode) path.getLastPathComponent();
      if (node != null) {
        try {
          Transferable tr = dtde.getTransferable();
          if (tr.isDataFlavorSupported(FolderTreeNode.FOLDER_TREE_NODE_FLAVOR)) {
            dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
            File file = (File) tr.getTransferData(FolderTreeNode.FOLDER_TREE_NODE_FLAVOR);
            addElement(path, file);
            dtde.dropComplete(true);
          } else if (tr.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
            dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
            List fileList = (List) tr.getTransferData(DataFlavor.javaFileListFlavor);
            Iterator iterator = fileList.iterator();
            while (iterator.hasNext()) {
              File file = (File) iterator.next();
              addElement(path, file);
            }
            dtde.dropComplete(true);
          }
        } catch (IOException ioe) {
        } catch (UnsupportedFlavorException ufe) {
        }
        node.setDropFocus(false);
        treeModel.nodeChanged(node);
      }
    }
    */
  }

  /**
   * @see java.awt.dnd.DropTargetListener#dropActionChanged(DropTargetDragEvent)
   */
  public void dropActionChanged(DropTargetDragEvent dtde) {
  }

}
