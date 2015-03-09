package de.seipler.versadoc.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

import de.seipler.versadoc.ui.frame.BrowserFrame;

/**
 * 
 * @author Georg Seipler
 */
public class VersaDoc extends JFrame {

  private static final int X_OFFSET = 30;
  private static final int Y_OFFSET = 30;

  private int openFrameCount;
  private JDesktopPane desktop;

  /**
   * 
   */
  public VersaDoc() {
    super("Versadoc");

    // make the big window be indented 50 pixels from each edge of the screen
    int inset = 50;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    setBounds(inset, inset, screenSize.width - inset * 2, screenSize.height - inset * 2);

    // set up the GUI
    desktop = new JDesktopPane(); //a specialized layered pane
    createFrame(); //Create first window
    setContentPane(desktop);
    setJMenuBar(createMenuBar());

    // make dragging faster through outline dragging
    // before v1.3
    // desktop.putClientProperty("JDesktopPane.dragMode", "outline");
    // since v1.3
    // desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
    // since v1.4
    // outline seems to be slower than continous display ?
  }

  /**
   * 
   */
  protected JMenuBar createMenuBar() {
    JMenuBar menuBar = new JMenuBar();

    JMenu menu = new JMenu("View");
    menu.setMnemonic(KeyEvent.VK_V);
    JMenuItem menuItem = new JMenuItem("New");
    menuItem.setMnemonic(KeyEvent.VK_N);
    menuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        createFrame();
      }
    });
    menu.add(menuItem);
    menuBar.add(menu);

    return menuBar;
  }

  /**
   * 
   */
  protected void createFrame() {
    openFrameCount++;
    BrowserFrame frame = new BrowserFrame("View #" + openFrameCount);
    frame.setLocation(X_OFFSET * openFrameCount, Y_OFFSET * openFrameCount);
    frame.setVisible(true);
    desktop.add(frame);
    try {
      frame.setSelected(true);
    } catch (PropertyVetoException e) {
    }
  }

  /**
   * Called by the constructors to init the <code>JFrame</code> properly.
   */
  protected void frameInit() {
    super.frameInit();
    setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  /**
   * 
   */
  public static void main(String[] args) {

    // very simple look and feel handling
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception ignored) {
    }

    VersaDoc myFrame = new VersaDoc();
    myFrame.setVisible(true);

  }

}
