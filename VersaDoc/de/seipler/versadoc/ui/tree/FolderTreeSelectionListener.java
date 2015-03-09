package de.seipler.versadoc.ui.tree;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import de.seipler.versadoc.ui.table.DocumentTable;

/**
 * @author Georg Seipler
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class FolderTreeSelectionListener implements TreeSelectionListener {

  private DocumentTable documentTable;

  /**
   * Default Constructor.
   */
  public FolderTreeSelectionListener(DocumentTable documentTable) {
    this.documentTable = documentTable;
  }

  /**
   * @see javax.swing.event.TreeSelectionListener#valueChanged(TreeSelectionEvent)
   */
  public void valueChanged(TreeSelectionEvent e) {
    FolderTreeNode node = (FolderTreeNode) e.getPath().getLastPathComponent();
    documentTable.updateFromFile(node.getFile());
  }

}
