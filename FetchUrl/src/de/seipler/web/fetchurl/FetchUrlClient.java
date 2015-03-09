package de.seipler.web.fetchurl;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * 
 * @author Georg Seipler
 */
public class FetchUrlClient extends JFrame {
  
  //
  // LESSONS LEARNED:
  //
  // - enforce minimum frame size through the use of a
  //   ComponentListener (see WindowResizeListener below)
  // - enforce scrolling in TextArea through the use of
  //   setCaretPosition(TextArea.getText().length())
  //

  private int minimumHeight;
  private int minimumWidth;
  private JTextField urlField;
  private JTextField fileField;
  private JButton startButton;
  private JButton stopButton;
  private JCheckBox enableRecursion;
  private JCheckBox enableLimitToHost;
  private JCheckBox enableDoNotAscend;
  private JCheckBox enableOverrideExtensions;
  private JCheckBox enableChangeToAbsolute;
  private JCheckBox enableTransform;
  private JLabel depthLabel;
  private JTextField depthField;
  private JTextArea outputArea;
  private JProgressBar progressBar;
  private FetchThread stepper;

  private String lineSeparator;

  /**
   * 
   */
  protected class WindowResizeListener extends ComponentAdapter {
    public void componentResized(ComponentEvent e) {
      boolean resize = false;     
      int width = getWidth(); int height = getHeight();
      if (width < minimumWidth) { resize = true; width = minimumWidth; }
      if (height < minimumHeight) { resize = true; height = minimumHeight; }
      if (resize) { setSize(width, height); }
    }
  }

  /**
   * 
   */
  protected class StartButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      startButton.setEnabled(false);
      stopButton.setEnabled(true);
      
      String urlString = urlField.getText();
      String directoryString = fileField.getText();
      int maxDepth = 0;
      if (depthLabel.isEnabled()) { maxDepth = Integer.parseInt(depthField.getText()); }
      
      stepper = new FetchThread(progressBar, urlString, directoryString, enableLimitToHost.isSelected(), enableDoNotAscend.isSelected(), enableOverrideExtensions.isSelected(), enableTransform.isSelected(), enableChangeToAbsolute.isSelected(), maxDepth);
      stepper.start();
    }
  }

  /**
   * 
   */
  protected class StopButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      stopButton.setEnabled(false);
      startButton.setEnabled(true);
      stepper.setIsStopped(true);
    }
  }

  /**
   * 
   */
  protected class CheckBoxListener implements ItemListener {
    public void itemStateChanged(ItemEvent e) {
      Object source = e.getItemSelectable();
      if (source == enableRecursion) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
          depthLabel.setEnabled(true);
          depthField.setEnabled(true);
				} else if (e.getStateChange() == ItemEvent.DESELECTED) {
					depthLabel.setEnabled(false);
					depthField.setEnabled(false);
				}
      } else if ((source == enableLimitToHost) && (e.getStateChange() == ItemEvent.DESELECTED)) {
				enableDoNotAscend.setSelected(false);
			} else if ((source == enableDoNotAscend) && (e.getStateChange() == ItemEvent.SELECTED)) {
				enableLimitToHost.setSelected(true);
			} else if ((source == enableTransform) && (e.getStateChange() == ItemEvent.DESELECTED)) {
        enableChangeToAbsolute.setSelected(false);
			} else if ((source == enableChangeToAbsolute) && (e.getStateChange() == ItemEvent.SELECTED)) {
        enableTransform.setSelected(true);
			}
    }
  }

  /**
   * 
   */
  protected class FetchThread extends Thread {

    private JProgressBar progressBar;
    private String startUrlString;
    private String archiveName;
    private boolean limitToHost;
    private boolean doNotAscend;
    private boolean overrideExtensions;
    private boolean transformLinks;
    private boolean changeLinksToAbsolute;
    private boolean loadData;
    private int maxDepth;
    private boolean isStopped;
    private FetchUrl fetchUrl;
    
    public FetchThread(JProgressBar bar, String startUrlString, String archiveName, boolean limitToHost, boolean doNotAscend, boolean overrideExtensions,  boolean transformLinks, boolean changeLinksToAbsolute, int maxDepth) {
      this.progressBar = bar;
      progressBar.setMinimum(0);
      this.startUrlString = startUrlString;
      this.archiveName = archiveName;
      this.limitToHost = limitToHost;
      this.doNotAscend = doNotAscend;
      this.overrideExtensions = overrideExtensions;
      this.transformLinks = transformLinks;
      this.changeLinksToAbsolute = changeLinksToAbsolute;
      this.maxDepth = maxDepth;
    }
    
    public void setIsStopped(boolean isStopped) {
      this.isStopped = isStopped;
      if (this.fetchUrl != null) { fetchUrl.setIsStopped(isStopped); }
      
    }
    
    public void run() {
   
      Runnable runner = new Runnable() {
        public void run() {
          int value = progressBar.getValue();
          progressBar.setValue(value + 1);
          progressBar.setString(Integer.toString(progressBar.getValue()) + " of " + Integer.toString(progressBar.getMaximum()));
        }
      };
      
      try {

        outputArea.setText("");

        URL startUrl = new URL(this.startUrlString);
        this.fetchUrl = new FetchUrl(startUrl, this.limitToHost, this.doNotAscend, this.overrideExtensions, this.loadData, runner);

        ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(this.archiveName));

        int depth = 0;
        List linkList = new ArrayList();
        
        linkList.add(startUrl);
        
        int nrOfSavedPages = 0;
        while ((depth <= maxDepth) && (!isStopped)) {
          
          outputLine("visiting links on depth " + depth);
          if (depth == 0) {
            resetProgressBar(1);
          } else {
            resetProgressBar(fetchUrl.findNumberOfUnsavedPageLinks(linkList));
          }
          
          nrOfSavedPages += fetchUrl.downloadAndParsePages(zipOut, linkList, (depth < maxDepth));
          
          depth++;

          Thread.sleep(500);  // show 100% bar for a little while
          
        }
        
        if (!isStopped) {

          outputLine("a total of " + nrOfSavedPages + " unique pages saved");

          outputLine("loading all data files");
          resetProgressBar(fetchUrl.findNumberOfUnsavedDataLinks(linkList));
          
          fetchUrl.downloadDataFiles(zipOut, linkList);
          
        }
  
        zipOut.close();                

        if (!isStopped && transformLinks) {

          Thread.sleep(500);  // show 100% bar for a little while
          
          ZipFile archive = new ZipFile(this.archiveName);
        
          outputLine("transforming links in all pages");
          resetProgressBar(nrOfSavedPages);
          
          fetchUrl.transformPages(archive, changeLinksToAbsolute);
          
          archive.close();
          
        }
        
        if (!isStopped) {
          
          Thread.sleep(500);  // show 100% bar for a little while
          outputLine("finished successfully");
          
        }
        
        stopButton.setEnabled(false);
        startButton.setEnabled(true);
        progressBar.setValue(0);
        progressBar.setString("Status");
        
      } catch (Exception all) {
        all.printStackTrace();
      }
    
    }
    
    /**
     * 
     */
    private void resetProgressBar(int maximum) {
      this.progressBar.setMaximum(maximum);
      this.progressBar.setValue(0);
      this.progressBar.setString(Integer.toString(progressBar.getValue()) + " of " + Integer.toString(progressBar.getMaximum()));
    }
    
    /**
     * 
     */
    private void outputLine(String line) {
      if (outputArea.getText().length() > 0) { outputArea.append(lineSeparator); }
      outputArea.append(line);
      outputArea.setCaretPosition(outputArea.getText().length());
    }

  }

  /**
   *
   */
  public FetchUrlClient(String title) {

    super(title);
    
    this.lineSeparator = System.getProperty("line.separator");
    if (lineSeparator == null) { lineSeparator = "\n"; }

    // build url area

    this.urlField = new JTextField(25);
    // urlField.addActionListener(new UrlFieldListener());
    urlField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 21));
    
    JPanel urlComponent = new JPanel();
    urlComponent.setLayout(new BoxLayout(urlComponent, BoxLayout.X_AXIS));
    urlComponent.add(urlField);
    urlComponent.setBorder(BorderFactory.createTitledBorder("URL"));

    // build directory area

    this.fileField = new JTextField(25);
    // directoryField.addActionListener(new UrlFieldListener());
    fileField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 21));
    
    JPanel fileComponent = new JPanel();
    fileComponent.setLayout(new BoxLayout(fileComponent, BoxLayout.X_AXIS));
    fileComponent.add(fileField);
    fileComponent.setBorder(BorderFactory.createTitledBorder("File"));

    // build button area

    this.startButton = new JButton("Start");
    startButton.setEnabled(true);
    startButton.addActionListener(new StartButtonListener());    
    
    this.stopButton = new JButton("Stop");
    stopButton.setEnabled(false);
    stopButton.addActionListener(new StopButtonListener());
    
    JPanel buttonComponent = new JPanel();
    buttonComponent.setLayout(new BoxLayout(buttonComponent, BoxLayout.X_AXIS));
    buttonComponent.add(startButton);
    buttonComponent.add(stopButton);
    buttonComponent.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

    // build output and progress area

    this.outputArea = new JTextArea(4, 15);
    outputArea.setEditable(false);

    JScrollPane outputAreaPane = new JScrollPane(outputArea);
    outputAreaPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    outputAreaPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    
    this.progressBar = new JProgressBar(0, 100);
    progressBar.setString("Status");
    progressBar.setStringPainted(true);
    
    JPanel progressBarPane = new JPanel();
    progressBarPane.setLayout(new BoxLayout(progressBarPane, BoxLayout.X_AXIS));
    progressBarPane.add(progressBar);
    progressBarPane.setBorder(BorderFactory.createEmptyBorder(3, 0, 2, 0));
    
    JPanel outputComponent = new JPanel();
    outputComponent.setLayout(new BoxLayout(outputComponent, BoxLayout.Y_AXIS));
    outputComponent.add(outputAreaPane);
    outputComponent.add(progressBarPane);

    // build option area

    this.depthLabel = new JLabel("depth");
    this.depthField = new JTextField("1");
    depthField.setPreferredSize(new Dimension(21, 21));
    depthField.setMaximumSize(new Dimension(21, 21));

    ItemListener checkboxListener = new CheckBoxListener();
    
    this.enableRecursion = new JCheckBox("Recursive download");
    enableRecursion.addItemListener(checkboxListener);
    enableRecursion.setMnemonic(KeyEvent.VK_R); 

    Box recursionComponent = new Box(BoxLayout.X_AXIS);
    recursionComponent.add(enableRecursion);
    recursionComponent.add(Box.createHorizontalGlue());
    recursionComponent.add(depthLabel);
    recursionComponent.add(Box.createRigidArea(new Dimension(5, 21)));
    recursionComponent.add(depthField);

		this.enableLimitToHost = new JCheckBox("Limit downloads to host");
		enableLimitToHost.addItemListener(checkboxListener);
		enableLimitToHost.setMnemonic(KeyEvent.VK_L); 

		Box limitToHostComponent = new Box(BoxLayout.X_AXIS);
		limitToHostComponent.add(enableLimitToHost);
		limitToHostComponent.add(Box.createHorizontalGlue());

		this.enableDoNotAscend = new JCheckBox("Do Not Ascend over first URL");
		enableDoNotAscend.addItemListener(checkboxListener);
		enableDoNotAscend.setMnemonic(KeyEvent.VK_A); 

		Box doNotAscendComponent = new Box(BoxLayout.X_AXIS);
		doNotAscendComponent.add(enableDoNotAscend);
		doNotAscendComponent.add(Box.createHorizontalGlue());

    this.enableTransform = new JCheckBox("Transform all links to local");
    enableTransform.addItemListener(checkboxListener);
    enableTransform.setMnemonic(KeyEvent.VK_T);
    
    Box transformComponent = new Box(BoxLayout.X_AXIS);
    transformComponent.add(enableTransform);
    transformComponent.add(Box.createHorizontalGlue());

    this.enableChangeToAbsolute = new JCheckBox("Change leftover links to absolute");
    enableChangeToAbsolute.addItemListener(checkboxListener);
    enableChangeToAbsolute.setMnemonic(KeyEvent.VK_C);
    
    Box changeToAbsoluteComponent = new Box(BoxLayout.X_AXIS);
    changeToAbsoluteComponent.add(enableChangeToAbsolute);
    changeToAbsoluteComponent.add(Box.createHorizontalGlue());

    this.enableOverrideExtensions = new JCheckBox("Override extensions according to MimeType");
    enableOverrideExtensions.addItemListener(checkboxListener);
    enableOverrideExtensions.setMnemonic(KeyEvent.VK_O);
    
    Box overrideExtensionsComponent = new Box(BoxLayout.X_AXIS);
    overrideExtensionsComponent.add(enableOverrideExtensions);
    overrideExtensionsComponent.add(Box.createHorizontalGlue());

		enableRecursion.setSelected(true);
		enableLimitToHost.setSelected(true);
		enableDoNotAscend.setSelected(false);
    enableTransform.setSelected(true);
		enableChangeToAbsolute.setSelected(true);
    enableOverrideExtensions.setSelected(true);
    
    JPanel optionComponent = new JPanel();
    optionComponent.setLayout(new BoxLayout(optionComponent, BoxLayout.Y_AXIS));
    optionComponent.add(recursionComponent);
		optionComponent.add(limitToHostComponent);
		optionComponent.add(doNotAscendComponent);
    optionComponent.add(transformComponent);
    optionComponent.add(changeToAbsoluteComponent);
    optionComponent.add(overrideExtensionsComponent);
    optionComponent.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    // put everything together
   
    JPanel centerComponent = new JPanel();
    centerComponent.setLayout(new BoxLayout(centerComponent, BoxLayout.Y_AXIS));
    centerComponent.add(urlComponent);
    centerComponent.add(fileComponent);
    centerComponent.add(optionComponent);
    centerComponent.add(buttonComponent);
    centerComponent.add(outputComponent);

    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(centerComponent, BorderLayout.CENTER);
    pack();
    
    this.minimumHeight = (int)getSize().getHeight();
    this.minimumWidth = (int)getSize().getWidth();
    addComponentListener(new WindowResizeListener());
    
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
    FetchUrlClient myFrame = new FetchUrlClient("FetchUrl");
    myFrame.setVisible(true);
    // myFrame.setResizable(false);
  }

}
