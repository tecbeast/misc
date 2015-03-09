package de.seipler.test.descriptors.simple;

import de.seipler.util.exception.ChainedRuntimeException;

/**
 * 
 * @author Georg Seipler
 */
public class FactoryException extends ChainedRuntimeException {

  /**
   * Constructor for FactoryException.
   * @param message
   */
  public FactoryException(String message) {
    super(message);
  }

  /**
   * Constructor for FactoryException.
   * @param message
   * @param cause
   */
  public FactoryException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructor for FactoryException.
   * @param cause
   */
  public FactoryException(Throwable cause) {
    super(cause);
  }

}
