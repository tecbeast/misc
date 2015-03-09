package de.seipler.bookcollection.xml;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import de.seipler.bookcollection.entity.Language;
import de.seipler.bookcollection.model.LanguageSet;

/**
 * 
 * @author Georg Seipler
 */
public class LanguageXmlHandler extends DefaultHandler {
  
  public static final String TAG_LANGUAGE_LIST = "language-list";
  public static final String TAG_LANGUAGE = "language";
  public static final String TAG_NAME = "name";

  public static final String ATTRIBUTE_ID = "id";  
  
  private StringBuffer value;
  private Language language;
  private LanguageSet languageSet;

  /**
   * Default constructor. 
   */
  public LanguageXmlHandler(LanguageSet languageSet) {
    this.languageSet = languageSet;
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
    if (TAG_LANGUAGE.equals(qName)) {
      languageSet.add(language);
    } else if (TAG_NAME.equals(qName)) {
      language.setName(value.toString().trim());
    }
    value = new StringBuffer();
  }

  /**
   * @see org.xml.sax.ContentHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
   */
  public void startElement(String uri, String localName, String qName, Attributes atts) {
    if (TAG_LANGUAGE.equals(qName)) {
      language = new Language();
      language.setId(atts.getValue(ATTRIBUTE_ID).trim());
    }
  }
  
}
