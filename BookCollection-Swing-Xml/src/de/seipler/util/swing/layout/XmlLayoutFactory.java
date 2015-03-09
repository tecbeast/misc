package de.seipler.util.swing.layout;

import java.awt.Container;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/**
 * 
 * @author Georg Seipler
 */
public class XmlLayoutFactory {

  private Map layoutById;
  
  public XmlLayoutFactory() {
    this.layoutById = new HashMap();
  }

  public XmlLayout create(Container root, String id) {
    XmlLayout layout = new XmlLayout(root, id);
    layoutById.put(id, layout);
    return layout;
  }

  public XmlLayout get(String id) {
    return (XmlLayout) layoutById.get(id);
  }

  public void read(InputStream xmlIn) {
    SAXParserFactory xmlParserFactory = SAXParserFactory.newInstance();
    xmlParserFactory.setNamespaceAware(false);
    XMLReader xmlReader = null; 
    try {
      xmlReader = xmlParserFactory.newSAXParser().getXMLReader();
      xmlReader.setContentHandler(new XmlLayoutHandler(this));
    } catch (Exception e) {
      throw new XmlLayoutException("Unable to initialize parser.", e);
    }
    InputSource inputSource = new InputSource(xmlIn);
    try {
      xmlReader.parse(inputSource);
    } catch (Exception e) {
      throw new XmlLayoutException("Parsing error.", e);
    }
    try {
      xmlIn.close();
    } catch (Exception e) {
      throw new XmlLayoutException("Unable to close stream.", e);
    }
  }

}
