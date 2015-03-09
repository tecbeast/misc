package de.seipler.test.descriptors.simple;

import java.io.*;
import java.util.*;

import de.seipler.util.file.FileIterator;

/**
 * 
 * @author Georg Seipler
 */
public class TestMP3Simple {
  
  private MP3DescriptorFactory descriptorFactory;
   
  /**
   * 
   */
  public TestMP3Simple() {
    this.descriptorFactory = new MP3DescriptorFactory();
  }

  /**
   * 
   */
  public int writeDescriptors(File scanDirectory) throws IOException {

    int nrOfFiles = 0;

    Iterator mp3Iterator = new FileIterator(
      scanDirectory,
      false,
      new FileFilter() {
        public boolean accept(File file) {
          if ((file != null) && (file.getName().endsWith(".mp3"))) {
            return true;
          } else {
            return false;
          }
        }
      }
    );    

    while (mp3Iterator.hasNext()) {
      nrOfFiles++;
      File file = (File) mp3Iterator.next();
      MP3Descriptor descriptor = this.descriptorFactory.createFor(file);
      File xmlFile = File.createTempFile("descriptor",".xml");
      // System.out.println(xmlFile.getPath());
      descriptor.saveTo(xmlFile);
    }
        
    return nrOfFiles;
    
  }
  
  /**
   * 
   */
  public int readDescriptors(File scanDirectory) throws DescriptorException, IOException {

    int nrOfFiles = 0;

    Iterator descriptorIterator = new FileIterator(
      scanDirectory,
      false,
      new FileFilter() {
        public boolean accept(File file) {
          if ((file != null) && (file.getName().startsWith("descriptor"))) {
            return true;
          } else {
            return false;
          }
        }
      }
    );    

    while (descriptorIterator.hasNext()) {
      nrOfFiles++;
      File file = (File) descriptorIterator.next();
      // System.out.println(file.getPath());
      this.descriptorFactory.loadFrom(file);
      // System.out.println(descriptor.getTitle());
      // System.out.println(descriptor.getFile());
    }
        
    return nrOfFiles;
    
  }
    
  /**
   * 
   */
  public static void main(String[] args) {
    
    // use piccolo parser
    System.setProperty("javax.xml.parsers.SAXParserFactory","com.bluecast.xml.JAXPSAXParserFactory");
        
    TestMP3Simple myself = new TestMP3Simple();
    
    if ((args == null) || (args.length != 1)) {
      
      System.out.println("Usage: java " + myself.getClass().getName() + " startDirectory");
      
    } else {
      
      try {

        System.out.println("Scan .mp3 files and create descriptor files ...");
        long time1 = System.currentTimeMillis();

        int nrOfFiles = myself.writeDescriptors(new File(args[0]));
    
        long time2 = System.currentTimeMillis();    
        System.out.println(nrOfFiles + " descriptors created in " + (time2 - time1) + " ms.");
        
        System.out.println("Read created descriptor files ...");
        long time3 = System.currentTimeMillis();
        
        File descriptorDirectory = new File(System.getProperty("java.io.tmpdir"));
        nrOfFiles = myself.readDescriptors(descriptorDirectory);
        
        long time4 = System.currentTimeMillis();
        System.out.println(nrOfFiles + " descriptors read in " + (time4 - time3) + " ms.");          
    
      } catch(Exception all) {
         all.printStackTrace();
      }
    
    }
    
  }
  
}
