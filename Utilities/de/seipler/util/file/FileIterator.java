package de.seipler.util.file;

import java.io.*;
import java.util.*;

/**
 * Utility class for a breadth-first iteration through filtered
 * files and/or directories of a directory structure.
 * 
 * @author Georg Seipler
 */
public class FileIterator implements Iterator {

	/** FileFilter that filters nothing */
	public static FileFilter ACCEPT_ALL = new FileFilter() {
		public boolean accept(File pathname) { return true;	}
	};

  private File startDirectory;
  private FileFilter fileFilter;
  private boolean includeDirectories;
  
  private List fileList;
  private Iterator fileIterator;
  private List directoryList;
  private int knownSize;

  /**
   * Convenience Constructor.
   * Iterator returns files and directories unfiltered.
   * 
   * @param startDirectory directory structure starting point.
   */
  public FileIterator(File startDirectory) {
    this(startDirectory, true, null);
  }

  /**
   * Convenience Constructor.
   * Iterator returns files (or files and directories) unfiltered.
   * 
   * @param startDirectory directory structure starting point.
   * @param includeDirectories whether directories should be returned as well
   */
  public FileIterator(File startDirectory, boolean includeDirectories) {
    this(startDirectory, includeDirectories, null);
  }

  /**
   * Default Constructor.
   * 
   * @param startDirectory directory structure starting point.
   * @param includeDirectories whether directories should be returned as well
   * @param fileFilter filter to be applied to all files and
   *   directories, determines which files the iterator will return.
   */
  public FileIterator(File startDirectory, boolean includeDirectories, FileFilter fileFilter) {
  	if (fileFilter == null) {	setFileFilter(ACCEPT_ALL); } else { setFileFilter(fileFilter); }
    setIncludeDirectories(includeDirectories);
    setStartDirectory(startDirectory);
    reset();
  }

  /**
   * Descends one level by visiting all directories on the current
   * level and collecting files and directories. Called whenever all
   * the files on the current level have been returned by the iterator.
   */
  protected void descend() {    
    List nextLevelFiles = new LinkedList();
    List nextLevelDirectories = new LinkedList();
    Iterator directoryIterator = this.directoryList.iterator();
    while (directoryIterator.hasNext()) {
      File directory = (File) directoryIterator.next();
      if (includeDirectories) { nextLevelFiles.add(directory); }
      File[] files = directory.listFiles();
      if (files != null) {
		    for (int i = 0; i < files.length; i++) {
	        if (files[i].isFile()) {
	          if (this.fileFilter.accept(files[i])) { nextLevelFiles.add(files[i]); }
	        } else if (files[i].isDirectory()) {
	          nextLevelDirectories.add(files[i]);
	        }
		    }
			}
    }
    this.fileList = nextLevelFiles;
    this.knownSize += nextLevelFiles.size();
    this.directoryList = nextLevelDirectories;
    this.fileIterator = fileList.iterator();
  }
  
  /**
   * Accessor to the used File Filter.
   */
  public FileFilter getFileFilter() {
    return this.fileFilter;
  }

  /**
   * Accessor to the directory structure starting point.
   */
  public File getStartDirectory() {
    return this.startDirectory;
  }

  /**
   * Returns <tt>true</tt> if the iteration has more elements.
   * 
   * @see Iterator#hasNext()
   */
  public boolean hasNext() {
    while ((fileIterator == null) || (!fileIterator.hasNext() && (directoryList.size() > 0))) { descend(); }
    return fileIterator.hasNext();
  }

  /**
   * Returns the includeDirectories.
   * @return boolean
   */
  public boolean hasIncludeDirectories() {
    return this.includeDirectories;
  }
  
  /**
   * Returns the size as currently known.
   */
  public int knownSize() {
  	return this.knownSize;
  }

  /**
   * Returns the next element in the interation.
   * 
   * @see Iterator#next()
   */
  public Object next() {
    if (hasNext()) {
      return fileIterator.next();
    } else {
      throw new NoSuchElementException("No more files available.");
    }
  }

  /**
   * Removes from the underlying collection the last element returned by
   * the iterator (unsupported operation).
   * 
   * @see Iterator#remove()
   * @exception UnsupportedOperationException always
   */
  public void remove() {
    throw new UnsupportedOperationException("Removing of files unsopported.");
  }

	/**
	 * Resets the iterator (same effect as new).
	 */
	public void reset() {
		this.directoryList = new LinkedList();
		directoryList.add(startDirectory);
		this.knownSize = 0;
		hasNext();
	}

  /**
   * Defines the used File Filter.
   */
  protected void setFileFilter(FileFilter fileFilter) {
    this.fileFilter = fileFilter;
  }

  /**
   * Sets the includeDirectories.
   * @param includeDirectories The includeDirectories to set
   */
  protected void setIncludeDirectories(boolean includeDirectories) {
    this.includeDirectories = includeDirectories;
  }

  /**
   * Defines the directory structure starting point.
   * 
   * @exception IllegalArgumentException if the given File is
   *   not a directory.
   */
  protected void setStartDirectory(File startDirectory) {
    if (!startDirectory.isDirectory()) {
      throw new IllegalArgumentException("File " + startDirectory.getPath() + " is not a directory.");
    }
    this.startDirectory = startDirectory;
  }
    
  /**
   * Simple Test for this class.
   * Lists all files of the given directory structure on <code>stdout</code>.
   */
  public static void main(String[] args) {
		int knownSize = 0;
		FileIterator myIterator = new FileIterator(new File(args[0]));
		while (myIterator.hasNext()) {
			if (myIterator.knownSize != knownSize) {
				knownSize = myIterator.knownSize();
				System.out.println("known size: " + knownSize);
			}
			System.out.println(myIterator.next());
		}
		System.out.println("known size: " + myIterator.knownSize());
  }
  
}
