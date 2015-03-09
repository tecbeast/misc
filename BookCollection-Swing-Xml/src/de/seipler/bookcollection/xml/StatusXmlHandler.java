package de.seipler.bookcollection.xml;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import de.seipler.bookcollection.entity.Status;
import de.seipler.bookcollection.model.StatusSet;

/**
 * 
 * @author Georg Seipler
 */
public class StatusXmlHandler extends DefaultHandler {
  
  public static final String TAG_STATUS_LIST = "status-list";
  public static final String TAG_STATUS = "status";
  public static final String TAG_NAME = "name";

  public static final String ATTRIBUTE_ID = "id";  
  
  private StringBuffer value;
  private Status status;
  private StatusSet statusSet;

  /**
   * Default constructor. 
   */
  public StatusXmlHandler(StatusSet statusSet) {
    this.statusSet = statusSet;
    this.value = new StringBuffer();
  }

  /**
   * @see org.xml.sax.ContentHandler#characters(char, int, int)
   */
  public void characters(char[] ch, int start, int length) {
    value.append(new String(ch, start, length));
  }

  /**
   * @see org.xml.sax.ContentHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
   */
  public void endElement(String uri, String localName, String qName) {
    if (TAG_STATUS.equals(qName)) {
      statusSet.add(status);
    } else if (TAG_NAME.equals(qName)) {
      status.setName(value.toString().trim());
    }
    value = new StringBuffer();
  }

  /**
   * @see org.xml.sax.ContentHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
   */
  public void startElement(String uri, String localName, String qName, Attributes atts) {
    if (TAG_STATUS.equals(qName)) {
      status = new Status();
      status.setId(atts.getValue(ATTRIBUTE_ID).trim());
    }
  }
  
}
