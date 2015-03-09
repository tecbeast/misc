package de.seipler.util.file.java;

import java.io.*;

import de.seipler.util.file.FileIterator;

/**
 * Simple Test for the <code>JavaFileParser</code> tool.
 * 
 * @author Georg Seipler
 */
public class JavaFileParserTest {

  /**
   * Iterates through all Javafiles in the given directory structure
   * (or of the given singular file) and prints their FileStructure
   * to <code>stdout</code>.
   */
  public static void main(String[] args) {
    
    File currentFile = null;
    
    try {
      File myFile = new File(args[0]);
      JavaFileParser myParser = new JavaFileParser();
      if (myFile.isDirectory()) {
        FileIterator iterator = new FileIterator(
          myFile,
          false,
          new FileFilter() {
            public boolean accept(File file) {
              if (file.isDirectory()) {
                return true;
              } else {
                return (file.getName().endsWith(".java"));
              }
            }
          }
        );
        while (iterator.hasNext()) {
          currentFile = (File) iterator.next();
          JavaFile fileStructure = myParser.parse(currentFile);
          System.out.println(fileStructure);
          System.out.println();
        }
      } else {
        currentFile = myFile;
        JavaFile fileStructure = myParser.parse(currentFile);
        System.out.println(fileStructure);
      }
    } catch (Exception all) {
      System.err.println("Error while processing file: " + currentFile.getPath());
      all.printStackTrace(System.err);
    }
  }

}

