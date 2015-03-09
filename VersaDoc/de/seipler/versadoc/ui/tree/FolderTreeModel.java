package de.seipler.versadoc.ui.tree;

import java.io.File;
import java.util.Enumeration;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

/**
 * 
 * @author Georg Seipler
 */
public class FolderTreeModel extends DefaultTreeModel {

  /**
   * Constructor for FolderTreeModel.
   * @param root
   */
  public FolderTreeModel(TreeNode root) {
    super(root);
  }

  /**
   * 
   */
  public FolderTreeNode findChildNode(FolderTreeNode parentNode, File file) {
    Enumeration children = parentNode.children();
    while(children.hasMoreElements()) {
      FolderTreeNode child = (FolderTreeNode) children.nextElement();
      if (child.getFile().equals(file)) { return child; }
    }
    return null;
  }

}
