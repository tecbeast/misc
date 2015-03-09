package de.seipler.bookcollection.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import de.seipler.bookcollection.xml.BookCollectionXmlHandler;

/**
 * 
 * @author Georg Seipler
 */
public class EntityCache {

  private BookSet allBooks;  
  private AuthorSet allAuthors;
  private CategorySet allCategories;
  private EditionSet allEditions;
  private GenreSet allGenres;
  private LanguageSet allLanguages;
  private PublisherSet allPublishers;
  private RatingSet allRatings;
  private SeriesSet allSeries;
  private StatusSet allStatus;
  
  public EntityCache() {
    allBooks = new BookSet();
    allAuthors = new AuthorSet();
    allCategories = new CategorySet();
    allEditions = new EditionSet();
    allGenres = new GenreSet();
    allLanguages = new LanguageSet();
    allPublishers = new PublisherSet();
    allRatings = new RatingSet();
    allSeries = new SeriesSet();
    allStatus = new StatusSet();
  }

  public AuthorSet getAllAuthors() {
    return this.allAuthors;
  }

  public BookSet getAllBooks() {
    return this.allBooks;
  }
  
  public CategorySet getAllCategories() {
    return this.allCategories;
  }

  public EditionSet getAllEditions() {
    return this.allEditions;
  }

  public GenreSet getAllGenres() {
    return this.allGenres;
  }

  public LanguageSet getAllLanguages() {
    return this.allLanguages;
  }
  
  public PublisherSet getAllPublishers() {
    return this.allPublishers;
  }

  public RatingSet getAllRatings() {
    return this.allRatings;
  }

  public SeriesSet getAllSeries() {
    return this.allSeries;
  }
  
  public StatusSet getAllStatus() {
    return this.allStatus;
  }

  public void initialize() throws ParserConfigurationException, IOException, SAXException, IllegalStateException {

    SAXParserFactory xmlParserFactory = SAXParserFactory.newInstance();
    xmlParserFactory.setNamespaceAware(false);
    xmlParserFactory.setValidating(false);
    XMLReader xmlReader = xmlParserFactory.newSAXParser().getXMLReader();
    xmlReader.setContentHandler(new BookCollectionXmlHandler(this));
    
    String[] resources = new String[] {
      "/data/authors.xml",
      "/data/books.xml",
      "/data/categories.xml",
      "/data/editions.xml",
      "/data/genres.xml",
      "/data/languages.xml",
      "/data/publishers.xml",
      "/data/ratings.xml",
      "/data/series.xml",
      "/data/status.xml"
    };
    
    for (int i = 0; i < resources.length; i++) {
      BufferedReader xmlIn = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(resources[i])));
      InputSource inputSource = new InputSource(xmlIn);
      xmlReader.parse(inputSource);
      xmlIn.close();
    }
    
    if (!getAllBooks().validate(this)) {
      throw new IllegalStateException("There are books with invalid references.");
    }
   
  }

}
