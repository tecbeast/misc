package com.balancedbytes.tools.xbmc;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
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
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker.StateValue;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

@SuppressWarnings("serial")
public class RenameXbmcEpisodes extends JFrame implements PropertyChangeListener, ActionListener {

  private JTextField fDirTextField;
  private JButton fDirButton;
  private JTextField fUrlTextField;
  private JTextField fSeasonTextField;
  private JTextPane fOutputPane;
  private JScrollPane fScrollPane;
  private JProgressBar fProgressBar;
  private JButton fUpdateButton;

  public RenameXbmcEpisodes() {

    super("RenameXbmcEpisodes");
    
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    
    Box centerBox = Box.createVerticalBox();
    centerBox.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    
    // Series directory
    
    Box dirBox1 = Box.createHorizontalBox();
    JLabel dirLabel = new JLabel("Series directory:");
    // dirLabel.setFont(dirLabel.getFont().deriveFont(Font.BOLD));
    dirBox1.add(dirLabel);
    dirBox1.add(Box.createHorizontalGlue());

    centerBox.add(dirBox1);

    fDirTextField = new JTextField("");
    fDirTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, fDirTextField.getPreferredSize().height));
    // Listen for changes in the text
    fDirTextField.getDocument().addDocumentListener(new DocumentListener() {
      public void changedUpdate(DocumentEvent e) {
        updateUrl();
      }
      public void removeUpdate(DocumentEvent e) {
        updateUrl();
      }
      public void insertUpdate(DocumentEvent e) {
        updateUrl();
      }
      private void updateUrl() {
        if ((fDirTextField.getText().length() > 0) && (fUrlTextField.getText().length() == 0)) {
          String url = findUrlShortcut(new File(fDirTextField.getText()));
          if (url != null) {
            fUrlTextField.setText(url);
          }
        }
      }
    });

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
        int option = chooser.showOpenDialog(RenameXbmcEpisodes.this);
        File selectedFile = chooser.getSelectedFile();
        if ((option == JFileChooser.APPROVE_OPTION) && (selectedFile != null)) {
          fDirTextField.setText(selectedFile.getAbsolutePath());
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

    // Series Url
    
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

    // Series Season
    
    Box seasonBox1 = Box.createHorizontalBox();
    JLabel seasonLabel = new JLabel("Series Season (optional, will use alphabetical sorting for episodes):");
    seasonBox1.add(seasonLabel);
    seasonBox1.add(Box.createHorizontalGlue());

    centerBox.add(Box.createVerticalStrut(5));
    centerBox.add(seasonBox1);

    fSeasonTextField = new JTextField("");
    fSeasonTextField.setMaximumSize(new Dimension(Integer.MAX_VALUE, fUrlTextField.getPreferredSize().height));
    
    Box seasonBox2 = Box.createHorizontalBox();
    seasonBox2.add(fSeasonTextField);
    
    centerBox.add(seasonBox2);

    // Output 
        
    Box outputBox1 = Box.createHorizontalBox();
    JLabel outputLabel = new JLabel("Renamed Files:");
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
    
    fProgressBar = new JProgressBar(0, 100);
    fProgressBar.setStringPainted(true);
    fProgressBar.setString("");
    
    Box progressBox = Box.createHorizontalBox();
    progressBox.add(fProgressBar);
    
    centerBox.add(Box.createVerticalStrut(5));
    centerBox.add(progressBox);

    centerBox.add(new Box.Filler(new Dimension(0, 5), new Dimension(0, 5), new Dimension(0, Integer.MAX_VALUE)));

    fUpdateButton = new JButton("Update Filenames");
    fUpdateButton.addActionListener(this);

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
    
    setSize(450, (int) getSize().getHeight());

  }

  /**
   * Invoked when task's progress property changes.
   */
  public void propertyChange(PropertyChangeEvent evt) {
      if ("progress".equals(evt.getPropertyName())) {
        int progress = (Integer) evt.getNewValue();
        fProgressBar.setIndeterminate(false);
        fProgressBar.setValue(progress);
        fProgressBar.setString(progress + "%");
      }
      if ("state".equals(evt.getPropertyName())) {
        StateValue state = (StateValue) evt.getNewValue();
        if (StateValue.STARTED == state) {
          try {
            fOutputPane.getDocument().remove(0, fOutputPane.getDocument().getLength());
          } catch (BadLocationException badLocationException) {
            // do nothing
          }
          fProgressBar.setValue(0);
          fUpdateButton.setEnabled(false);
          fProgressBar.setIndeterminate(true);
          fProgressBar.setString("Scan URL");
        }
        if (StateValue.DONE == state) {
          fUpdateButton.setEnabled(true);
          fProgressBar.setIndeterminate(false);
        }
      }
  }

  public void actionPerformed(ActionEvent ae) {
    File dir = new File(fDirTextField.getText());
    String url = fUrlTextField.getText();
    String season = fSeasonTextField.getText();
    RenameXbmcEpisodesTask renameTask = new RenameXbmcEpisodesTask(dir, url, fOutputPane.getDocument(), season);
    renameTask.addPropertyChangeListener(this);
    renameTask.execute();
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

    RenameXbmcEpisodes xbmcTools = new RenameXbmcEpisodes();
    xbmcTools.setVisible(true);

  }
  
  private String findUrlShortcut(File dir) {
    if ((dir == null) || !dir.isDirectory()) {
      return null;
    }
    File[] files = dir.listFiles(new FileFilter() {
      public boolean accept(File file) {
        return ((file != null) && !file.isDirectory() && file.getName().endsWith(".url"));
      }
    });
    if ((files != null) && (files.length > 0)) {
      BufferedReader in = null;
      String line = null;
      try {
        in = new BufferedReader(new FileReader(files[0]));
        while ((line = in.readLine()) != null) {
          if ((line.length() > 4) && line.startsWith("URL=")) {
            return line.substring(4);
          }
        }
      } catch (IOException ioe1) {
        return null;
      } finally {
        if (in != null) {
          try {
            in.close();
          } catch (IOException ioe2) {
            // nothing to do at this point
          }
        }
      }
    }
    return null;
  }

  public static void main(String[] args) {
    // schedule a job for the event-dispatching thread:
    // creating and showing this application's GUI.
    SwingUtilities.invokeLater(
      new Runnable() {
        public void run() {
          createAndShowUi();
        }
      }
    );
  }

}
