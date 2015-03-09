package de.seipler.ausgaben.datenversorgung.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * @author Georg Seipler
 */
public final class DbInitializer {
    
    public static void init(Connection pConnection) throws SQLException {
        
        Statement statement = pConnection.createStatement();
        String sqlQuery = null;

        // Tabellenstruktur fuer Tabelle fixe_kosten
        
        sqlQuery = "DROP TABLE fixe_kosten IF EXISTS;";
        statement.executeQuery(sqlQuery);
        sqlQuery = "CREATE TABLE fixe_kosten ("
                 + "  id INTEGER NOT NULL IDENTITY,"
                 + "  startdatum DATE NOT NULL,"
                 + "  endedatum DATE,"
                 + "  zahlungsfrequenz INTEGER NOT NULL,"
                 + "  betrag DOUBLE NOT NULL,"
                 + "  artikel VARCHAR(80) NOT NULL,"
                 + "  kategorie INTEGER NOT NULL,"
                 + "  verkaeufer INTEGER NOT NULL,"
                 + "  zahlungsart INTEGER NOT NULL,"
                 + "  bemerkung LONGVARCHAR,"
                 + "  PRIMARY KEY (id)"
                 + ");";
        statement.executeQuery(sqlQuery);
        
        // Daten fuer Tabelle fixe_kosten
        
        // Tabellenstruktur fuer Tabelle kaeufer

        sqlQuery = "DROP TABLE kaeufer IF EXISTS;";
        statement.executeQuery(sqlQuery);
        sqlQuery = "CREATE TABLE kaeufer ("
                 + "  id INTEGER NOT NULL IDENTITY,"
                 + "  name VARCHAR(20) NOT NULL,"
                 + "  beschreibung LONGVARCHAR,"
                 + "  PRIMARY KEY (id)"
                 + ");";
        statement.executeQuery(sqlQuery);
        
        // Daten fuer Tabelle kaeufer

        sqlQuery = "INSERT INTO kaeufer VALUES (NULL, 'Georg', NULL);";
        statement.executeQuery(sqlQuery);
        sqlQuery = "INSERT INTO kaeufer VALUES (NULL, 'Sabine', NULL);";
        statement.executeQuery(sqlQuery);

        // Tabellenstruktur für Tabelle kategorien
        
        sqlQuery = "DROP TABLE kategorien IF EXISTS;";
        statement.executeQuery(sqlQuery);
        sqlQuery = "CREATE TABLE kategorien ("
                 + "  id INTEGER NOT NULL IDENTITY,"
                 + "  name VARCHAR(20) NOT NULL,"
                 + "  beschreibung LONGVARCHAR,"
                 + "  PRIMARY KEY (id)"
                 + ");";
        statement.executeQuery(sqlQuery);
        
        // Daten fuer Tabelle kategorien

        sqlQuery = "INSERT INTO kategorien VALUES (NULL, 'Lebensmittel', NULL);";
        statement.executeQuery(sqlQuery);
        sqlQuery = "INSERT INTO kategorien VALUES (NULL, 'Auto', NULL);";
        statement.executeQuery(sqlQuery);
        sqlQuery = "INSERT INTO kategorien VALUES (NULL, 'Kleidung', NULL);";
        statement.executeQuery(sqlQuery);

        // Tabellenstruktur fuer Tabelle variable_kosten

        sqlQuery = "DROP TABLE variable_kosten IF EXISTS;";
        statement.executeQuery(sqlQuery);
        sqlQuery = "CREATE TABLE variable_kosten ("
                 + "  id INTEGER NOT NULL IDENTITY,"
                 + "  kaufdatum DATE NOT NULL,"
                 + "  betrag DOUBLE NOT NULL,"
                 + "  artikel VARCHAR(80) NOT NULL,"
                 + "  kategorie INTEGER NOT NULL,"
                 + "  verkaeufer INTEGER NOT NULL,"
                 + "  kaeufer INTEGER NOT NULL,"
                 + "  zahlungsart INTEGER NOT NULL,"
                 + "  bemerkung LONGVARCHAR,"
                 + "  PRIMARY KEY (id)"
                 + ");";
        statement.executeQuery(sqlQuery);

        // Daten fuer Tabelle variable-kosten
        
        // Tabellenstruktur fuer Tabelle anbieter

        sqlQuery = "DROP TABLE anbieter IF EXISTS;";
        statement.executeQuery(sqlQuery);
        sqlQuery = "CREATE TABLE anbieter ("
                 + "  id INTEGER NOT NULL,"
                 + "  name VARCHAR(40) NOT NULL,"
                 + "  beschreibung LONGVARCHAR,"
                 + "  PRIMARY KEY (id)"
                 + ");";
        statement.executeQuery(sqlQuery);

        // Daten fuer Tabelle anbieter

        sqlQuery = "INSERT INTO anbieter VALUES (1, 'Marktkauf Asperg', NULL);";
        statement.executeQuery(sqlQuery);
        sqlQuery = "INSERT INTO anbieter VALUES (2, 'Breuningerland Ludwigsburg', NULL);";
        statement.executeQuery(sqlQuery);
        
        // Tabellenstruktur fuer Tabelle zahlungsarten

        sqlQuery = "DROP TABLE zahlungsarten IF EXISTS;";
        statement.executeQuery(sqlQuery);
        sqlQuery = "CREATE TABLE zahlungsarten ("
                 + "  id INTEGER NOT NULL IDENTITY,"
                 + "  name VARCHAR(20) NOT NULL,"
                 + "  beschreibung LONGVARCHAR,"
                 + "  PRIMARY KEY (id)"
                 + ");";
        statement.executeQuery(sqlQuery);

        // Daten fuer Tabelle zahlungsarten

        sqlQuery = "INSERT INTO zahlungsarten VALUES (NULL, 'bar', NULL);";
        statement.executeQuery(sqlQuery);
        sqlQuery = "INSERT INTO zahlungsarten VALUES (NULL, 'per EC-Karte', NULL);";
        statement.executeQuery(sqlQuery);
        sqlQuery = "INSERT INTO zahlungsarten VALUES (NULL, 'per Kreditkarte', NULL);";
        statement.executeQuery(sqlQuery);
        sqlQuery = "INSERT INTO zahlungsarten VALUES (NULL, 'per Lastschrift', NULL);";
        statement.executeQuery(sqlQuery);
        sqlQuery = "INSERT INTO zahlungsarten VALUES (NULL, 'per Dauerauftrag', NULL);";
        statement.executeQuery(sqlQuery);
        sqlQuery = "INSERT INTO zahlungsarten VALUES (NULL, 'per Überweisung', NULL);";        
        statement.executeQuery(sqlQuery);

        // Tabellenstruktur fuer Tabelle zahlungsfrequenzen

        sqlQuery = "DROP TABLE zahlungsfrequenzen IF EXISTS;";
        statement.executeQuery(sqlQuery);
        sqlQuery = "CREATE TABLE zahlungsfrequenzen ("
                 + "  id INTEGER NOT NULL IDENTITY,"
                 + "  name VARCHAR(20) NOT NULL,"
                 + "  beschreibung LONGVARCHAR,"
                 + "  PRIMARY KEY (id)"
                 + ");";
        statement.executeQuery(sqlQuery);

        // Daten fuer Tabelle zahlungsfrequenzen

        sqlQuery = "INSERT INTO zahlungsfrequenzen VALUES (NULL, 'jährlich', NULL);";
        statement.executeQuery(sqlQuery);
        sqlQuery = "INSERT INTO zahlungsfrequenzen VALUES (NULL, 'vierteljährlich', NULL);";        
        statement.executeQuery(sqlQuery);
        sqlQuery = "INSERT INTO zahlungsfrequenzen VALUES (NULL, 'monatlich', NULL);";        
        statement.executeQuery(sqlQuery);
        sqlQuery = "INSERT INTO zahlungsfrequenzen VALUES (NULL, 'wöchentlich', NULL);";        
        statement.executeQuery(sqlQuery);
        
    }

}
