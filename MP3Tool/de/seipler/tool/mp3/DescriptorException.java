package de.seipler.tool.mp3;

import de.seipler.util.exception.ChainedException;

/**
 * 
 * @author Georg Seipler
 */
public class DescriptorException extends ChainedException {

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
