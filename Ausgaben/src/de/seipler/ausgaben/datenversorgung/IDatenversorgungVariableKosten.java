package de.seipler.ausgaben.datenversorgung;

import de.seipler.ausgaben.entity.IVariableKosten;

/**
 * 
 * @author Georg Seipler
 */
public interface IDatenversorgungVariableKosten extends IDatenversorgung {

    IVariableKosten liesVariableKostenMitId(String pId);

}
