package de.seipler.versadoc.ui.table;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.sql.Date;

import javax.swing.Icon;
import javax.swing.UIManager;

import de.seipler.versadoc.ui.tree.FolderTreeNode;

/**
 * Very very quick and dirty for now.
 * 
 * @author Georg Seipler
 */
public class DocumentTableEntry implements Comparable, Transferable {

  public final static DataFlavor DATA_FLAVOR = new DataFlavor(DocumentTableEntry.class, "DocumentTableEntry");
  protected final static DataFlavor[] SUPPORTED_FLAVORS = { DATA_FLAVOR, FolderTreeNode.DATA_FLAVOR };

  private File file;
  private Icon icon;
  private String name;
  private Long length;
  private Date lastModified;
  private boolean isDirectory;
  
  /**
   * 
   */
  public DocumentTableEntry(File file) {

    super();

    setFile(file);
    setLength(new Long(file.length()));
    setLastModified(new Date(file.lastModified()));
    setName(file.getName());
    
    if (file.isDirectory()) {
      setIsDirectory(true);
      setIcon(UIManager.getIcon("Tree.closedIcon"));
    } else {
      setIsDirectory(false);
      setIcon(UIManager.getIcon("Tree.leafIcon"));
    }
    
  }

  /**
   * 
   */
  public boolean isDirectory() {
    return this.isDirectory;
  }

  /**
   * Returns the file.
   * @return File
   */
  public File getFile() {
    return file;
  }

  /**
   * Returns the lastModified.
   * @return String
   */
  public Date getLastModified() {
    return this.lastModified;
  }


  /**
   * Returns the size.
   * @return Long
   */
  public Long getLength() {
    return length;
  }

  /**
   * 
   */
  protected void setIsDirectory(boolean isDirectory) {
    this.isDirectory = isDirectory;
  }

  /**
   * Sets the file.
   * @param file The file to set
   */
  protected void setFile(File file) {
    this.file = file;
  }


  /**
   * Sets the lastModified.
   * @param lastModified The lastModified to set
   */
  protected void setLastModified(Date lastModified) {
    this.lastModified = lastModified;
  }


  /**
   * Sets the size.
   * @param size The size to set
   */
  protected void setLength(Long length) {
    this.length = length;
  }

  /**
   * Returns the icon.
   * @return Icon
   */
  public Icon getIcon() {
    return icon;
  }

  /**
   * Returns the name.
   * @return String
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the icon.
   * @param icon The icon to set
   */
  protected void setIcon(Icon icon) {
    this.icon = icon;
  }

  /**
   * Sets the name.
   * @param name The name to set
   */
  protected void setName(String name) {
    this.name = name;
  }

  /**
   * 
   */
  public boolean equals(Object object) {
    if (object instanceof DocumentTableEntry) {
      return (compareTo(object) == 0);
    } else {
      return false;
    }
  }

  // Directories are less than everything else
  public int compareTo(Object object) {
    DocumentTableEntry anotherEntry = (DocumentTableEntry) object;
    if (isDirectory() == anotherEntry.isDirectory()) {
      return getName().toLowerCase().compareTo(anotherEntry.getName().toLowerCase());
    } else if (isDirectory()) {
      return -1;
    } else {
      return 1;
    }
  }

  /**
   * @see java.awt.datatransfer.Transferable#getTransferData(DataFlavor)
   */
  public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
    boolean flavorSupported = false;
    for (int i = 0; i < SUPPORTED_FLAVORS.length; i++) {
      if (flavor.equals(SUPPORTED_FLAVORS[i])) { flavorSupported = true; break; }
    }
    if (flavorSupported) {
      return getFile();
    } else {
      throw new UnsupportedFlavorException(flavor);
    }
  }

  /**
   * @see java.awt.datatransfer.Transferable#getTransferDataFlavors()
   */
  public DataFlavor[] getTransferDataFlavors() {
    return SUPPORTED_FLAVORS;
  }

  /**
   * @see java.awt.datatransfer.Transferable#isDataFlavorSupported(DataFlavor)
   */
  public boolean isDataFlavorSupported(DataFlavor flavor) {
    boolean flavorSupported = false;
    for (int i = 0; i < SUPPORTED_FLAVORS.length; i++) {
      if (flavor.equals(SUPPORTED_FLAVORS[i])) { flavorSupported = true; break; }
    }
    return flavorSupported;
  }

}
