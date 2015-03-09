package de.seipler.test.hsqldb;

import java.sql.*;

import de.seipler.util.format.StringFormatter;

/**
 * 
 * @author Georg Seipler
 */
public class BookCollection {
	
	public static void main(String[] args) {
    
		Connection con = null;
    
    try {
    	
	    Class.forName("org.hsqldb.jdbcDriver");
	    
	    con = DriverManager.getConnection("jdbc:hsqldb:C:/temp/bookCollection", "sa", "");
			
			Statement stmt = con.createStatement();
			
			String sqlQuery = null;
			ResultSet rs = null;
			
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

			sqlQuery = "CREATE TABLE authors(" +
			stmt.executeQuery(sqlQuery);

			sqlQuery = "CREATE TABLE publishers(" +
			stmt.executeQuery(sqlQuery);

			sqlQuery = "CREATE TABLE series(" +
				"books_total INTEGER NULL);";
			stmt.executeQuery(sqlQuery);			

			sqlQuery = "CREATE TABLE books(" +
				"pages INTEGER NULL," +
				"FOREIGN KEY(publishers_index) REFERENCES publishers(index));";
			stmt.executeQuery(sqlQuery);
		
			sqlQuery = "CREATE TABLE books2authors(" +
			stmt.executeQuery(sqlQuery);

			sqlQuery = "CREATE TABLE books2series(" +
			stmt.executeQuery(sqlQuery);

			sqlQuery = "INSERT INTO authors VALUES(NULL, 'Hobb', 'Robin', NULL);";
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
	
			sqlQuery = "SELECT books.isbn, books.title, series.name AS series, books2series.book_number, series.books_total, CONCAT(CONCAT(authors.name, ', '), authors.first_name) AS author, publishers.name AS publisher, books.pages, books.language, books.edition, books.printing_date" +
				" FROM books, publishers, authors, books2authors, series, books2series" +
			rs = stmt.executeQuery(sqlQuery);

	    int counter = 0;
	    while (rs.next()) {
	    	ResultSetMetaData md = rs.getMetaData();
	    	for (int i = 1; i <= md.getColumnCount(); i++) {
	    		System.out.println(StringFormatter.leftAlignText(md.getColumnLabel(i), 14) + ": " + rs.getString(i));
	    	}
	    	System.out.println();
	    	counter++;
	    }
	    
	    System.out.println(counter + " hits total.");
	    
		} catch (Exception all) {
			all.printStackTrace();
		
		} finally {
			try { con.close(); } catch (Exception ignored) { }
			
		}
    
  }

}