package de.seipler.util.log;

import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;

/**
 * Verzeichnis von und Utilityklasse fuer alle Protokolliererinstanzen.
 *
 * @author Georg Seipler
 */
public class LogFactory {

  private static LogFactory instance;

  private Map logPerClass;
  private Map logLevelPerPackage;
  private LogLevel defaultLogLevel;
  private LogWriter defaultLogWriter;

  /**
   * Standard Konstruktor.
   */
  private LogFactory() {
    this.logPerClass = new HashMap();
    this.logLevelPerPackage = new HashMap();
    DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS");
    PrintWriter writer = new PrintWriter(System.err, false);
    setDefaultLogWriter(new LogWriter(writer, dateFormat));
    setDefaultLogLevel(LogLevel.TRACE);
  }

  /**
   *
   */
  public LogLevel getDefaultLogLevel() {
    return this.defaultLogLevel;
  }
  
  /**
   * 
   */
  public LogWriter getDefaultLogWriter() {
    return this.defaultLogWriter;
  }

  /**
   * Singleton pattern.
   */
  public static LogFactory getInstance() {
    if (instance == null) { instance = new LogFactory(); }
    return instance;
  }

  /**
   * 
   */
  protected Log getLogForClass(Class clazz) {
    return (Log) logPerClass.get(clazz);
  }

  /**
   * 
   */
  protected LogLevel getLogLevelForPackage(Package pkg) {
    return (LogLevel) logLevelPerPackage.get(pkg);
  }

  /**
   * Liefert die Protokolliererinstanz fuer die uebergebene Kategorie (protokollierende Klasse).
   */
  public static Log getLog(Class clazz) {
  	Log log = getInstance().getLogForClass(clazz);
    if (log == null) {
      LogLevel level = getInstance().getLogLevelForPackage(clazz.getPackage());
      if (level == null) {
        level = getInstance().getDefaultLogLevel();
      }
      log = new Log(clazz, level, getInstance().getDefaultLogWriter());
      getInstance().setLogForClass(clazz, log);
    }
    return log;
  }

  /**
   * 
   */
  public void configure(String propertyFilename) throws IOException {
    propertyFilename = propertyFilename.replace(File.separatorChar, '/');
    if (!propertyFilename.startsWith("/")) {
      propertyFilename = "/" + propertyFilename;
    }
    URL propertyUrl = getInstance().getClass().getResource(propertyFilename);
    Properties properties = new Properties();
    InputStream in = null;
    try {
      in = propertyUrl.openStream();
      properties.load(in);
    } finally {
      in.close(); // always close your streams
    }
    getInstance().configure(properties);
  }

  /**
   *
   * @exception LogConfigurationException
   */
  public void configure(Properties properties) {
  	
  	Map newLogPerClass = new HashMap();
    
    Iterator propertyKeys = properties.keySet().iterator();
    while (propertyKeys.hasNext()) {
      String key = ((String) propertyKeys.next()).trim();
      if (key.startsWith("loglevel.")) {
        // ProtokolliererLevel aus Properties ermitteln
        LogLevel logLevel = null;
        try {
          logLevel = LogLevel.forName(properties.getProperty(key));
        } catch (Exception e) {
          throw new LogConfigurationException("loglevel " + key + " cannot be read.", e);
        }
        // Definition gilt fuer Klasse, Paket oder Default
        String clazzOrPackage = key.substring(9);
        if (clazzOrPackage.equals("default")) {
          setDefaultLogLevel(logLevel);
        } else {
          Class clazz = null;
          try {
            clazz = Class.forName(clazzOrPackage);
          } catch (ClassNotFoundException ignored) {
          }
          if (clazz != null) {
          	Log log = (Log) logPerClass.remove(clazz);
          	if (log == null) {
          		log = new Log(clazz, logLevel, defaultLogWriter);
          	} else {
          		log.setLevel(logLevel);
          	}
          	newLogPerClass.put(clazz, log);
          } else {
            Package pkg = Package.getPackage(clazzOrPackage);
            if (pkg != null) {
              logLevelPerPackage.put(pkg, logLevel);
            }
          }
        }
      }
    }
    
    // alle noch bestehenden Level aendern
    Iterator logClazzes = logPerClass.keySet().iterator();
    while (logClazzes.hasNext()) {
    	Class clazz = (Class) logClazzes.next();
    	Log log = (Log) logPerClass.remove(clazz);
    	LogLevel packageLevel = (LogLevel) logLevelPerPackage.get(clazz.getPackage());
    	if (packageLevel != null) {
    		log.setLevel(packageLevel);
    	} else {
    		log.setLevel(defaultLogLevel);
    	}
    	newLogPerClass.put(clazz, log);
    }
    
    // neue LogLevel wirksam machen
    logPerClass = newLogPerClass;
    
  }

  /**
   *
   */
  protected void setDefaultLogLevel(LogLevel defaultLogLevel) {
    this.defaultLogLevel = defaultLogLevel;
  }

  /**
   * 
   */
  protected void setDefaultLogWriter(LogWriter defaultLogWriter) {
    this.defaultLogWriter = defaultLogWriter;
  }

  /**
   * 
   */
  protected void setLogForClass(Class clazz, Log log) {
    logPerClass.put(clazz, log);
  }

}