package de.balancedbytes.tool.earthdawn;

import java.awt.Dimension;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * @author Georg Seipler
 */
public class EarthdawnToolComponent extends JComponent {

  private JFormattedTextField fStepField;
  private JFormattedTextField fKarmaField;
  private JFormattedTextField fResultField;
  private JButton fRollButton;

  public EarthdawnToolComponent() {

    // Step Panel
    
    NumberFormat stepFormatter = NumberFormat.getIntegerInstance();
    stepFormatter.setMaximumIntegerDigits(2);
    
    fStepField = new JFormattedTextField(stepFormatter);
    fStepField.setColumns(3);
    fStepField.setMaximumSize(fStepField.getPreferredSize());

    JPanel stepPanel = new JPanel();
    stepPanel.setLayout(new BoxLayout(stepPanel, BoxLayout.X_AXIS));
    stepPanel.add(new JLabel("Step"));
    stepPanel.add(Box.createRigidArea(new Dimension(5, 0)));
    stepPanel.add(fStepField);

    // Karma Panel
    
    NumberFormat karmaFormatter = NumberFormat.getIntegerInstance();
    karmaFormatter.setMaximumIntegerDigits(2);
    
    fKarmaField = new JFormattedTextField(karmaFormatter);
    fKarmaField.setColumns(3);
    fKarmaField.setMaximumSize(fKarmaField.getPreferredSize());

    JPanel karmaPanel = new JPanel();
    karmaPanel.setLayout(new BoxLayout(karmaPanel, BoxLayout.X_AXIS));
    karmaPanel.add(new JLabel("+ Karma"));
    karmaPanel.add(Box.createRigidArea(new Dimension(5, 0)));
    karmaPanel.add(fKarmaField);
    
    // Result Panel

    NumberFormat resultFormatter = NumberFormat.getIntegerInstance();
    resultFormatter.setMaximumIntegerDigits(3);
    
    fResultField = new JFormattedTextField(resultFormatter);
    fResultField.setColumns(3);
    fResultField.setEditable(false);
    fResultField.setMaximumSize(fResultField.getPreferredSize());

    JPanel resultPanel = new JPanel();
    resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.X_AXIS));
    resultPanel.add(new JLabel("="));
    resultPanel.add(Box.createRigidArea(new Dimension(5, 0)));
    resultPanel.add(fResultField);
    
    // Button Panel

    fRollButton = new JButton("Roll");
    fRollButton.setMaximumSize(fRollButton.getMinimumSize());
    
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
    buttonPanel.add(fRollButton);
    
    // putting it all together
    
    setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    
    Dimension minSize = new Dimension(5, 0);
    Dimension prefSize = minSize;
    Dimension maxSize = new Dimension(Integer.MAX_VALUE, 0);

    add(stepPanel);
    add(new Box.Filler(minSize, prefSize, maxSize));
    add(karmaPanel);
    add(new Box.Filler(minSize, prefSize, maxSize));
    add(resultPanel);
    add(new Box.Filler(minSize, prefSize, maxSize));
    add(buttonPanel);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              

  }
  
  /*

  private CompoundBorder createBorder(String title) {
    return
      BorderFactory.createCompoundBorder(
        BorderFactory.createTitledBorder(title),
        BorderFactory.createEmptyBorder(0, 2, 2, 2)
      );
  }

  // # Any valid number (Character.isDigit). 
  // ' (single quote) Escape character, used to escape any of the special formatting characters. 
  // U Any character (Character.isLetter). All lowercase letters are mapped to uppercase. 
  // L Any character (Character.isLetter). All uppercase letters are mapped to lowercase. 
  // A Any character or number (Character.isLetter or Character.isDigit). 
  // ? Any character (Character.isLetter). 
  // * Anything. 
  // H Any hex character (0-9, a-f or A-F).

  private MaskFormatter createFormatter(String s) {
    MaskFormatter formatter = null;
    try {
      formatter = new MaskFormatter(s);
    } catch (java.text.ParseException exc) {
      System.err.println("formatter is bad: " + exc.getMessage());
      System.exit(-1);
    }
    return formatter;
  }
  
  */

}
