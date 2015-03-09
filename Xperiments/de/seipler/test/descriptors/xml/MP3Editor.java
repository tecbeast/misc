package de.seipler.test.descriptors.xml;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

/**
 * 
 * @author Georg Seipler
 */
public class MP3Editor extends JFrame {

	private JTextField trackField;
	private JTextField titleField;
	private JTextField artistField;
	private JTextField albumField;
	private JTextField yearField;
	private JComboBox genreField;
	private JTextArea commentArea;
	
	private JButton updateButton;
	private JButton cancelButton;
  
  private MP3Descriptor descriptor;

	/**
	 * Registers usage of Update Button.
	 */
	protected class UpdateButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
      descriptor.setTrack(trackField.getText());
      descriptor.setTitle(titleField.getText());
      descriptor.setArtist(artistField.getText());
      descriptor.setAlbum(albumField.getText());
      descriptor.setYear(yearField.getText());
      descriptor.setGenre(genreField.getSelectedItem().toString());
      descriptor.setComment(commentArea.getText());
      System.out.println("Update Button pressed");
      try {
        descriptor.writeTo(new PrintWriter(System.out), 0);
      } catch (IOException ioe) {
        ioe.printStackTrace();
      }
		}
	}

	/**
	 * Registers usage of Cancel Button.
	 */
	protected class CancelButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("Cancel Button pressed");
		}
	}

	/**
	 * Default constructor.
	 */
	public MP3Editor(String title) {

		super(title);

    this.descriptor = new MP3Descriptor();

		// build input area

		JPanel inputComponent = new JPanel();
		GridBagLayout gridbag = new GridBagLayout();
		inputComponent.setLayout(gridbag);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(0, 0, 3, 0);
		
		/*
		GridBagConstraints defaults = new GridBagConstraints(
			GridBagConstraints.RELATIVE, GridBagConstraints.RELATIVE,  // gridx, gridy (position)
			1, 1,                                                      // gridwidth, gridheight (number of cells)
			0.0, 0.0,                                                  // weightx, weighty (distribute space) 
			GridBagConstraints.CENTER,                                 // anchor (placing smaller components)
			GridBagConstraints.NONE,                                   // fill (resizing smaller component)
			new Insets(0, 0, 0, 0),                                    // insets (external padding)
			0, 0                                                       // ipadx, ipady (internal padding)
		);
		*/

		JLabel headerLabel = new JLabel("ID3v1 Information");
		// either: Dialog, DialogInput, Monospaced, Serif, SansSerif, or Symbol		
		headerLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		headerLabel.setForeground(Color.black);

		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 2;
		constraints.ipadx = 0;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.fill = GridBagConstraints.NONE;
		gridbag.setConstraints(headerLabel, constraints);
		inputComponent.add(headerLabel);

		JLabel trackLabel = new JLabel("Track #");

		this.trackField = new JTextField(3);
		trackField.setMaximumSize(trackField.getPreferredSize());
		trackLabel.setLabelFor(trackField);

		Box trackBox = new Box(BoxLayout.X_AXIS);
		trackBox.add(Box.createHorizontalGlue());
		trackBox.add(trackLabel);
		trackBox.add(Box.createRigidArea(new Dimension(5, 0)));
		trackBox.add(trackField);
		
		constraints.gridx = 2;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.ipadx = 0;
		constraints.anchor = GridBagConstraints.EAST;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		gridbag.setConstraints(trackBox, constraints);
		inputComponent.add(trackBox);

		JLabel titleLabel = new JLabel("Title");

		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.ipadx = 5;
		constraints.anchor = GridBagConstraints.EAST;
		constraints.fill = GridBagConstraints.NONE;
		gridbag.setConstraints(titleLabel, constraints);
		inputComponent.add(titleLabel);

		this.titleField = new JTextField(25);
		titleLabel.setLabelFor(titleField);

		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridwidth = 2;
		constraints.ipadx = 0;
		constraints.anchor = GridBagConstraints.WEST;
		gridbag.setConstraints(titleField, constraints);
		inputComponent.add(titleField);

		JLabel artistLabel = new JLabel("Artist");

		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.ipadx = 5;
		constraints.anchor = GridBagConstraints.EAST;
		constraints.fill = GridBagConstraints.NONE;
		gridbag.setConstraints(artistLabel, constraints);
		inputComponent.add(artistLabel);
		
		this.artistField = new JTextField(25);
		artistLabel.setLabelFor(artistField);
		
		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.gridwidth = 2;
		constraints.ipadx = 0;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		gridbag.setConstraints(artistField, constraints);
		inputComponent.add(artistField);

		JLabel albumLabel = new JLabel("Album");

		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.ipadx = 5;
		constraints.gridwidth = 1;
		constraints.anchor = GridBagConstraints.EAST;
		constraints.fill = GridBagConstraints.NONE;
		gridbag.setConstraints(albumLabel, constraints);
		inputComponent.add(albumLabel);

		this.albumField = new JTextField(25);
		albumLabel.setLabelFor(albumField);
		
		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.gridwidth = 2;
		constraints.ipadx = 0;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		gridbag.setConstraints(albumField, constraints);
		inputComponent.add(albumField);

		JLabel yearLabel = new JLabel("Year");
		
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		constraints.ipadx = 5;
		constraints.anchor = GridBagConstraints.EAST;
		constraints.fill = GridBagConstraints.NONE;
		gridbag.setConstraints(yearLabel, constraints);
		inputComponent.add(yearLabel);

		this.yearField = new JTextField(4);
		yearField.setMaximumSize(yearField.getPreferredSize());
		yearLabel.setLabelFor(yearField);
		
		constraints.gridx = 1;
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		constraints.ipadx = 0;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.fill = GridBagConstraints.NONE;
		gridbag.setConstraints(yearField, constraints);
		inputComponent.add(yearField);		
		
		JLabel genreLabel = new JLabel("Genre");
				
		String[] genres = { "", "Classic", "Pop", "Rock", "Techno", "Trance" };
		this.genreField = new JComboBox(genres);		

		Box genreBox = new Box(BoxLayout.X_AXIS);
		genreBox.add(Box.createHorizontalGlue());
		genreBox.add(genreLabel);
		genreBox.add(Box.createRigidArea(new Dimension(5, 0)));
		genreBox.add(genreField);

		constraints.gridx = 2;
		constraints.gridy = 4;
		constraints.gridwidth = 1;
		constraints.ipadx = 0;
		constraints.anchor = GridBagConstraints.EAST;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		gridbag.setConstraints(genreBox, constraints);
		inputComponent.add(genreBox);
		
		JLabel commentLabel = new JLabel("Comment");

		constraints.gridx = 0;
		constraints.gridy = 5;
		constraints.gridwidth = 1;
		constraints.ipadx = 5;
		constraints.anchor = GridBagConstraints.NORTHEAST;
		constraints.fill = GridBagConstraints.NONE;
		gridbag.setConstraints(commentLabel, constraints);
		inputComponent.add(commentLabel);

		this.commentArea = new JTextArea(3, 20);
		commentArea.setLineWrap(true);
		commentArea.setWrapStyleWord(true);
		commentLabel.setLabelFor(commentArea);
		JScrollPane commentAreaComponent = new JScrollPane(commentArea);
		commentAreaComponent.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		commentAreaComponent.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		constraints.gridx = 1;
		constraints.gridy = 5;
		constraints.gridwidth = 2;
		constraints.ipadx = 0;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		gridbag.setConstraints(commentAreaComponent, constraints);
		inputComponent.add(commentAreaComponent);

		// build button area

		this.updateButton = new JButton("Update");
		updateButton.setEnabled(true);
		updateButton.addActionListener(new UpdateButtonListener());    
    
		this.cancelButton = new JButton("Cancel");
		cancelButton.setEnabled(true);
		cancelButton.addActionListener(new CancelButtonListener());
    
		Box buttonBox = new Box(BoxLayout.X_AXIS);
		buttonBox.add(updateButton);
		buttonBox.add(Box.createRigidArea(new Dimension(5, 0)));
		buttonBox.add(cancelButton);
		
		// put everything together
   
		JPanel centerComponent = new JPanel();
		centerComponent.setLayout(new BoxLayout(centerComponent, BoxLayout.Y_AXIS));
		centerComponent.add(inputComponent);
		centerComponent.add(new Box.Filler(new Dimension(0, 5), new Dimension(0, 5), new Dimension(0, Short.MAX_VALUE)));
		centerComponent.add(buttonBox);
		centerComponent.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(centerComponent, BorderLayout.CENTER);
		pack();
    
    setResizable(false);
    		
	}

	/**
	 * Called by the constructors to init the <code>JFrame</code> properly.
	 */
	protected void frameInit() {
		super.frameInit();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

  /**
   * Returns Descriptor;
   */
  public MP3Descriptor getDescriptor() {
    return descriptor;
  }

	/**
	 * 
	 */
	public static void main(String[] args) {

    // very simple look and feel handling
    try {
      // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());      
      // reset Label color to Java 1.3 and earlier
      // see Java Look & Feel Design Guidelines 
      // http://java.sun.com/products/jlf/ed2/guidelines.html      
      ColorUIResource colorNew = new ColorUIResource(102, 102, 153);
      ColorUIResource colorOld = (ColorUIResource) UIManager.get("Label.foreground");
      if (colorOld.equals(colorNew) == false) {
        UIManager.put("Label.foreground", colorNew);
        UIManager.put("TitledBorder.titleColor", colorNew);
      }
    } catch (Exception ignored) {
    }
    
		MP3Editor myFrame = new MP3Editor("MP3 Editor");
		myFrame.setVisible(true);
	}

}
