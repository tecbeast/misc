package de.seipler.bookcollection.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.jgoodies.clearlook.ClearLookManager;
import com.jgoodies.clearlook.ClearLookMode;
import com.jgoodies.plaf.FontSizeHints;
import com.jgoodies.plaf.Options;
import com.jgoodies.plaf.plastic.PlasticLookAndFeel;
import com.jgoodies.plaf.windows.ExtWindowsLookAndFeel;

import de.seipler.bookcollection.model.BookSet;
import de.seipler.bookcollection.model.EntityCache;
import de.seipler.bookcollection.model.EntityTreeNode;

/**
 * 
 * @author Georg Seipler
 */
public class BookCollectionUi extends JFrame {

  private int minWidth;
  private int minHeight;
  private EntityCache entityCache;

  public BookCollectionUi(EntityCache entityCache) {

    super("BookCollection");
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    this.entityCache = entityCache;

    configureUi();

    DefaultMutableTreeNode root = new DefaultMutableTreeNode("Books");
    BookSet books = entityCache.getAllBooks();
    for (int i = 0; i < books.size(); i++) {
      root.add(new EntityTreeNode(books.get(i)));
    }
    
    final JTree tree = new JTree(root);
    tree.addTreeSelectionListener(new TreeSelectionListener() {

      public void valueChanged(TreeSelectionEvent e) {
        EntityTreeNode node = (EntityTreeNode) e.getPath().getLastPathComponent();
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

    Options.setDefaultIconSize(new Dimension(18, 18));
    // LookAndFeel selectedLaf = new Plastic3DLookAndFeel();
    // LookAndFeel selectedLaf = new PlasticXPLookAndFeel();
    LookAndFeel selectedLaf = new ExtWindowsLookAndFeel();
    // LookAndFeel selectedLaf = new WindowsLookAndFeel();
    // LookAndFeel selectedLaf = new MetalLookAndFeel();

    // Set font options   
    UIManager.put(Options.USE_SYSTEM_FONTS_APP_KEY, Boolean.TRUE);
    Options.setGlobalFontSizeHints(FontSizeHints.MIXED);
    Options.setUseNarrowButtons(false);

    // Global options
    Options.setTabIconsEnabled(true);
    ClearLookManager.setMode(ClearLookMode.OFF);
    ClearLookManager.setPolicy(ClearLookManager.getPolicy().getClass().getName());
    UIManager.put(Options.POPUP_DROP_SHADOW_ENABLED_KEY, Boolean.TRUE);

    // Swing Settings
    if (selectedLaf instanceof PlasticLookAndFeel) {
      PlasticLookAndFeel.setMyCurrentTheme(PlasticLookAndFeel.createMyDefaultTheme());
      PlasticLookAndFeel.setTabStyle(PlasticLookAndFeel.TAB_STYLE_DEFAULT_VALUE);
      PlasticLookAndFeel.setHighContrastFocusColorsEnabled(false);
    } else if (selectedLaf.getClass() == MetalLookAndFeel.class) {
      MetalLookAndFeel.setCurrentTheme(new DefaultMetalTheme());
    }

    // Workaround caching in MetalRadioButtonUI
    JRadioButton radio = new JRadioButton();
    radio.getUI().uninstallUI(radio);
    JCheckBox checkBox = new JCheckBox();
    checkBox.getUI().uninstallUI(checkBox);

    try {
      UIManager.setLookAndFeel(selectedLaf);
    } catch (Exception e) {
      System.out.println("Can't change L&F: " + e);
    }

  }
  
  public EntityCache getEntityCache() {
    return this.entityCache;
  }

}
