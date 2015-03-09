package de.seipler.bookcollection.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * 
 * @author Georg Seipler
 */
public class TestDb implements DbConfiguration {
  
  private Properties properties;
  private Connection connection;
  
  public TestDb() {
    this.properties = new Properties();
    properties.put("user", "sa");
    properties.put("password", "");
  }
  
  public String getJdbcDriverClass() {
    return "org.hsqldb.jdbcDriver";
  }

  public String getUrl() {
    return "jdbc:hsqldb:.";
    // return "jdbc:hsqldb:bookCollection";
  }
  
  public Properties getProperties() {
    return this.properties;    
  }
  
  public Connection getConnection() {
    return this.connection;
  }

  public void initialize() throws SQLException {

    try {
      Class.forName(getJdbcDriverClass());
    } catch (ClassNotFoundException cnfe) {
      throw new SQLException("JDBCDriver Class not found");
    }  
    this.connection = DriverManager.getConnection(getUrl(), getProperties());

    Statement stmt = connection.createStatement();
      
    String sqlQuery = null;
      
    sqlQuery = "DROP TABLE books2series IF EXISTS;";
    stmt.executeQuery(sqlQuery);

    sqlQuery = "DROP TABLE books2authors IF EXISTS;";
    stmt.executeQuery(sqlQuery);

    sqlQuery = "DROP TABLE books IF EXISTS;";
    stmt.executeQuery(sqlQuery);

    sqlQuery = "DROP TABLE authors IF EXISTS;";
    stmt.executeQuery(sqlQuery);

    sqlQuery = "DROP TABLE publishers IF EXISTS;";
    stmt.executeQuery(sqlQuery);

    sqlQuery = "DROP TABLE series IF EXISTS;";
    stmt.executeQuery(sqlQuery);

    sqlQuery = "DROP TABLE categories IF EXISTS;";
    stmt.executeQuery(sqlQuery);

    sqlQuery = "DROP TABLE genres IF EXISTS;";
    stmt.executeQuery(sqlQuery);

    sqlQuery = "DROP TABLE editions IF EXISTS;";
    stmt.executeQuery(sqlQuery);

    sqlQuery = "DROP TABLE languages IF EXISTS;";
    stmt.executeQuery(sqlQuery);

    sqlQuery = "CREATE CACHED TABLE authors(" +
      "index INTEGER IDENTITY," +
      "name VARCHAR(20) NOT NULL," +
      "first_name VARCHAR(20) NULL," +
      "title VARCHAR(10) NULL," +
      "sex CHAR(1) NOT NULL);";
    stmt.executeQuery(sqlQuery);

    sqlQuery = "CREATE CACHED TABLE publishers(" +
      "index INTEGER IDENTITY," +
      "name VARCHAR(40) NOT NULL);";
    stmt.executeQuery(sqlQuery);

    sqlQuery = "CREATE CACHED TABLE series(" +
      "index INTEGER IDENTITY," +
      "name VARCHAR(80) NOT NULL," +
      "books_total INTEGER NULL);";
    stmt.executeQuery(sqlQuery);      

    sqlQuery = "CREATE CACHED TABLE books(" +
      "isbn CHAR(10) NOT NULL PRIMARY KEY," +
      "title VARCHAR(80) NOT NULL," +
      "publishers_index INTEGER NOT NULL," +
      "pages INTEGER NULL," +
      "language VARCHAR(20) NULL," +
      "edition VARCHAR(20) NULL," +
      "printing_date DATE NULL," +
      "FOREIGN KEY(publishers_index) REFERENCES publishers(index));";
    stmt.executeQuery(sqlQuery);
    
    sqlQuery = "CREATE CACHED TABLE books2authors(" +
      "books_isbn CHAR(13) NOT NULL," +
      "authors_index INTEGER NOT NULL," +
      "PRIMARY KEY(books_isbn, authors_index)," +
      "FOREIGN KEY(books_isbn) REFERENCES books(isbn)," +
      "FOREIGN KEY(authors_index) REFERENCES authors(index));";
    stmt.executeQuery(sqlQuery);

    sqlQuery = "CREATE CACHED TABLE books2series(" +
      "books_isbn CHAR(13) NOT NULL," +
      "series_index INTEGER NOT NULL," +
      "book_number INTEGER NOT NULL," +
      "PRIMARY KEY(books_isbn, series_index)," +
      "FOREIGN KEY(books_isbn) REFERENCES books(isbn)," +
      "FOREIGN KEY(series_index) REFERENCES series(index));";
    stmt.executeQuery(sqlQuery);
    
    sqlQuery = "CREATE CACHED TABLE genres(" +
      "index INTEGER IDENTITY," +
      "name VARCHAR(40) NOT NULL);";
    stmt.executeQuery(sqlQuery);

    sqlQuery = "CREATE CACHED TABLE categories(" +
      "index INTEGER IDENTITY," +
      "name VARCHAR(40) NOT NULL);";
    stmt.executeQuery(sqlQuery);

    sqlQuery = "CREATE CACHED TABLE editions(" +
      "index INTEGER IDENTITY," +
      "name VARCHAR(40) NOT NULL);";
    stmt.executeQuery(sqlQuery);

    sqlQuery = "CREATE CACHED TABLE languages(" +
      "index INTEGER IDENTITY," +
      "name VARCHAR(40) NOT NULL);";
    stmt.executeQuery(sqlQuery);

    sqlQuery = "INSERT INTO authors VALUES(NULL, 'Hobb', 'Robin', NULL, 'F');";
    stmt.executeQuery(sqlQuery);
    sqlQuery = "INSERT INTO authors VALUES(NULL, 'Williams', 'Tad', NULL, 'M');";
    stmt.executeQuery(sqlQuery);
    sqlQuery = "INSERT INTO authors VALUES(NULL, 'Hickman', 'Tracy', NULL, 'M');";
    stmt.executeQuery(sqlQuery);
    sqlQuery = "INSERT INTO authors VALUES(NULL, 'Weis', 'Margaret', NULL, 'F');";
    stmt.executeQuery(sqlQuery);
    sqlQuery = "INSERT INTO authors VALUES(NULL, 'Asprin', 'Robert', NULL, 'M');";
    stmt.executeQuery(sqlQuery);
    sqlQuery = "INSERT INTO authors VALUES(NULL, 'Martin', 'George R. R.', NULL, 'M');";
    stmt.executeQuery(sqlQuery);
    sqlQuery = "INSERT INTO authors VALUES(NULL, 'Tolkien', 'J. R. R.', NULL, 'M');";
    stmt.executeQuery(sqlQuery);

    sqlQuery = "INSERT INTO publishers VALUES(NULL, 'Bantam Books');";
    stmt.executeQuery(sqlQuery);
    sqlQuery = "INSERT INTO publishers VALUES(NULL, 'Spectra');";
    stmt.executeQuery(sqlQuery);

    sqlQuery = "INSERT INTO series VALUES(NULL, 'The Liveship Traders', 3);";
    stmt.executeQuery(sqlQuery);      

    sqlQuery = "INSERT INTO books VALUES('0553575635', 'Ship of Magic', 0, 809, 'English', 'Mass Market Paperback', '1999-02-02');";
    stmt.executeQuery(sqlQuery);      
    sqlQuery = "INSERT INTO books VALUES('0553575643', 'Mad Ship', 0, 864, 'English', 'Mass Market Paperback', '2000-02-29');";
    stmt.executeQuery(sqlQuery);      
    sqlQuery = "INSERT INTO books VALUES('0553575651', 'Ship of Destiny', 1, 789, 'English', 'Mass Market Paperback', '2001-11-27');";
    stmt.executeQuery(sqlQuery);      

    sqlQuery = "INSERT INTO books2authors VALUES('0553575635', 0);";
    stmt.executeQuery(sqlQuery);      
    sqlQuery = "INSERT INTO books2authors VALUES('0553575643', 0);";
    stmt.executeQuery(sqlQuery);      
    sqlQuery = "INSERT INTO books2authors VALUES('0553575651', 0);";
    stmt.executeQuery(sqlQuery);      

    sqlQuery = "INSERT INTO books2series VALUES('0553575635', 0, 1);";
    stmt.executeQuery(sqlQuery);      
    sqlQuery = "INSERT INTO books2series VALUES('0553575643', 0, 2);";
    stmt.executeQuery(sqlQuery);      
    sqlQuery = "INSERT INTO books2series VALUES('0553575651', 0, 3);";
    stmt.executeQuery(sqlQuery);
    
    sqlQuery = "INSERT INTO categories VALUES(NULL, 'Roman');";
    stmt.executeQuery(sqlQuery);
    sqlQuery = "INSERT INTO categories VALUES(NULL, 'Comic');";
    stmt.executeQuery(sqlQuery);

    sqlQuery = "INSERT INTO genres VALUES(NULL, 'Fantasy');";
    stmt.executeQuery(sqlQuery);
    sqlQuery = "INSERT INTO genres VALUES(NULL, 'Science-Fiction');";
    stmt.executeQuery(sqlQuery);

    sqlQuery = "INSERT INTO editions VALUES(NULL, 'Softcover');";
    stmt.executeQuery(sqlQuery);
    sqlQuery = "INSERT INTO editions VALUES(NULL, 'Hardcover');";
    stmt.executeQuery(sqlQuery);

    sqlQuery = "INSERT INTO languages VALUES(NULL, 'Deutsch');";
    stmt.executeQuery(sqlQuery);
    sqlQuery = "INSERT INTO languages VALUES(NULL, 'Englisch');";
    stmt.executeQuery(sqlQuery);

  }
  
  public void close() throws SQLException {
    if (connection != null) {
      connection.close();
      connection = null;
    }
  }

}
