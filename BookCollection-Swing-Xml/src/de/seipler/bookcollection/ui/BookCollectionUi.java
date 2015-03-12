package de.seipler.bookcollection.ui;

import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import de.seipler.bookcollection.model.BookSet;
import de.seipler.bookcollection.model.EntityCache;
import de.seipler.bookcollection.model.EntityTreeNode;

/**
 * 
 * @author Georg Seipler
 */
@SuppressWarnings("serial")
public class BookCollectionUi extends JFrame {

  private EntityCache entityCache;

  public BookCollectionUi(EntityCache entityCache) {

    super("BookCollection");
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    this.entityCache = entityCache;

    /*
    try {
      for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    } catch (Exception e) {
      // If Nimbus is not available, you can set the GUI to another look and feel.
    }
    */
    
    configureUi();

    DefaultMutableTreeNode root = new DefaultMutableTreeNode("Books");
    BookSet books = entityCache.getAllBooks();
    for (int i = 0; i < books.size(); i++) {
      root.add(new EntityTreeNode(books.get(i)));
    }
    
    final JTree tree = new JTree(root);
    tree.addTreeSelectionListener(new TreeSelectionListener() {

      public void valueChanged(TreeSelectionEvent e) {
        EntityTreeNode node = (EntityTreeNode) e.getNewLeadSelectionPath().getLastPathComponent();
        node.expand();
        TreePath path = new TreePath(node.getPath());
        tree.expandPath(path);
        // make sure the user can see the lovely new node.
        // tree.scrollPathToVisible(path);
      }
      
    });

    /*
    JTabbedPane tabbedPane = new JTabbedPane();
    tabbedPane.addTab("Books", new BookEditor(this));
    getContentPane().add(tabbedPane, BorderLayout.CENTER);
    */

    JScrollPane treeComponent = new JScrollPane(tree);
    BookEditor editorComponent = new BookEditor(this);    

    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    splitPane.setLeftComponent(treeComponent);
    splitPane.setRightComponent(editorComponent);
    getContentPane().add(splitPane, BorderLayout.CENTER);
    
    pack();
        
    addComponentListener(new ComponentAdapter() {
      private int minWidth;
      private int minHeight;
      public void componentResized(ComponentEvent e) {
        int width = getWidth();
        int height = getHeight();
        boolean resize = false;
        if (width < minWidth) {
          resize = true;
          width = minWidth;
        }
        if (height < minHeight) {
          resize = true;
          height = minHeight;
        }
        if (resize) {
          setSize(width, height);
        }
      }
      public void componentShown(ComponentEvent e) {
        minWidth = (int) getSize().getWidth();
        minHeight = (int) getSize().getHeight();
      }
    });

  }

  private void configureUi() {
    try {
      // UIManager.setLookAndFeel("com.jgoodies.looks.plastic.PlasticLookAndFeel");
      UIManager.setLookAndFeel("com.jgoodies.looks.windows.WindowsLookAndFeel");
    } catch (Exception e) {
      System.out.println("Can't change L&F: " + e);
    }
  }
  
  public EntityCache getEntityCache() {
    return this.entityCache;
  }

}
