package de.seipler.util.log;

/**
 * 
 * @author Georg Seipler
 */
public class LogLevel {

  private static LogLevel[] knownLevels = new LogLevel[7];

  public static final LogLevel OFF   = new LogLevel(0, "off",   "  OFF");
  public static final LogLevel FATAL = new LogLevel(1, "fatal", "FATAL");
  public static final LogLevel ERROR = new LogLevel(2, "error", "ERROR");
  public static final LogLevel WARN  = new LogLevel(3, "warn",  " WARN");
  public static final LogLevel INFO  = new LogLevel(4, "info",  " INFO");
  public static final LogLevel DEBUG = new LogLevel(5, "debug", "DEBUG");
  public static final LogLevel TRACE = new LogLevel(6, "trace", "TRACE");

  private int value;
  private String name;
  private String formattedName;
  
  /**
   * Default Constructor for LogLevel.
   */
  protected LogLevel(int value, String name, String formattedName) {
    this.value = value;
    this.name = name;
    this.formattedName = formattedName;
    knownLevels[value] = this;
  }
  
  /**
   * 
   */
  public int getValue() {
    return this.value;
  }
  
  /**
   * 
   */
  public String getName() {
    return this.name;
  }

  /**
   * 
   */
  public String getFormattedName() {
    return this.formattedName;
  }

  /**
   * 
   */
  public static final LogLevel forName(String name) {
    for (int i = 0; i <= knownLevels.length; i++) {
      if (knownLevels[i].getName().equalsIgnoreCase(name)) {
        return knownLevels[i];
      }
    }      
    return null;
  }

  /**
   * 
   */
  public static final LogLevel forValue(int value) {
    if ((value < 0) || (value >= knownLevels.length)) {
      return null;
    } else {
      return knownLevels[value];
    }    
  }

}
