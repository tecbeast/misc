package de.seipler.ausgaben.datenversorgung;



/**
 * 
 * @author Georg Seipler
 */
public interface IDatenversorgungFactory extends IDatenversorgung {
    
    IDatenversorgungAnbieter getDatenversorgungAnbieter();
    
    IDatenversorgungKaeufer getDatenversorgungKaeufer();
    
    IDatenversorgungKategorie getDatenversorgungKategorie();
    
    IDatenversorgungFixeKosten getDatenversorgungFixeKosten();
    
    IDatenversorgungVariableKosten getDatenversorgungVariableKosten();
    
    IDatenversorgungZahlungsart getDatenversorgungZahlungsart();
    
    IDatenversorgungZahlungsfrequenz getDatenversorgungZahlungsfrequenz();
    
}
