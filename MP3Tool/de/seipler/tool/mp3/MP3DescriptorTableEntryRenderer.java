package de.seipler.tool.mp3;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * 
 * @author Georg Seipler
 */
public class MP3DescriptorTableEntryRenderer extends DefaultTableCellRenderer {
  
  private boolean isEdited;

  /**
   * 
   */
  public MP3DescriptorTableEntryRenderer() {
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

    if (hasFocus) {
      setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
      super.setForeground(UIManager.getColor("Table.focusCellForeground"));
      super.setBackground(UIManager.getColor("Table.focusCellBackground"));
    } else if (isEdited) {
      setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
    } else {
      setBorder(noFocusBorder);
    }

    if (value instanceof Number) {
      setText(value.toString());
      setHorizontalAlignment(JLabel.RIGHT);
    } else {
      setText(value.toString());
      setHorizontalAlignment(JLabel.LEFT);
    }

    // ---- begin optimization to avoid painting background ----
    Color back = getBackground();
    boolean colorMatch = (back != null) && (back.equals(table.getBackground())) && table.isOpaque();
    setOpaque(!colorMatch);
    // ---- end optimization to avoid painting background ----

    return this;
    
  }

  /**
   *
   */
  public boolean isEdited() {
    return isEdited;
  }

  /**
   *
   */
  public void setEdited(boolean b) {
    isEdited = b;
  }

}
