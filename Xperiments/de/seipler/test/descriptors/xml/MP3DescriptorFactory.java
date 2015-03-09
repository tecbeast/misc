package de.seipler.test.descriptors.xml;

import helliker.id3.MP3File;

import java.io.File;
import java.util.Comparator;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.XMLReader;

/**
 * 
 * @author Georg Seipler
 */
public class MP3DescriptorFactory {
  
  private static final int GENRE_ID = 1;  
  private static final int ARTIST_ID = 2;
  private static final int ALBUM_ID = 3;
  private static final int TITLE_ID = 4;
  
  private XMLReader xmlReader;
  private DescriptorListHandler descriptorListHandler;
  private Category[] categories;
  private Comparator[] comparators;  

  /**
   * 
   */
  public MP3DescriptorFactory() {
    
    this.categories = new Category[] {
      new Category(GENRE_ID, "genre"),
      new Category(ARTIST_ID, "artist"),
      new Category(ALBUM_ID, "album"),
      new Category(TITLE_ID, "title")    
    };
    
    this.comparators = new Comparator[] {
      new Comparator() {
        public int compare(Object o1, Object o2) {
          return ((MP3Descriptor) o1).getGenre().compareTo(((MP3Descriptor) o2).getGenre());
        }  
      },
      new Comparator() {
        public int compare(Object o1, Object o2) {
          return ((MP3Descriptor) o1).getArtist().compareTo(((MP3Descriptor) o2).getArtist());
        }  
      },
      new Comparator() {
        public int compare(Object o1, Object o2) {
          return ((MP3Descriptor) o1).getAlbum().compareTo(((MP3Descriptor) o2).getAlbum());
        }  
      },
      new Comparator() {
        public int compare(Object o1, Object o2) {
          return ((MP3Descriptor) o1).getTitle().compareTo(((MP3Descriptor) o2).getTitle());
        }  
      }
    };
    
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
      throw new FactoryException(e);
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
  
  /**
   * 
   */
  public Comparator getComparator(Category category) {
    return this.comparators[category.getId() - 1];
  }
  
}
