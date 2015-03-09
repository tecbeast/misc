package de.seipler.test.swing;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * 
 * @author Georg Seipler
 */
public class EditableComboBox  extends JFrame {

	private JTextArea textArea;
	private JComboBox comboBox;
	private static String lineSeparator = System.getProperty("line.separator", "\n");
	private static String[] labels = { "Chardonnay", "Sauvignon", "Riesling", "Cabernet", "Zinfandel", "Merlot", "Pinot Noir", "Sauvignon Blanc", "Syrah", "Gewürztraminer" };

	/**
	 * 
	 */
	protected class ComboBoxListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			textArea.append("Selected: " + comboBox.getSelectedItem());
			textArea.append(", Position: " + comboBox.getSelectedIndex());
			textArea.append(lineSeparator);
		}
	}

	/**
	 * 
	 */
	public EditableComboBox() {
		super("Editable JComboBox");
	
		this.comboBox = new JComboBox(labels);
		this.comboBox.setMaximumRowCount(5);
		this.comboBox.setEditable(true);
		this.comboBox.addActionListener(new ComboBoxListener());
		
		JPanel boxComponent = new JPanel();
		boxComponent.setLayout(new BoxLayout(boxComponent, BoxLayout.X_AXIS));
		boxComponent.add(this.comboBox);
		
		this.textArea = new JTextArea(7, 40);
		this.textArea.setEditable(false);
		
		JScrollPane textComponent = new JScrollPane(this.textArea);
		textComponent.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		textComponent.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				
		JPanel centerComponent = new JPanel();
		centerComponent.setLayout(new BoxLayout(centerComponent, BoxLayout.Y_AXIS));
		centerComponent.add(boxComponent);
		centerComponent.add(textComponent);
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(centerComponent, BorderLayout.CENTER);

		pack();		
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
		/*
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) {	}
		*/
		EditableComboBox myFrame = new EditableComboBox();
		myFrame.setVisible(true);
  }

}
