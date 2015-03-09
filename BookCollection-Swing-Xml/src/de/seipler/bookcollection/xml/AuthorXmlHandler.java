package de.seipler.bookcollection.xml;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import de.seipler.bookcollection.entity.Author;
import de.seipler.bookcollection.model.AuthorSet;

/**
 * 
 * @author Georg Seipler
 */
public class AuthorXmlHandler extends DefaultHandler {
  
  public static final String TAG_AUTHOR_LIST = "author-list";
  public static final String TAG_AUTHOR = "author";
  public static final String TAG_NAME = "name";
  public static final String TAG_SURNAME = "surname";
  public static final String TAG_TITLE = "title";
  public static final String TAG_SEX = "sex";

  public static final String ATTRIBUTE_ID = "id";  
  
  private StringBuffer value;
  private Author author;
  private AuthorSet authorSet;

  /**
   * Default constructor. 
   */
  public AuthorXmlHandler(AuthorSet authorSet) {
    this.authorSet = authorSet;
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
    if (TAG_AUTHOR.equals(qName)) {
      authorSet.add(author);
    } else if (TAG_NAME.equals(qName)) {
      author.setName(value.toString().trim());
    } else if (TAG_SURNAME.equals(qName)) {
      author.setSurname(value.toString().trim());
    } else if (TAG_TITLE.equals(qName)) {
      author.setTitle(value.toString().trim());
    } else if (TAG_SEX.equals(qName)) {
      String sex = value.toString().trim();
      if ("female".equals(sex)) {
        author.setSex(Author.SEX_FEMALE);
      } else if ("male".equals(sex)) {
        author.setSex(Author.SEX_MALE);
      } else if ("no person".equals(sex)) {
        author.setSex(Author.SEX_NO_PERSON);
      }
    }
    value = new StringBuffer();
  }

  /**
   * @see org.xml.sax.ContentHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
   */
  public void startElement(String uri, String localName, String qName, Attributes atts) {
    if (TAG_AUTHOR.equals(qName)) {
      author = new Author();
      author.setId(atts.getValue(ATTRIBUTE_ID).trim());
    }
  }
  
}
