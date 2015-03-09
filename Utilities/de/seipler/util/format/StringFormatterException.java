package de.seipler.util.format;

/**
 * 
 * @author Georg Seipler
 */
public class StringFormatterException extends RuntimeException {

  private Throwable cause;

  /**
   * Constructor for FormatException.
   */
  public StringFormatterException(String message) {
    super(message);
  }

  /**
   * Constructor for FormatException.
   */
  public StringFormatterException(Throwable cause) {
    super();
    this.cause = cause;
  }

  /**
   * Constructor for FormatException.
   */
  public StringFormatterException(String message, Throwable cause) {
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
