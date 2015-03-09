package de.seipler.bookcollection;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import de.seipler.bookcollection.db.DbAdapter;
import de.seipler.bookcollection.db.TestDb;
import de.seipler.bookcollection.ui.BookCollectionUi;

/**
 * 
 * @author Georg Seipler
 */
public class BookCollection {
  
  private BookCollectionUi ui;
  private TestDb dbConfiguration;
  private DbAdapter dbAdapter;
  private EntityCache cache;
  
  public BookCollection() {
    this.dbConfiguration = new TestDb();
    this.dbAdapter = new DbAdapter(dbConfiguration);
    this.cache = new EntityCache(dbAdapter);
  }
  
  public void run() throws SQLException {
    dbConfiguration.initialize();
    dbAdapter.setConnection(dbConfiguration.getConnection());  // Memory DB only
    dbAdapter.initialize();
    cache.initialize();
    this.ui = new BookCollectionUi(cache);
    ui.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        close();
      }
    });
    ui.setVisible(true);
  }

  public void close() {
    try {
      dbConfiguration.close();
      dbAdapter.close();
    } catch (SQLException sqle) {
      sqle.printStackTrace();
    }
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
