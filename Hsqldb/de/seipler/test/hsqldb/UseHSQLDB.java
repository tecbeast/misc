package de.seipler.test.hsqldb;

import java.sql.*;

import de.seipler.util.log.*;

/**
 * 
 * @author Georg Seipler
 */
public class UseHSQLDB {
	
	public static void main(String[] args) {
    
    Log log = LogFactory.getLog(UseHSQLDB.class);
    
    try {
    	
	    log.info("Starting to load JDBC driver ...");
	    Class.forName("org.hsqldb.jdbcDriver");
	    log.info("JDBC driver successfully loaded !");
	    
	    Connection con = DriverManager.getConnection("jdbc:hsqldb:C:/temp/cdShop", "sa", "");
			log.info("Connection established");
			
			Statement stmt = con.createStatement();
			
			String sqlQuery = "CREATE TABLE cdShop(cdNr INTEGER, cdArtist CHAR(20), cdTitle CHAR(20));";
			ResultSet rs = stmt.executeQuery(sqlQuery);
			
			sqlQuery = "INSERT INTO cdShop VALUES(1, 'Groenemeyer', 'Mensch');";
			rs = stmt.executeQuery(sqlQuery);
			
			sqlQuery = "INSERT INTO cdShop VALUES(2, 'Sting', 'Fields of Gold');";
			rs = stmt.executeQuery(sqlQuery);
			
			sqlQuery = "INSERT INTO cdShop VALUES(3, 'Bach', 'Pluratorium');";
			rs = stmt.executeQuery(sqlQuery);
			
			sqlQuery = "UPDATE cdShop SET cdTitle='Pure Nonsense' where cdNr=1;";
			rs = stmt.executeQuery(sqlQuery);
			
			sqlQuery = "SELECT * from cdShop;";
			rs = stmt.executeQuery(sqlQuery);
			
			System.out.println();
			System.out.println("=== CD Shop DB ===");
			System.out.println();				     
	    
	    int counter = 0;
	    while (rs.next()) {
	    	System.out.println(++counter + ". Datensatz");
	    	int cdNr = rs.getInt("cdNr");
				System.out.println("\t[cdNr -> " + cdNr + "]");
				String cdArtist = rs.getString("cdArtist");
				System.out.println("\t[cdArtist -> " + cdArtist + "]");
				String cdTitle = rs.getString("cdTitle");
				System.out.println("\t[cdTitle -> " + cdTitle + "]");
	    }
	    
	    con.close();
	    
		} catch (Exception all) {
			log.error(all);
		}
    
  }

}
