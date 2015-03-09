package de.seipler.test.descriptors.xml;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 
 * @author Georg Seipler
 */
public class DescriptorListHandler extends DefaultHandler {

  private DescriptorList descriptorList;
  private DescriptorHandler currentHandler;
  private Map handlerRegistry;
  private File file;

  /**
   * Constructor for MP3DescriptorReader.
   */
  public DescriptorListHandler() {
    this.handlerRegistry = new HashMap();
  }

  /**
   * @see org.xml.sax.ContentHandler#startDocument()
   */
  public void startDocument() {
    this.descriptorList = new DescriptorList();
    this.currentHandler = null;
  }

  /**
   * @see org.xml.sax.ContentHandler#characters(char, int, int)
   */
  public void characters(char[] ch, int start, int length) {
    if (this.currentHandler != null) { this.currentHandler.characters(ch, start, length); }
  }

  /**
   * @see org.xml.sax.ContentHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
   */
  public void endElement(String uri, String localName, String qName) {
    if (this.currentHandler != null) {
      if ("descriptor".equals(qName)) {
        this.currentHandler.endDescriptor();
        this.descriptorList.add(this.currentHandler.getDescriptor());
        this.currentHandler = null;
      } else {
        this.currentHandler.endElement(qName);
      }
    }
  }

  /**
   * @see org.xml.sax.ContentHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
   */
  public void startElement(String uri, String localName, String qName, Attributes atts) {
    if (this.currentHandler != null) {
      this.currentHandler.startElement(qName);
      if (atts != null) {
        for (int i = 0; i < atts.getLength(); i++) {
          this.currentHandler.setAttribute(atts.getQName(i), atts.getValue(i));
        }
      }
    } else if ("descriptor".equals(qName)) {
      if ((atts != null) && "type".equals(atts.getQName(0))) {
        this.currentHandler = (DescriptorHandler) this.handlerRegistry.get(atts.getValue(0));
        this.currentHandler.setFile(this.file);
        this.currentHandler.startDescriptor();
      }
    }
  }

  /**
   * @see org.xml.sax.ContentHandler#endDocument()
   */
  public void endDocument() {
  }

  /**
   * 
   */
  public DescriptorList getDescriptorList() {
    return this.descriptorList;
  }

  /**
   * 
   */
  public void registerHandler(DescriptorHandler handler) {
    this.handlerRegistry.put(handler.getType(), handler);
  }

  /**
   * Returns the file.
   * @return File
   */
  public File getFile() {
    return this.file;
  }

  /**
   * Sets the file.
   * @param file The file to set
   */
  public void setFile(File file) {
    this.file = file;
  }

}
