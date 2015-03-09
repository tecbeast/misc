package de.seipler.util.log;

/**
 * 
 * @author Georg Seipler
 */
public class LogConfigurationException extends RuntimeException {

  private Throwable cause;

  /**
   * Constructor for LogConfigurationException.
   */
  public LogConfigurationException() {
    super();
  }

  /**
   * Constructor for LogConfigurationException.
   * @param s
   */
  public LogConfigurationException(String message) {
    super(message);
  }

  /**
   * 
   */
  public LogConfigurationException(Throwable cause) {
    this.cause = cause;
  }

  /**
   * 
   */
  public LogConfigurationException(String message, Throwable cause) {
    super(message);
    this.cause = cause;
  }    

  /**
   * 
   */
  public Throwable getCause() {
    return this.cause;
  }

}
