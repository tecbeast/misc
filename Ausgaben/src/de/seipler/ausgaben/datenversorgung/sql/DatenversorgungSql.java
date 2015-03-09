package de.seipler.ausgaben.datenversorgung.sql;

import java.sql.Connection;
import java.sql.SQLException;

import de.seipler.ausgaben.datenversorgung.DatenversorgungException;
import de.seipler.ausgaben.datenversorgung.IDatenversorgung;

/**
 * 
 * @author Georg Seipler
 */
public abstract class DatenversorgungSql implements IDatenversorgung {
    
    private IDbConfiguration fDbConfiguration;    
    
    protected DatenversorgungSql(IDbConfiguration pDbConfiguration) {
        fDbConfiguration = pDbConfiguration;
    }
    
    protected IDbConfiguration getDbConfiguration() {
        return fDbConfiguration;
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
    
    public void init() throws DatenversorgungException {
    }

    public void release() throws DatenversorgungException {
    }
    
    public void sync() throws DatenversorgungException {
    }
    
}
