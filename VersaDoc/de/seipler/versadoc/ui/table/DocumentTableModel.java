package de.seipler.versadoc.ui.table;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;

/**
 * Very very quick and dirty for now.
 * 
 * @author Georg Seipler
 */
public class DocumentTableModel extends AbstractTableModel {

  public final static int DEFAULT_SIZE = 100;
  public final static int SIZE_INCREMENT = 50;

  private String[] columnNames;
  private DocumentTableEntry[] entries;
  int nrOfRows;
  
  // from TableSorter
  
  int indexes[];
  boolean ascending;
  int sortColumn;

  /**
   * Constructor for DocumentTableModel.
   */
  public DocumentTableModel() {
    this(-1, true);
  }

  /**
   * Constructor for DocumentTableModel.
   */
  public DocumentTableModel(int sortColumn, boolean ascending) {
    this.sortColumn = sortColumn;
    this.ascending = ascending;
    this.columnNames = new String[] { "Filename", "Size", "Modified" };
    this.entries = new DocumentTableEntry[DEFAULT_SIZE];
  }

  /**
   * 
   */
  public String getColumnName(int col) {
    return columnNames[col];
  }

  /**
   * 
   */
  public int getRowCount() {
    return this.nrOfRows;
  }

  /**
   * 
   */
  public int getColumnCount() {
    return columnNames.length;
  }

  /**
   * 
   */
  public Object getValueAt(int row, int col) {
    // The mapping only affects the contents of the data rows.
    // Pass all requests to these rows through the mapping array: "indexes".
    return getRealValueAt(indexes[row], col);
  }

  /**
   * 
   */
  protected Object getRealValueAt(int row, int col) {
    DocumentTableEntry entry = entries[row];
    switch (col) {
      case 0: return entry;
      case 1: return entry.getLength();
      case 2: return entry.getLastModified();
    }
    return null;
  }

  /**
   * 
   */
  public Class getColumnClass(int column) {
    switch (column) {
      case 0: return DocumentTableEntry.class;
      case 1: return Long.class;
      case 2: return Date.class;
    }
    return null;
  }

  /**
   * 
   */
  public void addEntry(DocumentTableEntry entry) {
    if (nrOfRows == entries.length) {
      DocumentTableEntry[] newEntries = new DocumentTableEntry[entries.length + SIZE_INCREMENT];
      System.arraycopy(entries, 0, newEntries, 0, entries.length);
      entries = newEntries;
    }
    entries[nrOfRows++] = entry;
    reallocateIndexes();    
    fireTableRowsInserted(nrOfRows - 1, nrOfRows - 1);
  }

  /**
   * 
   */
  public void clear() {
    nrOfRows = 0;
  }

  // from TableSorter

  public int compareRowsByColumn(int row1, int row2, int column) {
    Class type = getColumnClass(column);

    // Check for nulls.

    Object o1 = getRealValueAt(row1, column);
    Object o2 = getRealValueAt(row2, column);

    // If both values are null, return 0.
    if (o1 == null && o2 == null) {
      return 0;
    } else if (o1 == null) { // Define null less than everything. 
      return -1;
    } else if (o2 == null) {
      return 1;
    }

    /*
     * We copy all returned values from the getValue call in case
     * an optimised model is reusing one object to return many
     * values.  The Number subclasses in the JDK are immutable and
     * so will not be used in this way but other subclasses of
     * Number might want to do this to save space and avoid
     * unnecessary heap allocation.
     */
    if (Comparable.class.isAssignableFrom(type)) {

      Comparable c1 = (Comparable) getRealValueAt(row1, column);
      Comparable c2 = (Comparable) getRealValueAt(row2, column);
      return c1.compareTo(c2);

    } else {
      
      Object v1 = getRealValueAt(row1, column);
      String s1 = v1.toString();
      Object v2 = getRealValueAt(row2, column);
      String s2 = v2.toString();
      int result = s1.compareTo(s2);

      if (result < 0) {
        return -1;
      } else if (result > 0) {
        return 1;
      } else {
        return 0;
      }
      
    }
  }

  public int compare(int row1, int row2) {
    int result = compareRowsByColumn(row1, row2, sortColumn);
    if (result != 0) {
      return ascending ? result : -result;
    }
    return 0;
  }

  public void reallocateIndexes() {
    int rowCount = getRowCount();

    // Set up a new array of indexes with the right number of elements
    // for the new data model.
    indexes = new int[rowCount];

    // Initialise with the identity mapping.
    for (int row = 0; row < rowCount; row++) {
      indexes[row] = row;
    }
    
    sortByColumn(sortColumn, ascending);    
  }

  public void sort(Object sender) {
    shuttlesort((int[]) indexes.clone(), indexes, 0, indexes.length);
  }

  // This is a home-grown implementation which we have not had time
  // to research - it may perform poorly in some circumstances. It
  // requires twice the space of an in-place algorithm and makes
  // NlogN assigments shuttling the values between the two
  // arrays. The number of compares appears to vary between N-1 and
  // NlogN depending on the initial order but the main reason for
  // using it here is that, unlike qsort, it is stable.
  public void shuttlesort(int from[], int to[], int low, int high) {
    if (high - low < 2) {
      return;
    }
    int middle = (low + high) / 2;
    shuttlesort(to, from, low, middle);
    shuttlesort(to, from, middle, high);

    int p = low;
    int q = middle;

    /* This is an optional short-cut; at each recursive call,
    check to see if the elements in this subset are already
    ordered.  If so, no further comparisons are needed; the
    sub-array can just be copied.  The array must be copied rather
    than assigned otherwise sister calls in the recursion might
    get out of sinc.  When the number of elements is three they
    are partitioned so that the first set, [low, mid), has one
    element and and the second, [mid, high), has two. We skip the
    optimisation when the number of elements is three or less as
    the first compare in the normal merge will produce the same
    sequence of steps. This optimisation seems to be worthwhile
    for partially ordered lists but some analysis is needed to
    find out how the performance drops to Nlog(N) as the initial
    order diminishes - it may drop very quickly.  */

    if (high - low >= 4 && compare(from[middle - 1], from[middle]) <= 0) {
      for (int i = low; i < high; i++) {
        to[i] = from[i];
      }
      return;
    }

    // A normal merge. 

    for (int i = low; i < high; i++) {
      if (q >= high || (p < middle && compare(from[p], from[q]) <= 0)) {
        to[i] = from[p++];
      } else {
        to[i] = from[q++];
      }
    }
  }

  public void swap(int i, int j) {
    int tmp = indexes[i];
    indexes[i] = indexes[j];
    indexes[j] = tmp;
  }

  public void sortByColumn(int column, boolean ascending) {
    this.sortColumn = column;
    this.ascending = ascending;
    sort(this);
    fireTableChanged(new TableModelEvent(this));
  }

  // There is no-where else to put this. 
  // Add a mouse listener to the Table to trigger a table sort 
  // when a column heading is clicked in the JTable. 
  public void addMouseListenerToHeaderInTable(JTable table) {
    table.setColumnSelectionAllowed(false);
    MouseAdapter listMouseListener = new MouseAdapter() {
      // mouseClicked is only invoked if mouse has not moved
      // it is buggy or too sensitive since some MouseEvents
      // get lost on that way - use mousePressed / mouseReleased instead
      public void mouseReleased(MouseEvent e) {
        JTable table = ((JTableHeader) e.getSource()).getTable();
        int column = table.columnAtPoint(e.getPoint());
        if (column != -1) {
          if (column != sortColumn) {
            sortByColumn(column, true);
          } else {
            sortByColumn(sortColumn, !ascending);
          }
          table.getTableHeader().repaint();
        }
      }
    };
    table.getTableHeader().addMouseListener(listMouseListener);
  }

  /**
   * Returns the sortedColumn.
   * @return int
   */
  public int getSortColumn() {
    return this.sortColumn;
  }

  /**
   * Returns the ascending.
   * @return boolean
   */
  public boolean isAscending() {
    return this.ascending;
  }
  
}
