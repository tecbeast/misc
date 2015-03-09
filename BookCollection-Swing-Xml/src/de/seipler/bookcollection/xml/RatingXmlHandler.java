package de.seipler.bookcollection.xml;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import de.seipler.bookcollection.entity.Rating;
import de.seipler.bookcollection.model.RatingSet;

/**
 * 
 * @author Georg Seipler
 */
public class RatingXmlHandler extends DefaultHandler {
  
  public static final String TAG_RATING_LIST = "rating-list";
  public static final String TAG_RATING = "rating";
  public static final String TAG_NAME = "name";
  public static final String TAG_VALUE = "value";

  public static final String ATTRIBUTE_ID = "id";  
  
  private StringBuffer value;
  private Rating rating;
  private RatingSet ratingSet;

  /**
   * Default constructor. 
   */
  public RatingXmlHandler(RatingSet ratingSet) {
    this.ratingSet = ratingSet;
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
    if (TAG_RATING.equals(qName)) {
      ratingSet.add(rating);
    } else if (TAG_NAME.equals(qName)) {
      rating.setName(value.toString().trim());
    } else if (TAG_VALUE.equals(qName)) {
      int ratingValue = Integer.parseInt(value.toString().trim());
      rating.setValue(ratingValue);
    }
    value = new StringBuffer();
  }

  /**
   * @see org.xml.sax.ContentHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
   */
  public void startElement(String uri, String localName, String qName, Attributes atts) {
    if (TAG_RATING.equals(qName)) {
      rating = new Rating();
      rating.setId(atts.getValue(ATTRIBUTE_ID).trim());
    }
  }
  
}
