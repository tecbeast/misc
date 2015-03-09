package de.seipler.versadoc.ui.table;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

/**
 * 
 * @author Georg Seipler
 */
public class DocumentTableHeaderRenderer extends JPanel implements TableCellRenderer {
  
  private Icon sortAscending;
  private Icon sortDescending;
  private JLabel textLabel;
  private JLabel iconLabel;
  
  /**
   * 
   */
  public DocumentTableHeaderRenderer() {
    super();
    sortAscending = new ImageIcon("icons/arrow_down.gif");
    sortDescending = new ImageIcon("icons/arrow_up.gif");
    textLabel = new JLabel();
    iconLabel = new JLabel();
    iconLabel.setPreferredSize(new Dimension(15, 15));
    setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    add(Box.createRigidArea(new Dimension(15, 15)));
    add(Box.createHorizontalGlue());
    add(textLabel);
    add(Box.createHorizontalGlue());
    add(iconLabel);
    setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
  }
  
  /**
   * 
   */
  public Component getTableCellRendererComponent(
    JTable table,
    Object value,
    boolean selected,
    boolean focused,
    int row,
    int column) {

    JTableHeader header;

    if ((header = table.getTableHeader()) != null) {
      setForeground(header.getForeground());
      setBackground(header.getBackground());
      setFont(header.getFont());
    } else {
      setForeground(UIManager.getColor("TableHeader.foreground"));
      setBackground(UIManager.getColor("TableHeader.background"));
      setFont(UIManager.getFont("TableHeader.font"));
    }

    column = table.convertColumnIndexToModel(column);

    DocumentTableModel model = (DocumentTableModel) table.getModel();

    if (model.getSortColumn() == column) {
      if (model.isAscending()) {
        iconLabel.setIcon(sortAscending);
      } else {
        iconLabel.setIcon(sortDescending);
      }
    } else {
      iconLabel.setIcon(null);
    }

    textLabel.setFont(header.getFont());
    textLabel.setText(value.toString());

    return this;

  }

}
