package de.seipler.versadoc.ui.tree;

import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.swing.Timer;
import javax.swing.tree.TreePath;

/**
 * 
 * @author Georg Seipler
 */
public class FolderTreeDropTargetListener implements DropTargetListener {

  public final static int EXPANSION_TIME = 1500;

  private FolderTree tree;
  private FolderTreeModel treeModel;
  private FolderTreeNode currentNode;
  private Timer timerExpansion;

  protected class ExpandAction implements ActionListener {
    public void actionPerformed(ActionEvent ae) {
      if (currentNode != null) {
        tree.expandPath(new TreePath(currentNode.getPath()));
      }
    }
  }

  /**
   * 
   */
  public FolderTreeDropTargetListener(FolderTree tree) {
    this.tree = tree;
    this.treeModel = (FolderTreeModel) tree.getModel();
    this.timerExpansion = new Timer(EXPANSION_TIME, new ExpandAction());
  }

  /**
   * @see java.awt.dnd.DropTargetListener#dragEnter(DropTargetDragEvent)
   */
  public void dragEnter(DropTargetDragEvent dtde) {
    // tree.setSelected(true);
  }

  /**
   * @see java.awt.dnd.DropTargetListener#dragExit(DropTargetEvent)
   */
  public void dragExit(DropTargetEvent dte) {
    if (currentNode != null) {
      currentNode.setDropFocus(false);
      treeModel.nodeChanged(currentNode);
      currentNode = null;
    }
    timerExpansion.stop();
  }

  /**
   * @see java.awt.dnd.DropTargetListener#dragOver(DropTargetDragEvent)
   */
  public void dragOver(DropTargetDragEvent dtde) {
    Point location = dtde.getLocation();

    TreePath path = tree.getPathForLocation(location.x, location.y);
    if (path != null) {
      FolderTreeNode node = (FolderTreeNode) path.getLastPathComponent();
      if (!node.isLeaf() && !tree.isExpanded(path)) {
        if (!timerExpansion.isRunning()) { timerExpansion.start(); }
      } else {
        timerExpansion.stop();
      }
      if (node != currentNode) {
        node.setDropFocus(true);
        treeModel.nodeChanged(node);
        if (currentNode != null) {
          currentNode.setDropFocus(false);
          treeModel.nodeChanged(currentNode);
        }
        currentNode = node;
      }
    }
  }

  /**
   * @see java.awt.dnd.DropTargetListener#drop(DropTargetDropEvent)
   */
  public synchronized void drop(DropTargetDropEvent dtde) {
    Point location = dtde.getLocation();
    TreePath path = tree.getPathForLocation(location.x, location.y);
    if (path != null) {
      FolderTreeNode node = (FolderTreeNode) path.getLastPathComponent();
      if (node != null) {
        try {
          Transferable tr = dtde.getTransferable();
          if (tr.isDataFlavorSupported(FolderTreeNode.DATA_FLAVOR)) {
            dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
            File file = (File) tr.getTransferData(FolderTreeNode.DATA_FLAVOR);
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
  }

  /**
   * @see java.awt.dnd.DropTargetListener#dropActionChanged(DropTargetDragEvent)
   */
  public void dropActionChanged(DropTargetDragEvent dtde) {
  }

  // add directly to model not to parent node
  // otherwise model doesn't know of change and won't update a visible subtree
  private void addElement(TreePath path, File element) {
    FolderTreeNode parent = (FolderTreeNode) path.getLastPathComponent();
    FolderTreeNode node = new FolderTreeNode(element);
    FolderTreeModel model = (FolderTreeModel) (tree.getModel());
    model.insertNodeInto(node, parent, parent.getChildCount());
  }

}
