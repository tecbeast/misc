package de.seipler.util.swing.layout.test;

import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;

import de.seipler.util.swing.layout.LayoutWrapper;
import de.seipler.util.swing.layout.LayoutWrapperFactory;

/**
 * 
 * @author Georg Seipler
 */
public class GridBagLayoutWrapperDemo {

  public static void addComponentsToPane(Container pane) {

    JButton button;
    LayoutWrapperFactory layoutFactory = new LayoutWrapperFactory();
    LayoutWrapper layout = layoutFactory.create(LayoutWrapperFactory.GRID_BAG_LAYOUT, "buttonLayout");
    pane.setLayout(layout);

    button = new JButton("Button 1");
    pane.add(button, "button1");

    button = new JButton("Button 2");
    pane.add(button, "button2");

    button = new JButton("Button 3");
    pane.add(button, "button3");

    button = new JButton("Long-Named Button 4");
    pane.add(button, "button4");

    button = new JButton("5");
    pane.add(button, "button5");
    
    try {
      layoutFactory.readConstraints(GridBagLayoutWrapperDemo.class.getResourceAsStream("layout.xml"));
    } catch (Exception all) {
      all.printStackTrace();
    }
    
  }

  /**
   * Create the GUI and show it.  For thread safety,
   * this method should be invoked from the
   * event-dispatching thread.
   */
  private static void createAndShowGUI() {
    //Make sure we have nice window decorations.
    JFrame.setDefaultLookAndFeelDecorated(true);

    //Create and set up the window.
    JFrame frame = new JFrame("GridBagLayoutWrapperDemo");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //Set up the content pane.
    addComponentsToPane(frame.getContentPane());

    //Display the window.
    frame.pack();
    frame.setVisible(true);
  }

  public static void main(String[] args) {
    System.setProperty("javax.xml.parsers.SAXParserFactory", "com.bluecast.xml.JAXPSAXParserFactory");
    //Schedule a job for the event-dispatching thread:
    //creating and showing this application's GUI.
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        createAndShowGUI();
      }
    });
  }

}
