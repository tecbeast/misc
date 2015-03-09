package de.seipler.ausgaben.datenversorgung;

import de.seipler.ausgaben.entity.IAnbieter;


/**
 * 
 * @author Georg Seipler
 */
public interface IDatenversorgungAnbieter extends IDatenversorgung {
    
    public abstract IAnbieter liesAnbieterMitId(String pId) throws DatenversorgungException;
    
    public abstract void schreibeAnbieter(IAnbieter pAnbieter) throws DatenversorgungException; 
    
    public abstract IAnbieter erzeugeAnbieter();

}
