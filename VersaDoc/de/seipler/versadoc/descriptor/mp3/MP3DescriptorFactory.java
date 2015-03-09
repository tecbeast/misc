package de.seipler.versadoc.descriptor.mp3;

import helliker.id3.MP3File;

import java.io.File;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.XMLReader;

import de.seipler.versadoc.category.Category;
import de.seipler.versadoc.category.CategoryRegistry;
import de.seipler.versadoc.descriptor.DescriptorException;
import de.seipler.versadoc.descriptor.DescriptorFactoryException;
import de.seipler.versadoc.descriptor.DescriptorListHandler;

/**
 * 
 * @author Georg Seipler
 */
public class MP3DescriptorFactory {
  
  private XMLReader xmlReader;
  private DescriptorListHandler descriptorListHandler;
  private Category[] categories;

  /**
   * 
   */
  public MP3DescriptorFactory(CategoryRegistry categoryRegistry) {

		try {
			categoryRegistry.registerCategory("genre", String.class, MP3Descriptor.class.getDeclaredMethod("getGenre", null));
			categoryRegistry.registerCategory("artist", String.class, MP3Descriptor.class.getDeclaredMethod("getArtist", null));
			categoryRegistry.registerCategory("album", String.class, MP3Descriptor.class.getDeclaredMethod("getAlbum", null));
			categoryRegistry.registerCategory("title", String.class, MP3Descriptor.class.getDeclaredMethod("getTitle", null));
		} catch (NoSuchMethodException nsme) {
			nsme.printStackTrace();
		}
    
    this.descriptorListHandler = new DescriptorListHandler();
    MP3DescriptorHandler mp3DescriptorHandler = new MP3DescriptorHandler();
    this.descriptorListHandler.registerHandler(mp3DescriptorHandler);
    
    try {
      SAXParserFactory xmlParserFactory = SAXParserFactory.newInstance();
      xmlParserFactory.setNamespaceAware(false);
      this.xmlReader = xmlParserFactory.newSAXParser().getXMLReader();
      this.xmlReader.setContentHandler(this.descriptorListHandler);      
    // ParserConfigurationException, SAXException
    } catch (Exception e) {
      throw new DescriptorFactoryException(e);
    }
    
  }

  /**
   * 
   */
  public MP3Descriptor createFor(File file) {
    try {
      MP3File mp3File = new MP3File(file);
      MP3Descriptor descriptor = new MP3Descriptor();
      descriptor.setFile(file);
      descriptor.setLastModified(file.lastModified());
      descriptor.setAuthor("GENERATED");
      descriptor.setArtist(mp3File.getArtist());
      descriptor.setTitle(mp3File.getTitle());
      descriptor.setAlbum(mp3File.getAlbum());
      descriptor.setYear(mp3File.getYear());
      descriptor.setTrack(mp3File.getTrackString());
      descriptor.setGenre(mp3File.getGenre());
			// use trim to eliminate illegal end of lines
      descriptor.setComment(mp3File.getComment().trim());
      // workaround to bug in mp3 library ?
      // sometimes it returns genre number in brackets in addition to genre name
      int genrePos = descriptor.getGenre().indexOf(')');
      if ((genrePos > 0) && (genrePos < descriptor.getGenre().length())) {
        descriptor.setGenre(descriptor.getGenre().substring(genrePos + 1));
      }
      return descriptor;
    // FileNotFoundException, NoMPEGFramesException, ID3v2FormatException, CorruptHeaderException, IOException
    } catch (Exception e) {
      throw new DescriptorException(e);
    }
  }

  /**
   * 
   */
  public Category[] getCategories() {
    return categories;
  }
  
}
