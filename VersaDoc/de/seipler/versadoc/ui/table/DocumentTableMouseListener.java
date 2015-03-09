package de.seipler.versadoc.ui.table;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPopupMenu;
import javax.swing.ListSelectionModel;

import de.seipler.versadoc.ui.tree.FolderTree;
import de.seipler.versadoc.ui.tree.FolderTreeModel;
import de.seipler.versadoc.ui.tree.FolderTreeNode;

/**
 * @author Georg Seipler
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class DocumentTableMouseListener implements MouseListener {

  private DocumentTable documentTable;
  private FolderTree folderTree;

  /**
   * 
   */
  public DocumentTableMouseListener(DocumentTable documentTable, FolderTree folderTree) {
    this.documentTable = documentTable;
    this.folderTree = folderTree;
  }

  /**
   * @see java.awt.event.MouseListener#mouseClicked(MouseEvent)
   */
  public void mouseClicked(MouseEvent e) {
  }

  /**
   * @see java.awt.event.MouseListener#mousePressed(MouseEvent)
   */
  public void mousePressed(MouseEvent e) {
  }

  /**
   * @see java.awt.event.MouseListener#mouseReleased(MouseEvent)
   */
  public void mouseReleased(MouseEvent e) {
    if (e.isPopupTrigger()) {
      int popupRow = documentTable.rowAtPoint(e.getPoint());
      documentTable.getSelectionModel().addSelectionInterval(popupRow, popupRow);
      JPopupMenu popupMenu = documentTable.getMenuForRow(popupRow);
      popupMenu.show(e.getComponent(), e.getX(), e.getY());
    } else {
      ListSelectionModel lsm = documentTable.getSelectionModel();
      if (!lsm.isSelectionEmpty()) {
        int selectedRow = lsm.getMinSelectionIndex();
        DocumentTableModel model = (DocumentTableModel) documentTable.getModel();
        DocumentTableEntry entry = (DocumentTableEntry) model.getValueAt(selectedRow, 0);
        if (entry.isDirectory()) {
          // System.out.println("directory");
          FolderTreeNode parentNode = (FolderTreeNode) folderTree.getLastSelectedPathComponent();
          if (parentNode == null) {
            parentNode = (FolderTreeNode) folderTree.getModel().getRoot();
          }
          FolderTreeNode childNode = ((FolderTreeModel) folderTree.getModel()).findChildNode(parentNode, entry.getFile());
          folderTree.selectNode(childNode);
        } else {
          // System.out.println("file");
        }
      }
    }
  }

  /**
   * @see java.awt.event.MouseListener#mouseEntered(MouseEvent)
   */
  public void mouseEntered(MouseEvent e) {
  }

  /**
   * @see java.awt.event.MouseListener#mouseExited(MouseEvent)
   */
  public void mouseExited(MouseEvent e) {
  }

}
