package de.seipler.ausgaben.datenversorgung;

import de.seipler.ausgaben.entity.IFixeKosten;

/**
 * 
 * @author Georg Seipler
 */
public interface IDatenversorgungFixeKosten extends IDatenversorgung {

    IFixeKosten liesFixeKostenMitId(String pId);
    
}
