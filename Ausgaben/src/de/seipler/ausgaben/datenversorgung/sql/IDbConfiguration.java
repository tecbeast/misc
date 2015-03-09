package de.seipler.ausgaben.datenversorgung.sql;

import java.sql.SQLException;
import snaq.db.ConnectionPool;

/**
 * 
 * @author Georg Seipler
 */
public interface IDbConfiguration {
    
    ConnectionPool getConnectionPool();
    
    void init() throws SQLException;
    
    void release() throws SQLException;

}
