package de.seipler.tool.mp3;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumnModel;

/**
 * 
 * @author Georg Seipler
 */
public class MP3Tool extends JFrame {

  private JComboBox editBox;
  private DefaultComboBoxModel editBoxModel;
  private ActionListener editBoxListener;
  private MP3DescriptorTableEntryRenderer tableEntryRenderer;
  
  private JTable table;
  private MP3DescriptorTableModel tableModel;

  public MP3Tool(MP3Descriptor[] mp3Descriptors) throws DescriptorException {
    
    super("MP3Tool");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    this.editBoxModel = new DefaultComboBoxModel();
    this.editBox = new JComboBox(editBoxModel);
    editBox.setEditable(true);
    editBox.setBorder(new EmptyBorder(5, 0, 5, 0));
    editBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, editBox.getPreferredSize().height));
    
    this.editBoxListener = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        int selectedColumn = table.getSelectedColumn();
        int[] selectedRows = table.getSelectedRows();
        for (int i = 0; i < selectedRows.length; i++) {
          tableModel.setValueAt(editBox.getSelectedItem(), selectedRows[i], selectedColumn);
        }
        tableEntryRenderer.setEdited(false);
        table.requestFocusInWindow();
      }
    };

    editBox.getEditor().getEditorComponent().addKeyListener(
      new KeyListener() {
        public void keyTyped(KeyEvent e) { }
        public void keyReleased(KeyEvent e) { }
        public void keyPressed(KeyEvent e) {
          if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            tableEntryRenderer.setEdited(false);
            // ((MP3DescriptorTableEntryRenderer) table.getCellRenderer(table.getSelectedRow(), table.getSelectedColumn())).setEdited(false);
            table.requestFocusInWindow();
            e.consume();
          }
        }
      }
    );


    this.tableModel = new MP3DescriptorTableModel(0, true);
    this.table = new JTable(tableModel);
    this.tableEntryRenderer = new MP3DescriptorTableEntryRenderer();
    table.setDefaultRenderer(MP3Descriptor.class, tableEntryRenderer);

    // for correct updates add tableModel first and entries after that
    for (int i = 0; i < mp3Descriptors.length; i++) {
      tableModel.addEntry(mp3Descriptors[i]);
    }

    TableColumnModel columnModel = table.getColumnModel();
    columnModel.getColumn(0).setPreferredWidth(300);
    columnModel.getColumn(1).setPreferredWidth(200);
    columnModel.getColumn(2).setPreferredWidth(100);
    columnModel.getColumn(3).setPreferredWidth(100);
    columnModel.getColumn(4).setPreferredWidth(40);
    columnModel.getColumn(5).setPreferredWidth(100);
    columnModel.getColumn(6).setPreferredWidth(40);
    columnModel.getColumn(7).setPreferredWidth(200);
    
    tableModel.addMouseListenerToTableHeader(table);

    table.getSelectionModel().addListSelectionListener(
      new ListSelectionListener() {
        public void valueChanged(ListSelectionEvent lse) {
          if (!lse.getValueIsAdjusting()) { updateEditBox(); }
        }
      }
    );
    
    table.getColumnModel().getSelectionModel().addListSelectionListener(
      new ListSelectionListener() {
        public void valueChanged(ListSelectionEvent lse) {
          if (!lse.getValueIsAdjusting()) { updateEditBox(); }
        }
      }
    );
    
    table.addKeyListener(
      new KeyListener() {
        public void keyTyped(KeyEvent e) { }
        public void keyReleased(KeyEvent e) { }
        public void keyPressed(KeyEvent e) {
          if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            tableEntryRenderer.setEdited(true);
            // ((MP3DescriptorTableEntryRenderer) table.getCellRenderer(table.getSelectedRow(), table.getSelectedColumn())).setEdited(true);
            editBox.getEditor().getEditorComponent().requestFocusInWindow();
            e.consume();
          }
        }
      }
    );

    table.addMouseListener(
      new MouseAdapter() {
        public void mousePressed(MouseEvent e) {
          tableEntryRenderer.setEdited(false);
        }
        public void mouseReleased(MouseEvent e) {
          if (e.getClickCount() == 2) {
            tableEntryRenderer.setEdited(true);
            editBox.getEditor().getEditorComponent().requestFocusInWindow();
          }
        }
      }
    );

    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.getViewport().setBackground(table.getBackground());
    scrollPane.setPreferredSize(new Dimension(1100, 500));

    JPanel centerPane = new JPanel();
    centerPane.setLayout(new BoxLayout(centerPane, BoxLayout.Y_AXIS));
    centerPane.add(editBox);
    centerPane.add(scrollPane);
    
    getContentPane().add(centerPane, BorderLayout.CENTER);
    pack();
    
  }
  
  private void updateEditBox() {
    editBox.removeActionListener(editBoxListener);
    editBoxModel.removeAllElements();
    int selectedColumn = table.getSelectedColumn();
    int[] selectedRows = table.getSelectedRows();
    String[] values = new String[selectedRows.length];
    for (int i = 0; i < selectedRows.length; i++) {
      values[i] = tableModel.getValueAt(selectedRows[i], selectedColumn).toString();
    }
    String firstValue = values[0];
    Arrays.sort(values);
    for (int i = 0; i < values.length; i++) {
      if (values[i].length() > 0) { editBoxModel.addElement(values[i]); }
    }
    editBoxModel.setSelectedItem(firstValue);
    editBox.addActionListener(editBoxListener);
  }

  public static void main(String[] args) throws DescriptorException {

    File directory = new File(args[0]);        

    File[] mp3Files = directory.listFiles(
      new FileFilter() {
        public boolean accept(File file) {
          if ((file != null) && file.isFile() && file.getName().endsWith(".mp3")) {
            return true;
          } else {
            return false;
          }
        }
      }
    );

    JFrame progressFrame = new JFrame("Scanning Files");
    JProgressBar progressBar = new JProgressBar(0, mp3Files.length);
    progressFrame.getContentPane().add(progressBar, BorderLayout.CENTER);
    progressFrame.pack();
    Dimension size = progressFrame.getSize();
    progressFrame.setSize(size.width + (size.width / 2), size.height + (size.height / 2));
    progressFrame.setLocation(100, 100);
    progressFrame.setVisible(true);

    MP3Descriptor[] mp3Descriptors = new MP3Descriptor[mp3Files.length];
    for (int i = 0; i < mp3Files.length; i++) {
      mp3Descriptors[i] = MP3Descriptor.readFromMP3(mp3Files[i]);
      progressBar.setValue(i + 1);
    }
    
    progressFrame.setVisible(false);

    try {
      JFrame myFrame = new MP3Tool(mp3Descriptors);
      myFrame.setVisible(true);
    } catch (DescriptorException de) {
      de.printStackTrace();
    }

  }

}
