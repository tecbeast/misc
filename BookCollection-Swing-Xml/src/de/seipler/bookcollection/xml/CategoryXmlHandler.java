package de.seipler.bookcollection.xml;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import de.seipler.bookcollection.entity.Category;
import de.seipler.bookcollection.model.CategorySet;

/**
 * 
 * @author Georg Seipler
 */
public class CategoryXmlHandler extends DefaultHandler {
  
  public static final String TAG_CATEGORY_LIST = "category-list";
  public static final String TAG_CATEGORY = "category";
  public static final String TAG_NAME = "name";

  public static final String ATTRIBUTE_ID = "id";  
  
  private StringBuffer value;
  private Category category;
  private CategorySet categorySet;

  /**
   * Default constructor. 
   */
  public CategoryXmlHandler(CategorySet categorySet) {
    this.categorySet = categorySet;
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
    if (TAG_CATEGORY.equals(qName)) {
      categorySet.add(category);
    } else if (TAG_NAME.equals(qName)) {
      category.setName(value.toString().trim());
    }
    value = new StringBuffer();
  }

  /**
   * @see org.xml.sax.ContentHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
   */
  public void startElement(String uri, String localName, String qName, Attributes atts) {
    if (TAG_CATEGORY.equals(qName)) {
      category = new Category();
      category.setId(atts.getValue(ATTRIBUTE_ID).trim());
    }
  }
  
}
