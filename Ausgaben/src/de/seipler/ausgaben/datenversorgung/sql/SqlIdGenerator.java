package de.seipler.ausgaben.datenversorgung.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.seipler.ausgaben.datenversorgung.DatenversorgungException;

/**
 * 
 * @author Georg Seipler
 */
public class SqlIdGenerator {
    
    private IDbConfiguration fDbConfiguration;    
    private String fQuery;
    private int fNaechsteId;
    
    public SqlIdGenerator(IDbConfiguration pDbConfiguration, String pQuery) {
        fDbConfiguration = pDbConfiguration;
        fQuery = pQuery;
    }

    public void init() throws DatenversorgungException {
        fNaechsteId = 0;
        try {
            Connection connection = leaseConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(fQuery);
            while (resultSet.next()) {
                fNaechsteId = resultSet.getInt(1);
            }
            returnLeasedConnection(connection);
        } catch (SQLException se) {
            throw new DatenversorgungException(se);
        }
        fNaechsteId++;
    }
    
    public int getId() {
        return fNaechsteId++;
    }
    
    protected Connection leaseConnection() throws SQLException {
        Connection connection = fDbConfiguration.getConnectionPool().getConnection();
        if (connection == null) {
            throw new SQLException("Unable to obtain a connection to the database.");
        }
        return connection;
    }
    
    protected void returnLeasedConnection(Connection pConnection) throws SQLException {
        pConnection.close();
    }
    
}
