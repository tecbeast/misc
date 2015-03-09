package de.seipler.bookcollection.xml;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import de.seipler.bookcollection.entity.Genre;
import de.seipler.bookcollection.model.GenreSet;

/**
 * 
 * @author Georg Seipler
 */
public class GenreXmlHandler extends DefaultHandler {
  
  public static final String TAG_GENRE_LIST = "genre-list";
  public static final String TAG_GENRE = "genre";
  public static final String TAG_NAME = "name";

  public static final String ATTRIBUTE_ID = "id";  
  
  private StringBuffer value;
  private Genre genre;
  private GenreSet genreSet;

  /**
   * Default constructor. 
   */
  public GenreXmlHandler(GenreSet genreSet) {
    this.genreSet = genreSet;
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
    if (TAG_GENRE.equals(qName)) {
      genreSet.add(genre);
    } else if (TAG_NAME.equals(qName)) {
      genre.setName(value.toString().trim());
    }
    value = new StringBuffer();
  }

  /**
   * @see org.xml.sax.ContentHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
   */
  public void startElement(String uri, String localName, String qName, Attributes atts) {
    if (TAG_GENRE.equals(qName)) {
      genre = new Genre();
      genre.setId(atts.getValue(ATTRIBUTE_ID).trim());
    }
  }
  
}
