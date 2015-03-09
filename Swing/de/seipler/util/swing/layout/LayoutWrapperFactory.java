package de.seipler.util.swing.layout;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 
 * @author Georg Seipler
 */
public class LayoutWrapperFactory {
  
  public static final int GRID_BAG_LAYOUT = 1;
  
  private Map layoutWrapperById;
  private DefaultHandler xmlHandler;
  
  public LayoutWrapperFactory() {
    this.layoutWrapperById = new HashMap();
    this.xmlHandler = new LayoutWrapperXmlHandler(this);
  }

  public LayoutWrapper create(int type, String id) {
    LayoutWrapper result = null;
    if (id == null) {
      throw new IllegalArgumentException("Parameter id must not be null.");
    }
    if (get(id) != null) {
      throw new IllegalArgumentException("Id " + id + " already in use.");
    }
    switch (type) {
      case GRID_BAG_LAYOUT:
        result = new GridBagLayoutWrapper();
        break;
      default:
        throw new IllegalArgumentException("Unknown type " + type + ".");
    }
    layoutWrapperById.put(id, result);
    return result;
  }
  
  protected LayoutWrapper get(String id) {
    return (LayoutWrapper) layoutWrapperById.get(id);
  }
  
  public void readConstraints(File file) throws ParserConfigurationException, SAXException, IOException, FileNotFoundException {
    BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
    readConstraints(in);
    in.close();
  }

  public void readConstraints(InputStream xmlIn) throws ParserConfigurationException, SAXException, IOException {
    SAXParserFactory xmlParserFactory = SAXParserFactory.newInstance();
    xmlParserFactory.setNamespaceAware(false);
    XMLReader xmlReader = xmlParserFactory.newSAXParser().getXMLReader();
    xmlReader.setContentHandler(xmlHandler);
    InputSource inputSource = new InputSource(xmlIn);
    xmlReader.parse(inputSource);
    xmlIn.close();
  }

}
