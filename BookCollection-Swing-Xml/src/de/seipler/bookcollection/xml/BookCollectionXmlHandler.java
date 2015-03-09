package de.seipler.bookcollection.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import de.seipler.bookcollection.model.EntityCache;

/**
 * 
 * @author Georg Seipler
 */
public class BookCollectionXmlHandler extends DefaultHandler {
  
  public static final String TAG_BOOK_COLLECTION = "book-collection";
  
  private AuthorXmlHandler authorHandler;
  private BookXmlHandler bookHandler;
  private CategoryXmlHandler categoryHandler;
  private EditionXmlHandler editionHandler;
  private GenreXmlHandler genreHandler;
  private LanguageXmlHandler languageHandler;
  private PublisherXmlHandler publisherHandler;
  private RatingXmlHandler ratingHandler;
  private SeriesXmlHandler seriesHandler;
  private StatusXmlHandler statusHandler;
    
  private DefaultHandler handler;

  /**
   * Default constructor. 
   */
  public BookCollectionXmlHandler(EntityCache cache) {
    this.authorHandler = new AuthorXmlHandler(cache.getAllAuthors());
    this.bookHandler = new BookXmlHandler(cache.getAllBooks());
    this.categoryHandler = new CategoryXmlHandler(cache.getAllCategories());
    this.editionHandler = new EditionXmlHandler(cache.getAllEditions());
    this.genreHandler = new GenreXmlHandler(cache.getAllGenres());
    this.languageHandler = new LanguageXmlHandler(cache.getAllLanguages());
    this.publisherHandler = new PublisherXmlHandler(cache.getAllPublishers());
    this.ratingHandler = new RatingXmlHandler(cache.getAllRatings());
    this.seriesHandler = new SeriesXmlHandler(cache.getAllSeries());
    this.statusHandler = new StatusXmlHandler(cache.getAllStatus());
    this.handler = null;
  }

  /**
   * @see org.xml.sax.ContentHandler#characters(char, int, int)
   */
  public void characters(char[] ch, int start, int length) throws SAXException {
    if (handler != null) {
      handler.characters(ch, start, length);
    }
  }

  /**
   * @see org.xml.sax.ContentHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
   */
  public void endElement(String uri, String localName, String qName) throws SAXException {
    if (handler != null) {
      handler.endElement(uri, localName, qName);
    }
    if (
      AuthorXmlHandler.TAG_AUTHOR_LIST.equals(qName)
      || BookXmlHandler.TAG_BOOK_LIST.equals(qName)
      || CategoryXmlHandler.TAG_CATEGORY_LIST.equals(qName)
      || EditionXmlHandler.TAG_EDITION_LIST.equals(qName)
      || GenreXmlHandler.TAG_GENRE_LIST.equals(qName)
      || LanguageXmlHandler.TAG_LANGUAGE_LIST.equals(qName)
      || PublisherXmlHandler.TAG_PUBLISHER_LIST.equals(qName)
      || RatingXmlHandler.TAG_RATING_LIST.equals(qName)
      || SeriesXmlHandler.TAG_SERIES_LIST.equals(qName)
      || StatusXmlHandler.TAG_STATUS_LIST.equals(qName)
    ) {
      handler = null;
    }
  }

  /**
   * @see org.xml.sax.ContentHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
   */
  public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
    if (AuthorXmlHandler.TAG_AUTHOR_LIST.equals(qName)) {
      handler = authorHandler;
    } else if (BookXmlHandler.TAG_BOOK_LIST.equals(qName)) {
      handler = bookHandler;
    } else if (CategoryXmlHandler.TAG_CATEGORY_LIST.equals(qName)) {
      handler = categoryHandler;
    } else if (EditionXmlHandler.TAG_EDITION_LIST.equals(qName)) {
      handler = editionHandler;
    } else if (GenreXmlHandler.TAG_GENRE_LIST.equals(qName)) {
      handler = genreHandler;
    } else if (LanguageXmlHandler.TAG_LANGUAGE_LIST.equals(qName)) {
      handler = languageHandler;
    } else if (PublisherXmlHandler.TAG_PUBLISHER_LIST.equals(qName)) {
      handler = publisherHandler;
    } else if (RatingXmlHandler.TAG_RATING_LIST.equals(qName)) {
      handler = ratingHandler;
    } else if (SeriesXmlHandler.TAG_SERIES_LIST.equals(qName)) {
      handler = seriesHandler;
    } else if (StatusXmlHandler.TAG_STATUS_LIST.equals(qName)) {
      handler = statusHandler;
    }
    if (handler != null) {
      handler.startElement(uri, localName, qName, atts);
    }     
  }
  
}
