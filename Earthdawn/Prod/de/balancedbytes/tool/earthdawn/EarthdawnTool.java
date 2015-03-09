package de.balancedbytes.tool.earthdawn;

import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;


/**
 * 
 * @author Georg Seipler
 */
public class EarthdawnTool extends JFrame {

  private int minWidth;
  private int minHeight;
  
  public EarthdawnTool() {
    
    super("EarthdawnTool");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    
    getContentPane().add(new EarthdawnToolComponent(), BorderLayout.CENTER);
    
    pack();
  
    // prohibit making the window smaller than minimum size
    // by automatically resizing it
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
  
  /**
   * 
   */
  public static void main(String[] args) {
    // Schedule a job for the event-dispatching thread:
    // creating and showing this application's GUI.
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        EarthdawnTool frame = new EarthdawnTool();
        frame.setVisible(true);
      }
    });
  }

}
