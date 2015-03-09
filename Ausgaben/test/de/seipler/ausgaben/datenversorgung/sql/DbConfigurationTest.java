package de.seipler.ausgaben.datenversorgung.sql;

import de.seipler.ausgaben.datenversorgung.sql.DbConfigurationHsqldb;


/**
 * 
 * @author Georg Seipler
 */
public class DbConfigurationTest extends DbConfigurationHsqldb {
    
    protected String getUrl() {
        return "jdbc:hsqldb:mem:ausgaben";
    }

}
