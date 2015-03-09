package de.seipler.versadoc.category;

import de.seipler.util.exception.ChainedRuntimeException;

/**
 * @author Georg Seipler
 */
public class CategoryRegistryException extends ChainedRuntimeException {

  /**
   * @param message
   */
  public CategoryRegistryException(String message) {
    super(message);
  }

  /**
   * @param message
   * @param cause
   */
  public CategoryRegistryException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * @param cause
   */
  public CategoryRegistryException(Throwable cause) {
    super(cause);
  }

}
