package de.seipler.util.file;

import java.io.*;
import java.util.*;

/**
 * Utility class for a breadth-first iteration through all
 * directories of a directory structure.
 * 
 * @author Georg Seipler
 */
public class DirectoryIterator implements Iterator {

	/** FileFilter that filters directories */
	public static FileFilter ACCEPT_DIRECTORIES_ONLY = new FileFilter() {
		public boolean accept(File pathname) { return ((pathname != null) && pathname.isDirectory());	}
	};

  private File startDirectory;
  private List directoryList;
  private Iterator directoryIterator;
  private int knownSize;

  /**
   * Default Constructor.
   * 
   * @param startDirectory directory structure starting point.
   */
  public DirectoryIterator(File startDirectory) {
    setStartDirectory(startDirectory);
    reset();
  }

  /**
   * Descends one level by visiting all directories on the current
   * level and collecting directories. Called whenever all of the
   * directories on the current level have been returned by the iterator.
   */
  protected void descend() {
  	if (this.directoryList.size() > 0) {
	    List nextLevelDirectories = new LinkedList();
	    this.directoryIterator = this.directoryList.iterator();
	    while (this.directoryIterator.hasNext()) {
	      File directory = (File) directoryIterator.next();
	      File[] files = directory.listFiles(ACCEPT_DIRECTORIES_ONLY);
	      if (files != null) {
					for (int i = 0; i < files.length; i++) { nextLevelDirectories.add(files[i]); }
	      }
	    }
	    this.directoryList = nextLevelDirectories;
			this.knownSize += nextLevelDirectories.size();
			this.directoryIterator = this.directoryList.iterator();
		}
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
  	if (this.directoryIterator == null) {
  		return true;
  	} else {
  		if (!this.directoryIterator.hasNext()) { descend(); }
  		return this.directoryIterator.hasNext();
	  }
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
		if (this.directoryIterator == null) {
			descend();
			return this.startDirectory;
		} else if (hasNext()) {
			return this.directoryIterator.next();
		} else {
			throw new NoSuchElementException("No more directories available.");
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
		this.directoryList.add(startDirectory);
    this.directoryIterator = null;
		this.knownSize = 1;
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
   * Lists all directories of the given directory structure on <code>stdout</code>.
   */
  public static void main(String[] args) {
  	int knownSize = 0;
    DirectoryIterator myIterator = new DirectoryIterator(new File(args[0]));
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
