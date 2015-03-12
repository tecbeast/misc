package com.balancedbytes.tools.xbmc;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class XbmcTools extends JFrame {
  
  private JTextField fDirTextField;
  private JButton fDirButton;
  private JTextField fUrlTextField;
  private JTextPane fOutputPane;
  private JScrollPane fScrollPane;
  private JButton fUpdateButton;

  public XbmcTools() {

    super("XbmcTools");
    
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    
    Box centerBox = Box.createVerticalBox();
    centerBox.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    
    Box dirBox1 = Box.createHorizontalBox();
    JLabel dirLabel = new JLabel("Series directory:");
    // dirLabel.setFont(dirLabel.getFont().deriveFont(Font.BOLD));
    dirBox1.add(dirLabel);
    dirBox1.add(Box.createHorizontalGlue());

    centerBox.add(dirBox1);

    fDirTextField = new JTextField("");
    fDirTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, fDirTextField.getPreferredSize().height));

    fDirButton = new JButton();
    try {
      Image img = ImageIO.read(getClass().getResource("/com/jgoodies/looks/windows/icons/xp/TreeOpen.png"));
      fDirButton.setIcon(new ImageIcon(img));
    } catch (IOException ex) {
    }

    // create a file chooser that allows you to pick a directory
    fDirButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int option = chooser.showOpenDialog(XbmcTools.this);
        if ((option == JFileChooser.APPROVE_OPTION) && (chooser.getSelectedFile() != null)) {
          fDirTextField.setText(chooser.getSelectedFile().getAbsolutePath());
        } else {
          fDirTextField.setText("");
        }
      }
    });

    Box dirBox2 = Box.createHorizontalBox();
    dirBox2.add(fDirTextField);
    dirBox2.add(Box.createHorizontalStrut(5));
    dirBox2.add(fDirButton);

    centerBox.add(dirBox2);
    
    Box urlBox1 = Box.createHorizontalBox();
    JLabel urlLabel = new JLabel("Series URL on www.thetvdb.com:");
    // urlLabel.setFont(urlLabel.getFont().deriveFont(Font.BOLD));
    urlBox1.add(urlLabel);
    urlBox1.add(Box.createHorizontalGlue());

    centerBox.add(Box.createVerticalStrut(5));
    centerBox.add(urlBox1);

    fUrlTextField = new JTextField("");
    fUrlTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, fUrlTextField.getPreferredSize().height));
    
    Box urlBox2 = Box.createHorizontalBox();
    urlBox2.add(fUrlTextField);
    
    centerBox.add(urlBox2);

    Box outputBox1 = Box.createHorizontalBox();
    JLabel outputLabel = new JLabel("Output:");
    // urlLabel.setFont(urlLabel.getFont().deriveFont(Font.BOLD));
    outputBox1.add(outputLabel);
    outputBox1.add(Box.createHorizontalGlue());

    centerBox.add(Box.createVerticalStrut(5));
    centerBox.add(outputBox1);

    fOutputPane = new JTextPane();
    fScrollPane = new JScrollPane(fOutputPane);
    fScrollPane.setPreferredSize(new Dimension(fScrollPane.getPreferredSize().width, 100));
    fScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    
    Box outputBox2 = Box.createHorizontalBox();
    outputBox2.add(fScrollPane);

    centerBox.add(outputBox2);

    centerBox.add(new Box.Filler(new Dimension(0, 5), new Dimension(0, 5), new Dimension(0, Integer.MAX_VALUE)));

    fUpdateButton = new JButton("Update Filenames");

    JPanel updatePanel = new JPanel();
    updatePanel.setLayout(new BoxLayout(updatePanel, BoxLayout.X_AXIS));
    updatePanel.add(Box.createHorizontalGlue());
    updatePanel.add(fUpdateButton);
    updatePanel.add(Box.createHorizontalGlue());
    
    centerBox.add(updatePanel);

    centerBox.add(Box.createHorizontalGlue());

    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(centerBox, BorderLayout.CENTER);
    pack();
    
    setSize(300, (int) getSize().getHeight());

  }

  /**
   * Create the GUI and show it. For thread safety, this method should be
   * invoked from the event-dispatching thread.
   */
  private static void createAndShowUi() {

    try {
      // UIManager.setLookAndFeel("com.jgoodies.looks.plastic.PlasticLookAndFeel");
      UIManager.setLookAndFeel("com.jgoodies.looks.windows.WindowsLookAndFeel");
    } catch (Exception e) {
      System.err.println("Can't change Look&Feel: " + e);
    }

    // make sure we have nice window decorations.
    JFrame.setDefaultLookAndFeelDecorated(true);

    XbmcTools xbmcTools = new XbmcTools();
    xbmcTools.setVisible(true);

  }

  public static void main(String[] args) {
    // schedule a job for the event-dispatching thread:
    // creating and showing this application's GUI.
    javax.swing.SwingUtilities.invokeLater(
      new Runnable() {
        public void run() {
          createAndShowUi();
        }
      }
    );
  }

}
