package de.seipler.versadoc.ui.table;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragSource;
import java.awt.dnd.DropTarget;
import java.io.File;
import java.util.Date;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

/**
 * @author Georg Seipler
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class DocumentTable extends JTable {

  private JPopupMenu popup;

  /**
   * Constructor for DocumentTable.
   */
  public DocumentTable(DocumentTableModel model) {
    super(model);
    
    model.addMouseListenerToHeaderInTable(this);
    setShowGrid(false);
    setIntercellSpacing(new Dimension(0, 0));
    
    // Is there a way to set a default renderer for all ?
    setDefaultRenderer(DocumentTableEntry.class, new DocumentTableEntryRenderer());
    setDefaultRenderer(Date.class, new DocumentTableEntryRenderer());
    setDefaultRenderer(Number.class, new DocumentTableEntryRenderer());
    
    JTableHeader tableHeader = getTableHeader();
    tableHeader.setDefaultRenderer(new DocumentTableHeaderRenderer());
    tableHeader.setFont(tableHeader.getFont().deriveFont(Font.BOLD));
    tableHeader.setReorderingAllowed(false);

    TableColumnModel columnModel = getColumnModel();
    columnModel.getColumn(0).setPreferredWidth(200);
    columnModel.getColumn(1).setPreferredWidth(50);
    
    DragSource dragSource = DragSource.getDefaultDragSource();
    dragSource.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY_OR_MOVE, new DocumentTableDragGestureListener());
    new DropTarget(this, new DocumentTableDropTargetListener());        
  }

  /**
   * 
   */
  public JPopupMenu getMenuForRow(int row) {
    // dummy implementation
    if (this.popup == null) {
      this.popup = new JPopupMenu();
      popup.add(new JMenuItem("Cut"));
      popup.add(new JMenuItem("Copy"));
      popup.add(new JMenuItem("Paste"));
    }
    return popup;
  }

  /**
   * 
   */
  public void updateFromFile(File file) {
    DocumentTableModel tableModel = (DocumentTableModel) getModel();
    tableModel.clear();
    File[] files = file.listFiles();
    if (files != null) {
      for (int i = 0; i < files.length; i++) {
        tableModel.addEntry(new DocumentTableEntry(files[i]));
      }
    }
  }

}
