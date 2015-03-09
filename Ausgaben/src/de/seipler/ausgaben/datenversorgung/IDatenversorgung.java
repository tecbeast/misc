package de.seipler.ausgaben.datenversorgung;


/**
 * 
 * @author Georg Seipler
 */
public interface IDatenversorgung {
    
    void init() throws DatenversorgungException;
    
    void sync() throws DatenversorgungException;

    void release() throws DatenversorgungException;
    
}
