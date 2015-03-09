package de.seipler.test.descriptors.simple;

import helliker.id3.MP3File;

import java.io.File;
import java.io.FileReader;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/**
 * 
 * @author Georg Seipler
 */
public class MP3DescriptorFactory {
  
  private XMLReader xmlReader;
  private MP3DescriptorHandler descriptorHandler;
  
  public MP3DescriptorFactory() {
    try {
      this.descriptorHandler = new MP3DescriptorHandler();
      SAXParserFactory xmlParserFactory = SAXParserFactory.newInstance();
      xmlParserFactory.setNamespaceAware(false);
      this.xmlReader = xmlParserFactory.newSAXParser().getXMLReader();
      this.xmlReader.setContentHandler(this.descriptorHandler);      
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
      descriptor.setType(MP3Descriptor.mp3MimeType);
      descriptor.setArtist(mp3File.getArtist());
      descriptor.setTitle(mp3File.getTitle());
      descriptor.setAlbum(mp3File.getAlbum());
      descriptor.setGenre(mp3File.getGenre());
      // workaround to bug in mp3 library ?
      // returns genre number in brackets in addition to genre name
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
  public MP3Descriptor loadFrom(File file) {
    try {
      InputSource inputSource = new InputSource(new FileReader(file));
      this.xmlReader.parse(inputSource);
      return this.descriptorHandler.getDescriptor();
    // SAXException, IOException
    } catch (Exception e) {
      throw new DescriptorException(e);
    }
  }
 
}
