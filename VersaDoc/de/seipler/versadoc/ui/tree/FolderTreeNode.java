package de.seipler.versadoc.ui.tree;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import javax.swing.tree.DefaultMutableTreeNode;

import de.seipler.versadoc.ui.table.DocumentTableEntry;

/**
 * @author Georg Seipler
 */
public class FolderTreeNode extends DefaultMutableTreeNode implements Transferable {

  public final static DataFlavor DATA_FLAVOR = new DataFlavor(FolderTreeNode.class, "FolderTreeNode");
  protected final static DataFlavor[] SUPPORTED_FLAVORS = { DATA_FLAVOR, DocumentTableEntry.DATA_FLAVOR };

  protected static FileFilter directoryFilter;
  
  private boolean isScanned;
  private boolean hasDropFocus;
  
  /**
   * 
   */
  public FolderTreeNode(File directory) {
    super(directory);

    setScanned(false);
    
    if (directoryFilter == null) {
      directoryFilter = new FileFilter() {
        public boolean accept(File file) {
          return file.isDirectory();
        }
      };
    }      
  }

  /**
   * 
   */
  public File getFile() {
    return (File) getUserObject();
  }

  /**
   * 
   */
  public boolean hasDropFocus() {
    return this.hasDropFocus;
  }

  /**
   * 
   */
  public boolean isScanned() {
    return this.isScanned;
  }

  /**
   * 
   */
  public void setDropFocus(boolean hasFocus) {
    this.hasDropFocus = hasFocus;
  }

  /**
   * 
   */
  public void setScanned(boolean isScanned) {
    this.isScanned = isScanned;
  }

  /**
   * 
   */
  public String toString() {
    if (userObject == null) {
      return null;
    } else {
      if (getFile().getName().length() > 0) {
        return getFile().getName();
      } else {
        return getFile().toString();
      }
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

  /**
   * 
   */
  public void expand() {
    if (!isScanned()) {
      File[] dirs = getFile().listFiles(directoryFilter);
      if (dirs != null) {
        for (int i = 0; i < dirs.length; i++) {
          add(new FolderTreeNode(dirs[i]));
        }
      }
      setScanned(true);
    }
  }

}
