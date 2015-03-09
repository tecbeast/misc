package de.seipler.util.swing.layout;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 
 * @author Georg Seipler
 */
public class LayoutWrapperFactory {
  
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
      case LayoutWrapper.GRID_BAG_LAYOUT:
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
  
  public void readConstraints(File file) {
    BufferedInputStream in = null;
    try {
      in = new BufferedInputStream(new FileInputStream(file));
    } catch (Exception e) {
      throw new LayoutWrapperException("Unable to open file.", e);
    }
    readConstraints(in);
  }

  public void readConstraints(InputStream xmlIn) {
    SAXParserFactory xmlParserFactory = SAXParserFactory.newInstance();
    xmlParserFactory.setNamespaceAware(false);
    XMLReader xmlReader = null; 
    try {
      xmlReader = xmlParserFactory.newSAXParser().getXMLReader();
      xmlReader.setContentHandler(xmlHandler);
    } catch (Exception e) {
      throw new LayoutWrapperException("Unable to initialize parser.", e);
    }
    InputSource inputSource = new InputSource(xmlIn);
    try {
      xmlReader.parse(inputSource);
    } catch (Exception e) {
      throw new LayoutWrapperException("Parsing error.", e);
    }
    try {
      xmlIn.close();
    } catch (Exception e) {
      throw new LayoutWrapperException("Unable to close stream.", e);
    }
  }

}
