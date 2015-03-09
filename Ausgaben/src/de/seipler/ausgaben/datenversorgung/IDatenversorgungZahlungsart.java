package de.seipler.ausgaben.datenversorgung;

import de.seipler.ausgaben.entity.IZahlungsart;

/**
 * 
 * @author Georg Seipler
 */
public interface IDatenversorgungZahlungsart extends IDatenversorgung {
    
    public abstract IZahlungsart liesZahlungsartMitId(String pId) throws DatenversorgungException;
    
    public abstract void schreibeZahlungsart(IZahlungsart pZahlungsart) throws DatenversorgungException;
    
    public abstract IZahlungsart erzeugeZahlungsart();

}
