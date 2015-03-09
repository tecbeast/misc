package de.seipler.ausgaben.datenversorgung;

import de.seipler.ausgaben.entity.IZahlungsfrequenz;

/**
 * 
 * @author Georg Seipler
 */
public interface IDatenversorgungZahlungsfrequenz extends IDatenversorgung {
    
    public abstract IZahlungsfrequenz liesZahlungsfrequenzMitId(String pId) throws DatenversorgungException;
    
    public abstract void schreibeZahlungsfrequenz(IZahlungsfrequenz pZahlungsfrequenz) throws DatenversorgungException;
    
    public abstract IZahlungsfrequenz erzeugeZahlungsfrequenz();

}
