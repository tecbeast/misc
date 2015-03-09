package de.seipler.bookcollection.xml;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import de.seipler.bookcollection.entity.Author;
import de.seipler.bookcollection.entity.Book;
import de.seipler.bookcollection.entity.Category;
import de.seipler.bookcollection.entity.Edition;
import de.seipler.bookcollection.entity.Genre;
import de.seipler.bookcollection.entity.Language;
import de.seipler.bookcollection.entity.Publisher;
import de.seipler.bookcollection.entity.Series;
import de.seipler.bookcollection.model.AuthorSet;
import de.seipler.bookcollection.model.BookSet;

/**
 * 
 * @author Georg Seipler
 */
public class BookXmlHandler extends DefaultHandler {
  
  public static final String TAG_BOOK_LIST = "book-list";
  public static final String TAG_BOOK = "book";
  public static final String TAG_PUBLISHER_ID = "publisher-id";
  public static final String TAG_TITLE = "title";
  public static final String TAG_SUBTITLE = "subtitle";
  public static final String TAG_AUTHORS = "authors";
  public static final String TAG_AUTHOR_ID = "author-id";
  public static final String TAG_SERIES_ID = "series-id";
  public static final String TAG_BOOK_NUMBER = "book-number";
  public static final String TAG_CATEGORY_ID = "category-id";
  public static final String TAG_GENRE_ID = "genre-id";
  public static final String TAG_LANGUAGE_ID = "language-id";
  public static final String TAG_EDITION_ID = "edition-id";
  public static final String TAG_PAGES = "pages";
  public static final String TAG_PRINTING_DATE = "printing-date";
  public static final String TAG_COVER_FILE = "cover-file";
  public static final String TAG_NOTES = "notes";

  public static final String ATTRIBUTE_ID = "id";

  public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");  

  private StringBuffer value;
  private Book book;
  private BookSet bookSet;
  private AuthorSet authorSet;

  /**
   * Default constructor. 
   */
  public BookXmlHandler(BookSet bookSet) {
    this.bookSet = bookSet;
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
    if (TAG_BOOK.equals(qName)) {
      bookSet.add(book);
    } else if (TAG_PUBLISHER_ID.equals(qName)) {
      Publisher publisher = new Publisher();
      publisher.setId(value.toString().trim());
      book.setPublisher(publisher);
    } else if (TAG_TITLE.equals(qName)) {
      book.setTitle(value.toString().trim());
    } else if (TAG_SUBTITLE.equals(qName)) {
      book.setSubTitle(value.toString().trim());
    } else if (TAG_AUTHORS.equals(qName)) {
      book.setAuthors(authorSet);
      authorSet = new AuthorSet();
    } else if (TAG_AUTHOR_ID.equals(qName)) {
      Author author = new Author();
      author.setId(value.toString().trim());
      authorSet.add(author);
    } else if (TAG_SERIES_ID.equals(qName)) {
      Series series = new Series();
      series.setId(value.toString().trim());
      book.setSeries(series);
    } else if (TAG_BOOK_NUMBER.equals(qName)) {
      String bookNumber = value.toString().trim();
      if (bookNumber.length() > 0) {
        book.setBookNumber(Integer.parseInt(bookNumber));
      }
    } else if (TAG_CATEGORY_ID.equals(qName)) {
      Category category = new Category();
      category.setId(value.toString().trim());
      book.setCategory(category);
    } else if (TAG_GENRE_ID.equals(qName)) {
      Genre genre = new Genre();
      genre.setId(value.toString().trim());
      book.setGenre(genre);
    } else if (TAG_LANGUAGE_ID.equals(qName)) {
      Language language = new Language();
      language.setId(value.toString().trim());
      book.setLanguage(language);
    } else if (TAG_EDITION_ID.equals(qName)) {
      Edition edition = new Edition();
      edition.setId(value.toString().trim());
      book.setEdition(edition);
    } else if (TAG_PAGES.equals(qName)) {
      String pages = value.toString().trim();
      if (pages.length() > 0) {
        book.setPages(Integer.parseInt(pages));
      }
    } else if (TAG_PRINTING_DATE.equals(qName)) {
      String printingDate = value.toString().trim();
      try {
        book.setPrintingDate(DATE_FORMAT.parse(printingDate));
      } catch (ParseException ignored) {
        // uses default
      }
    } else if (TAG_COVER_FILE.equals(qName)) {
      String coverFile = value.toString().trim();
      if (coverFile.length() > 0) {
        book.setCoverFile(new File(coverFile));
      }
    } else if (TAG_NOTES.equals(qName)) {
      String notes = value.toString().trim();
      book.setNotes(convertNotes(notes));
    }
    value = new StringBuffer();
  }

  /**
   * @see org.xml.sax.ContentHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
   */
  public void startElement(String uri, String localName, String qName, Attributes atts) {
    if (TAG_BOOK.equals(qName)) {
      book = new Book();
      book.setId(atts.getValue(ATTRIBUTE_ID).trim());
    } else if (TAG_AUTHORS.equals(qName)) {
      authorSet = new AuthorSet();
    }
  }
 
  private String convertNotes(String rawText) {
    StringBuffer buffer = new StringBuffer();
    boolean escape = false;
    for (int i = 0; i < rawText.length(); i++) {
      switch (rawText.charAt(i)) {
        case '\\':
          if (escape) {
            buffer.append('\\');
            escape = false;
          } else {
            escape = true;
          }
          break;
        case 'n':
          if (escape) {
            buffer.append('\n');
            escape = false;
          } else {
            buffer.append('n');
          }
          break;
        default:
          buffer.append(rawText.charAt(i));
          break;
      }
    }
    return buffer.toString();
  }
  
}
