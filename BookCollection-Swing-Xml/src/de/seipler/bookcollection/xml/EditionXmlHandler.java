package de.seipler.bookcollection.xml;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import de.seipler.bookcollection.entity.Edition;
import de.seipler.bookcollection.model.EditionSet;

/**
 * 
 * @author Georg Seipler
 */
public class EditionXmlHandler extends DefaultHandler {
  
  public static final String TAG_EDITION_LIST = "edition-list";
  public static final String TAG_EDITION = "edition";
  public static final String TAG_NAME = "name";

  public static final String ATTRIBUTE_ID = "id";  
  
  private StringBuffer value;
  private Edition edition;
  private EditionSet editionSet;

  /**
   * Default constructor. 
   */
  public EditionXmlHandler(EditionSet editionSet) {
    this.editionSet = editionSet;
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
    if (TAG_EDITION.equals(qName)) {
      editionSet.add(edition);
    } else if (TAG_NAME.equals(qName)) {
      edition.setName(value.toString().trim());
    }
    value = new StringBuffer();
  }

  /**
   * @see org.xml.sax.ContentHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
   */
  public void startElement(String uri, String localName, String qName, Attributes atts) {
    if (TAG_EDITION.equals(qName)) {
      edition = new Edition();
      edition.setId(atts.getValue(ATTRIBUTE_ID).trim());
    }
  }
  
}
