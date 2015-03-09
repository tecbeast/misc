 package de.seipler.test.descriptors.xml;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;

import de.seipler.util.file.DirectoryIterator;

/**
 * 
 * @author Georg Seipler
 */
public class TestMP3 {
  
	protected static final boolean USE_COMPRESSION = false;  
	protected static final String DESCRIPTOR_FILENAME = ".descriptors"; 

  private MP3DescriptorFactory descriptorFactory;
  private DescriptorListFactory descriptorListFactory;
  private FileFilter mp3Filter;
   
  /**
   * 
   */
  public TestMP3() {

    this.descriptorFactory = new MP3DescriptorFactory();
    this.descriptorListFactory = new DescriptorListFactory();

    this.mp3Filter = new FileFilter() {
			public boolean accept(File file) {
				if ((file != null) && file.isFile() && file.getName().endsWith(".mp3")) {
					return true;
				} else {
					return false;
				}
			}
    };

  }

  /**
   * 
   */
  public boolean writeDescriptorsToXML(File directory) throws IOException {

		if ((directory == null) || !directory.isDirectory()) {
			throw new IllegalArgumentException("Parameter directory must be a directory");
		}

    File[] mp3Files = directory.listFiles(mp3Filter);
    if ((mp3Files != null) && (mp3Files.length > 0)) {
			DescriptorList descriptorList = new DescriptorList();
    	for (int i = 0; i < mp3Files.length; i++) {
				descriptorList.add(this.descriptorFactory.createFor(mp3Files[i]));
    	}
			descriptorList.writeTo(new File(directory, DESCRIPTOR_FILENAME), USE_COMPRESSION);
			return true;
    } else {
			return false;
    }
    
  }

  /**
   * 
   */
  public DescriptorList readDescriptors(File directory) throws DescriptorException, IOException {

		if ((directory == null) || !directory.isDirectory()) {
			throw new IllegalArgumentException("Parameter directory must be a directory");
		}

		File xmlFile = new File(directory, DESCRIPTOR_FILENAME);
		if (xmlFile.isFile()) {
			return this.descriptorListFactory.readFrom(xmlFile, USE_COMPRESSION);
		} else {
			return new DescriptorList();
		}
   	
  }
    
  /**
   * 
   */
  public static void main(String[] args) {
    
    // use piccolo parser
    System.setProperty("javax.xml.parsers.SAXParserFactory","com.bluecast.xml.JAXPSAXParserFactory");
        
    TestMP3 myself = new TestMP3();
    
    if ((args == null) || (args.length != 1)) {
      
      System.out.println("Usage: java " + myself.getClass().getName() + " scanDirectory");
      
    } else {
      
      try {

        File scanDirectory = new File(args[0]);        
        
        System.out.println();

				System.out.println("Scan .mp3 files and create descriptor xmlFiles ...");
				long time1 = System.currentTimeMillis();

        int nrOfFiles = 0;
        DirectoryIterator directoryIterator = new DirectoryIterator(scanDirectory);
        while (directoryIterator.hasNext()) {
        	if (myself.writeDescriptorsToXML((File) directoryIterator.next())) { nrOfFiles++; }
        }
        
        long time2 = System.currentTimeMillis();    
        System.out.println(nrOfFiles + " descriptors created in " + (time2 - time1) + " ms.");

        System.out.println("Read created descriptors ...");
        long time3 = System.currentTimeMillis();

        directoryIterator.reset();
        DescriptorList allDescriptors = new DescriptorList();
				while (directoryIterator.hasNext()) {
          allDescriptors.addAll(myself.readDescriptors((File) directoryIterator.next())); 
				}
			
        long time4 = System.currentTimeMillis();
        System.out.println(allDescriptors.size() + " descriptors read in " + (time4 - time3) + " ms.");
        
        System.out.println();
        
        Category[] categories = myself.descriptorFactory.getCategories();
        Comparator[] comparators = new Comparator[categories.length];
        for (int i = 0; i < comparators.length; i++) {
          comparators[i] = myself.descriptorFactory.getComparator(categories[i]);
        }
        Comparator comparatorChain = new ComparatorChain(comparators);
        
        allDescriptors.sort(comparatorChain);
        Iterator descriptorIterator = allDescriptors.iterator();
        while (descriptorIterator.hasNext()) {
          MP3Descriptor descriptor = (MP3Descriptor) descriptorIterator.next();
          System.out.print(descriptor.getGenre());
          System.out.print(" - ");
          System.out.print(descriptor.getArtist());
          System.out.print(" - ");
          System.out.print(descriptor.getAlbum());
          System.out.print(" - ");
          System.out.print(descriptor.getTitle());
          System.out.println();
        }
    
      } catch(Exception all) {
        all.printStackTrace();
      }
    
    }
    
  }
  
}
