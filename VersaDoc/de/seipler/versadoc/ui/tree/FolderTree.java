package de.seipler.versadoc.ui.tree;

import java.awt.Component;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.dnd.Autoscroll;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragSource;
import java.awt.dnd.DropTarget;
import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.JViewport;
import javax.swing.tree.TreePath;

/**
 * 
 * @author Georg Seipler
 */
public class FolderTree extends JTree implements Autoscroll {

  public static final int AUTOSCROLL_MARGIN = 12;

  private JScrollPane scrollPane;
  private JInternalFrame internalFrame;

  /**
   * Constructor for FolderTree.
   */
  public FolderTree(FolderTreeModel model, JInternalFrame internalFrame) {
    super(model);

    this.internalFrame = internalFrame;
        
    DragSource dragSource = DragSource.getDefaultDragSource();
    dragSource.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY_OR_MOVE, new FolderTreeDragGestureListener());
    new DropTarget(this, new FolderTreeDropTargetListener(this));
  }


  // Autoscroll Interface...
  // The following code was adapted from the book:
  //    Java Swing
  //    By Robert Eckstein, Marc Loy & Dave Wood
  //    Paperback - 1221 pages 1 Ed edition (September 1998) 
  //    O'Reilly & Associates; ISBN: 156592455X 
  //
  // The relevant chapter of which can be found at:
  //    http://www.oreilly.com/catalog/jswing/chapter/dnd.beta.pdf
  
  // Ok, we’ve been told to scroll because the mouse cursor is in our
  // scroll zone.
  public void autoscroll(Point pt) {
    JScrollPane scrollPane = getScrollPane();
    if (scrollPane != null) {
      Rectangle outerBounds = getBounds();
      Rectangle innerBounds = getParent().getBounds();
      int relativeY = pt.y + outerBounds.y - innerBounds.y;
      int relativeX = pt.x + outerBounds.x - innerBounds.x;
      JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
      if (verticalBar != null) {
        if (relativeY <= AUTOSCROLL_MARGIN) {
          verticalBar.setValue(verticalBar.getValue() - verticalBar.getBlockIncrement());
        } else if (relativeY >= (innerBounds.height - AUTOSCROLL_MARGIN)) {
          verticalBar.setValue(verticalBar.getValue() + verticalBar.getBlockIncrement());
        }
      }
      JScrollBar horizontalBar = scrollPane.getHorizontalScrollBar();
      if (horizontalBar != null) {
        if (relativeX <= AUTOSCROLL_MARGIN) {
          horizontalBar.setValue(horizontalBar.getValue() - horizontalBar.getBlockIncrement());
        } else if (relativeX >= (innerBounds.width - AUTOSCROLL_MARGIN)) {
          horizontalBar.setValue(horizontalBar.getValue() + horizontalBar.getBlockIncrement());
        }
      }
    }    
  }

  // Calculate the insets for the *JTREE*, not the viewport
  // the tree is in. This makes it a bit messy.
  public Insets getAutoscrollInsets() {
    Rectangle raOuter = getBounds();
    Rectangle raInner = getParent().getBounds();
    return new Insets(
      raInner.y - raOuter.y + AUTOSCROLL_MARGIN,
      raInner.x - raOuter.x + AUTOSCROLL_MARGIN,
      raOuter.height - raInner.height - raInner.y + raOuter.y + AUTOSCROLL_MARGIN,
      raOuter.width - raInner.width - raInner.x + raOuter.x + AUTOSCROLL_MARGIN
    );
  }

  /**
   * 
   */
  private JScrollPane getScrollPane() {
    if (scrollPane == null) {    
      Component parent = getParent();
      if (parent != null && (parent instanceof JViewport)) {
        parent = parent.getParent();
        if (parent != null && (parent instanceof JScrollPane)) {
          scrollPane = (JScrollPane) parent;
        }
      }
    }
    return scrollPane;
  }

  /**
   * 
   */
  public void selectNode(FolderTreeNode node) {
    getSelectionModel().setSelectionPath(new TreePath(node.getPath()));
  }

  /**
   * 
   */
  public void setSelected(boolean isSelected) {
    if (internalFrame != null) {
      try {
        internalFrame.setSelected(isSelected);
      } catch (PropertyVetoException ignored) { }
    }
  }

}