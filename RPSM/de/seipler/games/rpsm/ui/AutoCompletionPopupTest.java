package de.seipler.games.rpsm.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.TextAction;
import javax.swing.text.Utilities;

public class AutoCompletionPopupTest extends JFrame {
  
  private JTextArea textArea;
  private JTextField textField;
  private JList list;
  private DefaultListModel listModel;
  private PopupList popupList;
  
  protected static String LINE_SEPARATOR = System.getProperty("line.separator", "\n");
  protected static KeyStroke CTRL_SPACE_KEY = KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, InputEvent.CTRL_MASK);
  protected static String[] completions = new String[] {
    "Apfel",
    "Applikation",
    "apportieren",
    "Auto",
    "Bayern",
    "Birne",
    "Butterbrot"
  };
  
  public AutoCompletionPopupTest() {
    
    super("AutoCompletionPopupTest");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    this.textArea = new JTextArea(5, 40);
    textArea.setLineWrap(true);

    this.textField = new JTextField(40);
    textField.setText("This is the story of the hare who lost his spectacles.");

    this.listModel = new DefaultListModel();
    this.list = new JList(listModel);
    this.popupList = new PopupList(textField, list);
    
    popupList.addListSelectionListener(
      new ListSelectionListener() {
        public void valueChanged(ListSelectionEvent e) {
          replaceCurrentWord(textField, (String) listModel.getElementAt(e.getFirstIndex()) + " ");
        }
      }
    );
  
    textField.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          textArea.append(textField.getText());
          textArea.append(LINE_SEPARATOR);
        }
      }
    );
    
    textField.getDocument().addDocumentListener(
      new DocumentListener() {
        public void changedUpdate(DocumentEvent e) {
          // never fired in PlainDocument
        }
        public void insertUpdate(DocumentEvent e) {
          if (popupList.isShown() && (e.getLength() == 1)) {
            buildListModelWithCompletions(listModel, findCurrentWord(textField, 1), completions);
            if (listModel.size() == 0) { popupList.hide(); }
          }
        }
        public void removeUpdate(DocumentEvent e) {
          if (popupList.isShown() && (e.getLength() == 1)) {
            buildListModelWithCompletions(listModel, findCurrentWord(textField, -1), completions);
            if (listModel.size() == 0) { popupList.hide(); }
          }
        }
      }
    );
    
    Action ctrlSpaceAction = new TextAction("popup-show") {
      public void actionPerformed(ActionEvent e) {
        buildListModelWithCompletions(listModel, findCurrentWord(textField, 0), completions);
        if (listModel.size() == 1) {
          replaceCurrentWord(textField, (String) listModel.firstElement() + " ");
        }
        if (listModel.size() > 1) {
          list.setVisibleRowCount(5);
          popupList.show();
        }
      }
    };
    textField.getKeymap().addActionForKeyStroke(CTRL_SPACE_KEY, ctrlSpaceAction);

    JScrollPane scrollPane = new JScrollPane(textArea);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    getContentPane().add(scrollPane, BorderLayout.CENTER);
    getContentPane().add(textField, BorderLayout.SOUTH);
    pack();
    
  }
  
  private String findCurrentWord(JTextComponent component, int offset) {
    try {
      Document doc = component.getDocument();
      int right = component.getCaretPosition() + offset;
      int left = Utilities.getPreviousWord(component, right);
      return doc.getText(left, right - left);
    } catch (BadLocationException ble) {
      return null;
    }
  }

  private boolean replaceCurrentWord(JTextComponent component, String replacement) {
    try {
      Document doc = component.getDocument();
      int right = component.getCaretPosition();
      int left = Utilities.getPreviousWord(component, right);
      doc.remove(left, right - left);
      doc.insertString(left, replacement, null);
      return true;
    } catch (BadLocationException ble) {
      return false;
    }
  }
  
  private void buildListModelWithCompletions(DefaultListModel listModel, String word, String[] completions) {
    listModel.clear();
    if ((word != null) && (word.length() > 0)) {
      for (int i = 0; i < completions.length; i++) {
        if (completions[i].regionMatches(true, 0, word, 0, word.length())) {
          listModel.addElement(completions[i]);
        }
      }
    }
  }
  
  public static void main(String[] args) {
    
    JFrame myFrame = new AutoCompletionPopupTest();
    myFrame.setVisible(true);
    
  }
 
}
