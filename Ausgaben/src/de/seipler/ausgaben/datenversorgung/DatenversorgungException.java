package de.seipler.ausgaben.datenversorgung;

/**
 * 
 * @author Georg Seipler
 */
public class DatenversorgungException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    public DatenversorgungException() {
        super();
    }
    
    public DatenversorgungException(String message) {
        super(message);
    }

    public DatenversorgungException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatenversorgungException(Throwable cause) {
        super(cause);
    }

}
