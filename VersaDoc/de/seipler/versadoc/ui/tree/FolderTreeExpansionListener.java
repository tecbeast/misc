package de.seipler.versadoc.ui.tree;

import java.util.Enumeration;

import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.TreePath;

/**
 * @author Georg Seipler
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class FolderTreeExpansionListener implements TreeExpansionListener {

  private FolderTree folderTree;

  /**
   * Default Constructor.
   */
  public FolderTreeExpansionListener(FolderTree folderTree) {
    this.folderTree = folderTree;
  }

  /**
   * @see javax.swing.event.TreeExpansionListener#treeExpanded(TreeExpansionEvent)
   */
  public void treeExpanded(TreeExpansionEvent tee) {
    FolderTreeNode expandedNode = (FolderTreeNode) tee.getPath().getLastPathComponent();
    Enumeration children = expandedNode.children();
    while (children.hasMoreElements()) {
      FolderTreeNode node = (FolderTreeNode) children.nextElement();
      node.expand();
    }
    // make sure the user can see the lovely new node.
    folderTree.scrollPathToVisible(new TreePath(expandedNode.getPath()));
  }

  /**
   * @see javax.swing.event.TreeExpansionListener#treeCollapsed(TreeExpansionEvent)
   */
  public void treeCollapsed(TreeExpansionEvent event) {
  }

}
