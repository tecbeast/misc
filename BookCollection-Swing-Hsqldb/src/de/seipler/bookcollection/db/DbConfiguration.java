package de.seipler.bookcollection.db;

import java.util.Properties;

/**
 * 
 * @author Georg Seipler
 */
public interface DbConfiguration {
  
  public String getJdbcDriverClass();
  
  public String getUrl();
 
  public Properties getProperties();
 
}
