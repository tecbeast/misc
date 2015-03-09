package de.seipler.versadoc.ui.frame;

import java.awt.BorderLayout;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.util.Enumeration;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.tree.TreeSelectionModel;

import de.seipler.versadoc.ui.table.DocumentTable;
import de.seipler.versadoc.ui.table.DocumentTableModel;
import de.seipler.versadoc.ui.table.DocumentTableMouseListener;
import de.seipler.versadoc.ui.tree.FolderTree;
import de.seipler.versadoc.ui.tree.FolderTreeCellRenderer;
import de.seipler.versadoc.ui.tree.FolderTreeExpansionListener;
import de.seipler.versadoc.ui.tree.FolderTreeModel;
import de.seipler.versadoc.ui.tree.FolderTreeNode;
import de.seipler.versadoc.ui.tree.FolderTreeSelectionListener;

/**
 * @author Georg Seipler
 */
public class BrowserFrame extends JInternalFrame {

  private DocumentTable documentTable;
  private FolderTree folderTree;
  
  /**
   *
   */
  public BrowserFrame(String title) {

    // frame is resizable, closable, maximizable and iconifiable
    super(title, true, true, true, true);

    File startFile = new File("c:/");

    DropTargetListener dropSelectListener = new BrowserFrameDropTargetListener(this);
    new DropTarget(this, dropSelectListener);
    new DropTarget(getGlassPane(), dropSelectListener);
    new DropTarget(getDesktopIcon(), dropSelectListener);

    // create file table

    this.documentTable = new DocumentTable(new DocumentTableModel(0, true));
    documentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    documentTable.updateFromFile(startFile);

    JScrollPane tableComponent = new JScrollPane(documentTable);
    // make component background the same as table (white)
    tableComponent.getViewport().setBackground(documentTable.getBackground());

    // create directory tree

    FolderTreeNode rootNode = new FolderTreeNode(startFile);
    rootNode.expand();
    Enumeration children = rootNode.children();
    while (children.hasMoreElements()) {
      FolderTreeNode node = (FolderTreeNode) children.nextElement();
      node.expand();
    }

    FolderTreeModel treeModel = new FolderTreeModel(rootNode);
    this.folderTree = new FolderTree(treeModel, this);
    folderTree.putClientProperty("JTree.lineStyle", "Angled");
    folderTree.setCellRenderer(new FolderTreeCellRenderer());
    folderTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

    folderTree.addTreeSelectionListener(new FolderTreeSelectionListener(documentTable));
    folderTree.addTreeExpansionListener(new FolderTreeExpansionListener(folderTree));

    // add mouse listener for the opening of directories
    documentTable.addMouseListener(new DocumentTableMouseListener(documentTable, folderTree));

    JScrollPane treeComponent = new JScrollPane(folderTree);

    JSplitPane centerComponent = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    centerComponent.setLeftComponent(treeComponent);
    centerComponent.setRightComponent(tableComponent);

    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(centerComponent, BorderLayout.CENTER);

    // set the window size or call pack ...
    // setSize(300,300);
    pack();

  }

}
