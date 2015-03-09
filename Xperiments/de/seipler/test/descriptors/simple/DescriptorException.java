package de.seipler.test.descriptors.simple;

import de.seipler.util.exception.ChainedRuntimeException;

/**
 * 
 * @author Georg Seipler
 */
public class DescriptorException extends ChainedRuntimeException {

  /**
   * Constructor for DescriptorException.
   * @param message
   */
  public DescriptorException(String message) {
    super(message);
  }

  /**
   * Constructor for DescriptorException.
   * @param message
   * @param cause
   */
  public DescriptorException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructor for DescriptorException.
   * @param cause
   */
  public DescriptorException(Throwable cause) {
    super(cause);
  }

}
