package de.seipler.versadoc.ui.tree;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 * 
 * @author Georg Seipler
 */
public class FolderTreeCellRenderer extends DefaultTreeCellRenderer {


  /**
   * 
   */
  public FolderTreeCellRenderer() {
    super();
  }

  /**
   * Configures the renderer based on the passed in components.
   * The value is set from messaging the tree with
   * <code>convertValueToText</code>, which ultimately invokes
   * <code>toString</code> on <code>value</code>.
   * The foreground color is set based on the selection and the icon
   * is set based on selected.
   */
  public Component getTreeCellRendererComponent(
    JTree tree,
    Object value,
    boolean sel,
    boolean expanded,
    boolean leaf,
    int row,
    boolean hasFocus) {

    FolderTreeNode node = (FolderTreeNode) value;
    this.hasFocus = node.hasDropFocus();
    setText(node.toString());
    
    // there needs to be a way to specify disabled icons.
    setEnabled(tree.isEnabled());

    if (sel) {

      setForeground(getTextSelectionColor());
      if (isEnabled()) {
        setIcon(getOpenIcon());
      } else {
        setDisabledIcon(getOpenIcon());
      }

    } else {

      setForeground(getTextNonSelectionColor());
      if (isEnabled()) {
        setIcon(getClosedIcon());
      } else {
        setDisabledIcon(getClosedIcon());
      }

    }

    setComponentOrientation(tree.getComponentOrientation());
    selected = sel;

    return this;

  }

}
