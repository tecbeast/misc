package de.seipler.test.descriptors.xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.zip.GZIPInputStream;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/**
 * 
 * @author Georg Seipler
 */
public class DescriptorListFactory {
  
  private XMLReader xmlReader;
  private DescriptorListHandler descriptorListHandler;
  
  public DescriptorListFactory() {
    try {
      this.descriptorListHandler = new DescriptorListHandler();
      MP3DescriptorHandler mp3DescriptorHandler = new MP3DescriptorHandler();
      this.descriptorListHandler.registerHandler(mp3DescriptorHandler);
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
  public DescriptorList readFrom(File xmlFile, boolean useCompression) {
    Reader xmlIn = null;
    try {
      if (useCompression) {
        xmlIn = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(xmlFile))));
      } else {
        xmlIn = new BufferedReader(new InputStreamReader(new FileInputStream(xmlFile)));
      }
      this.descriptorListHandler.setFile(xmlFile);
      InputSource inputSource = new InputSource(xmlIn);
      this.xmlReader.parse(inputSource);
      return this.descriptorListHandler.getDescriptorList();
    // SAXException, IOException
    } catch (Exception e) {
      throw new DescriptorException(e);
    } finally {
      try { xmlIn.close(); } catch (IOException ignored) { }
    }
  }

}
