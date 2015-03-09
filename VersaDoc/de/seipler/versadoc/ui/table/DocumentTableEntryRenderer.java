package de.seipler.versadoc.ui.table;

import java.awt.Color;
import java.awt.Component;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * 
 * @author Georg Seipler
 */
public class DocumentTableEntryRenderer extends DefaultTableCellRenderer {

  public final static DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm");

  /**
   * 
   */
  public DocumentTableEntryRenderer() {
    super();
  }

  /**
   *
   * Returns the default table cell renderer.
   *
   * @param table  the <code>JTable</code>
   * @param value  the value to assign to the cell at
   *      <code>[row, column]</code>
   * @param isSelected true if cell is selected
   * @param isFocus true if cell has focus
   * @param row  the row of the cell to render
   * @param column the column of the cell to render
   * @return the default table cell renderer
   */
  public Component getTableCellRendererComponent(
    JTable table,
    Object value,
    boolean isSelected,
    boolean hasFocus,
    int row,
    int column) {

    if (isSelected) {
      super.setForeground(table.getSelectionForeground());
      super.setBackground(table.getSelectionBackground());
    } else {
      super.setForeground(table.getForeground());
      super.setBackground(table.getBackground());
    }

    setFont(table.getFont());

    /*
    if (hasFocus) {
      setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
      if (table.isCellEditable(row, column)) {
        super.setForeground(UIManager.getColor("Table.focusCellForeground"));
        super.setBackground(UIManager.getColor("Table.focusCellBackground"));
      }
    } else {
      setBorder(noFocusBorder);
    }
    */

    if (value instanceof Date) {
      setText(DATE_FORMAT.format((Date) value));
      setHorizontalAlignment(JLabel.CENTER);
    } else if (value instanceof Number) {
      setText(value.toString());
      setHorizontalAlignment(JLabel.RIGHT);
    } else if (value instanceof DocumentTableEntry) {
      setText(((DocumentTableEntry) value).getName());
      setIcon(((DocumentTableEntry) value).getIcon());
      setHorizontalAlignment(JLabel.LEFT);
    } else {
      setText(value.toString());
      setHorizontalAlignment(JLabel.LEFT);
    }

    // ---- begin optimization to avoid painting background ----
    Color back = getBackground();
    boolean colorMatch = (back != null) && (back.equals(table.getBackground())) && table.isOpaque();
    setOpaque(!colorMatch);
    // ---- end optimization to aviod painting background ----

    return this;
  }

}
