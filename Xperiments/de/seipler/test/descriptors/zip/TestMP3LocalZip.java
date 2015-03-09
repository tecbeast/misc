package de.seipler.test.descriptors.zip;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import de.seipler.util.file.FileIterator;

/**
 * 
 * @author Georg Seipler
 */
public class TestMP3LocalZip {
  
  private MP3DescriptorFactory descriptorFactory;
   
  /**
   * 
   */
  public TestMP3LocalZip() {
    this.descriptorFactory = new MP3DescriptorFactory();
  }

  /**
   * 
   */
  public int writeDescriptorsToLocalZip(File scanDirectory) throws IOException {

    int nrOfFiles = 0;

    Iterator mp3Iterator = new FileIterator(
      scanDirectory,
      true,
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

    File zipFile = null;
    ZipOutputStream zipOut = null;
    
    while (mp3Iterator.hasNext()) {
      File file = (File) mp3Iterator.next();
      if (file.isDirectory()) {
        zipFile = new File(file, "descriptors.zip");
        if (zipOut != null) { zipOut.close(); }
        zipOut = null;
      } else {        
        if (zipOut == null) {
          nrOfFiles++;
          zipOut = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));
        }
        MP3Descriptor descriptor = this.descriptorFactory.createFor(file);
        ZipEntry zipEntry = new ZipEntry(file.getName());
        zipOut.putNextEntry(zipEntry);
        descriptor.writeTo(zipOut);
        zipOut.closeEntry();
      }
    }
        
    if (zipOut != null) { zipOut.close(); }

    return nrOfFiles;
    
  }

  /**
   * 
   */
  public int readDescriptorsFromLocalZip(File scanDirectory) throws DescriptorException, IOException {

    int nrOfFiles = 0;

    Iterator descriptorIterator = new FileIterator(
      scanDirectory,
      false,
      new FileFilter() {
        public boolean accept(File file) {
          if ((file != null) && (file.getName().equals("descriptors.zip"))) {
            return true;
          } else {
            return false;
          }
        }
      }
    );    

    while (descriptorIterator.hasNext()) {
      ZipFile zipFile = new ZipFile((File) descriptorIterator.next());
      Enumeration entryEnum = zipFile.entries();
      while (entryEnum.hasMoreElements()) {
        nrOfFiles++;
        ZipEntry zipEntry = (ZipEntry) entryEnum.nextElement();
        // System.out.println(zipEntry.getName());
        this.descriptorFactory.readFrom(zipFile.getInputStream(zipEntry));
        // System.out.println(descriptor.getTitle());
        // System.out.println(descriptor.getFile());
      }
      zipFile.close();
    }

    return nrOfFiles;
    
  }
    
  /**
   * 
   */
  public static void main(String[] args) {
    
    // use piccolo parser
    System.setProperty("javax.xml.parsers.SAXParserFactory","com.bluecast.xml.JAXPSAXParserFactory");
        
    TestMP3LocalZip myself = new TestMP3LocalZip();
    
    if ((args == null) || (args.length != 1)) {
      
      System.out.println("Usage: java " + myself.getClass().getName() + " scanDirectory");
      
    } else {
      
      try {

        File scanDirectory = new File(args[0]);

        System.out.println("Scan .mp3 files and create descriptor zipFiles ...");
        long time1 = System.currentTimeMillis();
        
        int nrOfFiles = myself.writeDescriptorsToLocalZip(scanDirectory);
    
        long time2 = System.currentTimeMillis();    
        System.out.println(nrOfFiles + " descriptor zipFiles created in " + (time2 - time1) + " ms.");

        System.out.println("Read created descriptors ...");
        long time3 = System.currentTimeMillis();
        
        nrOfFiles = myself.readDescriptorsFromLocalZip(scanDirectory);
        
        long time4 = System.currentTimeMillis();
        System.out.println(nrOfFiles + " descriptors read in " + (time4 - time3) + " ms.");
    
      } catch(Exception all) {
         all.printStackTrace();
      }
    
    }
    
  }
  
}
