package de.seipler.bookcollection.xml;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import de.seipler.bookcollection.entity.Series;
import de.seipler.bookcollection.model.SeriesSet;

/**
 * 
 * @author Georg Seipler
 */
public class SeriesXmlHandler extends DefaultHandler {
  
  public static final String TAG_SERIES_LIST = "series-list";
  public static final String TAG_SERIES = "series";
  public static final String TAG_NAME = "name";
  public static final String TAG_BOOKS_TOTAL = "books-total";

  public static final String ATTRIBUTE_ID = "id";  
  
  private StringBuffer value;
  private Series series;
  private SeriesSet seriesSet;

  /**
   * Default constructor. 
   */
  public SeriesXmlHandler(SeriesSet seriesSet) {
    this.seriesSet = seriesSet;
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
    if (TAG_SERIES.equals(qName)) {
      seriesSet.add(series);
    } else if (TAG_NAME.equals(qName)) {
      series.setName(value.toString().trim());
    } else if (TAG_BOOKS_TOTAL.equals(qName)) {
      String booksTotal = value.toString().trim();
      if (booksTotal.length() > 0) {
        series.setBooksTotal(Integer.parseInt(booksTotal));
      }
    }
    value = new StringBuffer();
  }

  /**
   * @see org.xml.sax.ContentHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
   */
  public void startElement(String uri, String localName, String qName, Attributes atts) {
    if (TAG_SERIES.equals(qName)) {
      series = new Series();
      series.setId(atts.getValue(ATTRIBUTE_ID).trim());
    }
  }
  
}
