 package de.seipler.versadoc.descriptor.mp3;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import de.seipler.util.file.DirectoryIterator;
import de.seipler.versadoc.category.CategoryPath;
import de.seipler.versadoc.category.CategoryRegistry;
import de.seipler.versadoc.descriptor.Descriptor;
import de.seipler.versadoc.descriptor.DescriptorException;
import de.seipler.versadoc.descriptor.DescriptorList;
import de.seipler.versadoc.descriptor.DescriptorListFactory;

/**
 * 
 * @author Georg Seipler
 */
public class TestMP3 {
  
	protected static final boolean USE_COMPRESSION = true;  
	protected static final String DESCRIPTOR_FILENAME = ".descriptors"; 

	private CategoryRegistry categoryRegistry;
  private MP3DescriptorFactory descriptorFactory;
  private DescriptorListFactory descriptorListFactory;
  private FileFilter mp3Filter;
   
  /**
   * 
   */
  public TestMP3() {

		this.categoryRegistry = new CategoryRegistry();
    this.descriptorFactory = new MP3DescriptorFactory(categoryRegistry);
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
        
        CategoryPath path = new CategoryPath(4);
				path.addCategory(myself.categoryRegistry.getCategory("genre"));
				path.addCategory(myself.categoryRegistry.getCategory("artist"));
				path.addCategory(myself.categoryRegistry.getCategory("album"));
				path.addCategory(myself.categoryRegistry.getCategory("title"));

        allDescriptors.sort(path.getComparator());
        
        DefaultMutableTreeNode[] nodes = new DefaultMutableTreeNode[path.size() + 1];
        nodes[0] = new DefaultMutableTreeNode("/");        
        
        Iterator descriptorIterator = allDescriptors.iterator();
        while (descriptorIterator.hasNext()) {
          Descriptor descriptor = (Descriptor) descriptorIterator.next();          
          for (int i = 1; i < nodes.length; i++) {
            Object value = path.getCategory(i - 1).getDescriptorValue(descriptor);
            if ((nodes[i] == null) || !value.equals(nodes[i].getUserObject())) {
              nodes[i] = new DefaultMutableTreeNode(value);
              nodes[i - 1].add(nodes[i]);
              if (i < nodes.length - 1) { nodes[i + 1] = null; }
            }
          }
				}
				
				JTree tree = new JTree(nodes[0]);
				JScrollPane scrollPane = new JScrollPane(tree);
				JFrame frame = new JFrame() {
					protected void frameInit() {
						super.frameInit();
						setDefaultCloseOperation(EXIT_ON_CLOSE);
					}
				};
				frame.getContentPane().setLayout(new BorderLayout());
				frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
				frame.setSize(300, 300);
				frame.setVisible(true);
    
      } catch(Exception all) {
        all.printStackTrace();
      }
    
    }
    
  }
  
}
