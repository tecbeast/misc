package de.seipler.util.swing.layout;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 
 * @author Georg Seipler
 */
public class LayoutWrapperXmlHandler extends DefaultHandler {

  public static final String TAG_LAYOUT_LIST = "layout-list";
  public static final String TAG_LAYOUT = "layout";
  
  public static final String ATTRIBUTE_ID = "id";

  private DefaultHandler handler;
  private LayoutWrapperFactory factory;
  
  public LayoutWrapperXmlHandler(LayoutWrapperFactory factory) {
    this.factory = factory;
  }
  
  public void startDocument() throws SAXException {
    this.handler = null;
  }
  
  public void characters(char[] ch, int start, int length) throws SAXException {
    if (handler != null) {
      handler.characters(ch, start, length);
    }
  }
  
  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    if (TAG_LAYOUT.equals(qName)) {
      String id = attributes.getValue(ATTRIBUTE_ID);
      LayoutWrapper wrapper = factory.get(id);
      if (wrapper == null) {
        throw new SAXException("No LayoutWrapper with id " + id + " registered.");
      }
      this.handler = wrapper.getXmlHandler();
    } else if (handler != null) {
      handler.startElement(uri, localName, qName, attributes);
    }
  }

  public void endElement(String uri, String localName, String qName) throws SAXException {
    if (handler != null) {
      handler.endElement(uri, localName, qName);
    }
  }

}
