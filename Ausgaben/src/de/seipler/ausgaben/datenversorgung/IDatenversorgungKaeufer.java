package de.seipler.ausgaben.datenversorgung;

import de.seipler.ausgaben.entity.IKaeufer;

/**
 * 
 * @author Georg Seipler
 */
public interface IDatenversorgungKaeufer extends IDatenversorgung {
    
    public abstract IKaeufer liesKaeuferMitId(String pId) throws DatenversorgungException;
    
    public abstract void schreibeKaeufer(IKaeufer pKaeufer) throws DatenversorgungException;
    
    public abstract IKaeufer erzeugeKaeufer();

}
