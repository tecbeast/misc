package de.seipler.tool.mp3;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;


/**
 * Quick and dirty for now.
 * 
 * @author Georg Seipler
 */
public class MP3DescriptorTableModel extends AbstractTableModel {

  protected static final int DEFAULT_SIZE = 100;
  protected static final int SIZE_INCREMENT = 50;
  
  protected static final int FILENAME_INDEX = 0;
  protected static final int TITLE_INDEX = 1;
  protected static final int ARTIST_INDEX = 2;
  protected static final int ALBUM_INDEX = 3;
  protected static final int TRACK_INDEX = 4;
  protected static final int GENRE_INDEX = 5;
  protected static final int YEAR_INDEX = 6;
  protected static final int COMMENT_INDEX = 7;

  private String[] columnNames;
  private MP3Descriptor[] entries;
  private int nrOfRows;
  
  // from TableSorter
  private int indexes[];
  private boolean ascending;
  private int sortColumn;

  /**
   *
   */
  public MP3DescriptorTableModel(int sortColumn, boolean ascending) {
    this.sortColumn = sortColumn;
    this.ascending = ascending;
    this.columnNames = new String[] {
      "Filename",
      "Title",
      "Artist",
      "Album",
      "Track",
      "Genre",
      "Year",
      "Comment"
    };
    this.entries = new MP3Descriptor[DEFAULT_SIZE];
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
  public Object getValueAt(int rowIndex, int columnIndex) {
    // The mapping only affects the contents of the data rows.
    // Pass all requests to these rows through the mapping array: "indexes".
    return getRealValueAt(indexes[rowIndex], columnIndex);
  }

  /**
   * 
   */
  protected Object getRealValueAt(int rowIndex, int columnIndex) {
    MP3Descriptor entry = entries[rowIndex];
    switch (columnIndex) {
      case FILENAME_INDEX:
        return entry.getFilename().trim();
      case TITLE_INDEX:
        return entry.getTitle().trim();
      case ARTIST_INDEX:
        return entry.getArtist().trim();
      case ALBUM_INDEX:
        return entry.getAlbum().trim();
      case TRACK_INDEX:
        String track = entry.getTrack().trim();
        if ((track != null) && (track.length() > 0)) {
          return new Integer(track);
        } else {
          return "";
        }
      case GENRE_INDEX:
        return entry.getGenre().trim();
      case YEAR_INDEX:
        String year = entry.getYear().trim();
        if ((year != null) && (year.length() > 0)) {
          return new Integer(year);
        } else {
          return "";
        }
      case COMMENT_INDEX:
        return entry.getComment().trim();
      default:
        return null;
    }
  }

  public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    // The mapping only affects the contents of the data rows.
    // Pass all requests to these rows through the mapping array: "indexes".
    MP3Descriptor entry = entries[indexes[rowIndex]];
    switch (columnIndex) {
      case FILENAME_INDEX: entry.setFilename(aValue.toString()); break;
      case TITLE_INDEX: entry.setTitle(aValue.toString()); break;
      case ARTIST_INDEX: entry.setArtist(aValue.toString()); break;
      case ALBUM_INDEX: entry.setAlbum(aValue.toString()); break;
      case TRACK_INDEX: entry.setTrack(aValue.toString()); break;
      case GENRE_INDEX: entry.setGenre(aValue.toString()); break;
      case YEAR_INDEX: entry.setYear(aValue.toString()); break;
      case COMMENT_INDEX: entry.setComment(aValue.toString()); break;
    }
    fireTableCellUpdated(rowIndex, columnIndex);
  }

  /**
   * 
   */
  public void addEntry(MP3Descriptor entry) {
    if (nrOfRows == entries.length) {
      MP3Descriptor[] newEntries = new MP3Descriptor[entries.length + SIZE_INCREMENT];
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

  protected int compareRowsByColumn(int row1, int row2, int column) {
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

    // We copy all returned values from the getValue call in case
    // an optimised model is reusing one object to return many
    // values.  The Number subclasses in the JDK are immutable and
    // so will not be used in this way but other subclasses of
    // Number might want to do this to save space and avoid
    // unnecessary heap allocation.
    if (Comparable.class.isAssignableFrom(type)) {

      Comparable c1 = (Comparable) getRealValueAt(row1, column);
      Comparable c2 = (Comparable) getRealValueAt(row2, column);
      return c1.compareTo(c2);

    } else {
      
      Object v1 = getRealValueAt(row1, column);
      String s1 = v1.toString();
      Object v2 = getRealValueAt(row2, column);
      String s2 = v2.toString();
      int result = s1.compareToIgnoreCase(s2);

      if (result < 0) {
        return -1;
      } else if (result > 0) {
        return 1;
      } else {
        return 0;
      }
      
    }
  }

  protected int compare(int row1, int row2) {
    int result = compareRowsByColumn(row1, row2, sortColumn);
    if (result != 0) {
      return ascending ? result : -result;
    }
    return 0;
  }

  protected void reallocateIndexes() {
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

  // This is a home-grown implementation which we have not had time
  // to research - it may perform poorly in some circumstances. It
  // requires twice the space of an in-place algorithm and makes
  // NlogN assigments shuttling the values between the two
  // arrays. The number of compares appears to vary between N-1 and
  // NlogN depending on the initial order but the main reason for
  // using it here is that, unlike qsort, it is stable.
  protected void shuttlesort(int from[], int to[], int low, int high) {
    if (high - low < 2) {
      return;
    }
    int middle = (low + high) / 2;
    shuttlesort(to, from, low, middle);
    shuttlesort(to, from, middle, high);

    int p = low;
    int q = middle;

    // This is an optional short-cut; at each recursive call,
    // check to see if the elements in this subset are already
    // ordered.  If so, no further comparisons are needed; the
    // sub-array can just be copied.  The array must be copied rather
    // than assigned otherwise sister calls in the recursion might
    // get out of sinc.  When the number of elements is three they
    // are partitioned so that the first set, [low, mid), has one
    // element and and the second, [mid, high), has two. We skip the
    // optimisation when the number of elements is three or less as
    // the first compare in the normal merge will produce the same
    // sequence of steps. This optimisation seems to be worthwhile
    // for partially ordered lists but some analysis is needed to
    // find out how the performance drops to Nlog(N) as the initial
    // order diminishes - it may drop very quickly.
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

  protected void swap(int i, int j) {
    int tmp = indexes[i];
    indexes[i] = indexes[j];
    indexes[j] = tmp;
  }

  protected void sortByColumn(int column, boolean ascending) {
    this.sortColumn = column;
    this.ascending = ascending;
    shuttlesort((int[]) indexes.clone(), indexes, 0, indexes.length);
    fireTableChanged(new TableModelEvent(this));
  }

  // There is no-where else to put this. 
  // Add a mouse listener to the Table to trigger a table sort 
  // when a column heading is clicked in the JTable. 
  public void addMouseListenerToTableHeader(JTable table) {
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

  public int getSortColumn() {
    return this.sortColumn;
  }

  public boolean isAscending() {
    return this.ascending;
  }

  public boolean isCellEditable(int rowIndex, int columnIndex) {
    return false;
  }
  
  public Class getColumnClass(int columnIndex) {
    return MP3Descriptor.class;
  }

}
