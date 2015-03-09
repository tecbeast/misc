package de.seipler.bookcollection;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import de.seipler.bookcollection.model.*;
import de.seipler.bookcollection.ui.BookCollectionUi;

/**
 * 
 * @author Georg Seipler
 */
public class BookCollection {

  private BookCollectionUi ui;
  private EntityCache cache;
  
  public BookCollection() {
    super();
  }
  
  public void run() throws ParserConfigurationException, IOException, SAXException {
    this.cache = new EntityCache();
    cache.initialize();
    this.ui = new BookCollectionUi(cache);
    ui.setVisible(true);
  }

  public static void main(String[] args) {
    
    // System.setProperty("javax.xml.parsers.SAXParserFactory", "com.bluecast.xml.JAXPSAXParserFactory");
    
    BookCollection app = null;
    try {
      app = new BookCollection();
      app.run();
    } catch (Exception e) {
      e.printStackTrace();
    }
    
  }  

}
