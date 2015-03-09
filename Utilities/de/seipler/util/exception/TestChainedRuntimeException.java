package de.seipler.util.exception;

/**
 * Testweise Konkretisierung der abstrakten Basisklasse ohne Extras.
 *
 * @author Georg Seipler 
 */
public class TestChainedRuntimeException extends ChainedRuntimeException {
  
  /**
   * Standard Konstruktor.
   *
   * @param message Fehlertext der Exception
   */
  public TestChainedRuntimeException(String message) {
    super(message);
  }
  
  /**
   * Standard Konstruktor.
   *
   * @param message Fehlertext der Exception
   * @param cause urspruenglicher Fehler (innere Exception)
   */
  public TestChainedRuntimeException(String message, Throwable cause) {
    super(message, cause);
  }
  
  /**
   * Standard Konstruktor.
   *
   * @param cause urspruenglicher Fehler (innere Exception)
   */
  public TestChainedRuntimeException(Throwable cause) {
    super(cause);
  }
  
}
