package de.seipler.util;

/**
 * 
 * @author Georg Seipler
 */
public class ResourceManagerException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ResourceManagerException() {
        super();
    }

    public ResourceManagerException(String pMessage, Throwable pCause) {
        super(pMessage, pCause);
    }

    public ResourceManagerException(String pMessage) {
        super(pMessage);
    }

    public ResourceManagerException(Throwable pCause) {
        super(pCause);
    }

}
