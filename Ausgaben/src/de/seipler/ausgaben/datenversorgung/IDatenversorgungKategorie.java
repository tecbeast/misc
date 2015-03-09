package de.seipler.ausgaben.datenversorgung;

import de.seipler.ausgaben.entity.IKategorie;

/**
 * 
 * @author Georg Seipler
 */
public interface IDatenversorgungKategorie extends IDatenversorgung {
    
    public abstract IKategorie liesKategorieMitId(String pId) throws DatenversorgungException;
    
    public abstract void schreibeKategorie(IKategorie pKategorie) throws DatenversorgungException;
    
    public abstract IKategorie erzeugeKategorie();

}
