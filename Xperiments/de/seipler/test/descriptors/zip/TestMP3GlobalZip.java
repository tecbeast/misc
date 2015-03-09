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
public class TestMP3GlobalZip {
  
  private MP3DescriptorFactory descriptorFactory;
   
  /**
   * 
   */
  public TestMP3GlobalZip() {
    this.descriptorFactory = new MP3DescriptorFactory();
  }

  /**
   * 
   */
  public int writeDescriptorsToZip(File scanDirectory, File descriptorFile) throws IOException {

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

    ZipOutputStream zipOut = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(descriptorFile)));
    
    while (mp3Iterator.hasNext()) {
      nrOfFiles++;
      File file = (File) mp3Iterator.next();
      MP3Descriptor descriptor = this.descriptorFactory.createFor(file);
      ZipEntry zipEntry = new ZipEntry("descriptor" + nrOfFiles + ".xml");
      zipOut.putNextEntry(zipEntry);
      descriptor.writeTo(zipOut);
      zipOut.closeEntry();
    }
        
    zipOut.close();

    return nrOfFiles;
    
  }

  /**
   * 
   */
  public int readDescriptorsFromZip(File descriptorFile) throws DescriptorException, IOException {

    int nrOfFiles = 0;

    ZipFile zipFile = new ZipFile(descriptorFile);
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
        
    return nrOfFiles;
    
  }
    
  /**
   * 
   */
  public static void main(String[] args) {
    
    // use piccolo parser
    System.setProperty("javax.xml.parsers.SAXParserFactory","com.bluecast.xml.JAXPSAXParserFactory");
        
    TestMP3GlobalZip myself = new TestMP3GlobalZip();
    
    if ((args == null) || (args.length != 2)) {
      
      System.out.println("Usage: java " + myself.getClass().getName() + " scanDirectory descriptorFile");
      
    } else {
      
      try {

        File scanDirectory = new File(args[0]);
        File descriptorFile = new File(args[1]);

        System.out.println("Scan .mp3 files and create descriptor zipFile ...");
        long time1 = System.currentTimeMillis();
        
        int nrOfFiles = myself.writeDescriptorsToZip(scanDirectory, descriptorFile);
    
        long time2 = System.currentTimeMillis();    
        System.out.println(nrOfFiles + " descriptors created in " + (time2 - time1) + " ms.");

        System.out.println("Read created descriptor files ...");
        long time3 = System.currentTimeMillis();
        
        nrOfFiles = myself.readDescriptorsFromZip(descriptorFile);
        
        long time4 = System.currentTimeMillis();
        System.out.println(nrOfFiles + " descriptors read in " + (time4 - time3) + " ms.");
    
      } catch(Exception all) {
         all.printStackTrace();
      }
    
    }
    
  }
  
}
