package de.seipler.ausgaben.datenversorgung.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import snaq.db.ConnectionPool;

/**
 * 
 * @author Georg Seipler
 */
public class DbConfigurationHsqldb implements IDbConfiguration {
    
    private ConnectionPool fConnectionPool;
    
    public DbConfigurationHsqldb() {
        fConnectionPool = new ConnectionPool(
            "local",
            10,
            30,
            180000,
            getUrl(),
            getUserName(),
            getPassword()
        );
    }
    
    protected String getDriver() {
        return "org.hsqldb.jdbcDriver";        
    }
    
    protected String getUrl() {
        return "jdbc:hsqldb:file:/temp/ausgaben";
    }
    
    protected String getUserName() {
        return "sa";
    }
    
    protected String getPassword() {
        return "";
    }
    
    public ConnectionPool getConnectionPool() {
        return fConnectionPool;
    }
    
    public void init() throws SQLException {
        
        // Treiber laden
        try {
            Class.forName(getDriver());
        } catch (ClassNotFoundException cnfe) {
            throw new SQLException("JDBCDriver Class not found");
        }

        Connection connection = getConnectionPool().getConnection();
        DbInitializer.init(connection);
        connection.close();
        
    }
    
    public void release() throws SQLException {
        
        ConnectionPool connectionPool = getConnectionPool();

        Connection connection = connectionPool.getConnection();
        Statement statement = connection.createStatement();
        statement.execute("SHUTDOWN");
        connection.close();
        
        connectionPool.release();
        
    }

}
