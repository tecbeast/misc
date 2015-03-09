package de.seipler.util.exception;

/**
 * Testweise Konkretisierung der abstrakten Basisklasse ohne Extras.
 *
 * @author Georg Seipler
 */
public class TestChainedException extends ChainedException {
  
  /**
   * Standard Konstruktor.
   *
   * @param message Fehlertext der Exception
   */
  public TestChainedException(String message) {
    super(message);
  }
  
  /**
   * Standard Konstruktor.
   *
   * @param message Fehlertext der Exception
   * @param cause urspruenglicher Fehler (innere Exception)
   */
  public TestChainedException(String message, Throwable cause) {
    super(message, cause);
  }
  
  /**
   * Standard Konstruktor.
   *
   * @param cause urspruenglicher Fehler (innere Exception)
   */
  public TestChainedException(Throwable cause) {
    super(cause);
  }
  
}
