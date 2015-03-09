package de.seipler.versadoc.descriptor;

import de.seipler.util.exception.ChainedRuntimeException;

/**
 * 
 * @author Georg Seipler
 */
public class DescriptorFactoryException extends ChainedRuntimeException {

  /**
   * Constructor for FactoryException.
   * @param message
   */
  public DescriptorFactoryException(String message) {
    super(message);
  }

  /**
   * Constructor for FactoryException.
   * @param message
   * @param cause
   */
  public DescriptorFactoryException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructor for FactoryException.
   * @param cause
   */
  public DescriptorFactoryException(Throwable cause) {
    super(cause);
  }

}
