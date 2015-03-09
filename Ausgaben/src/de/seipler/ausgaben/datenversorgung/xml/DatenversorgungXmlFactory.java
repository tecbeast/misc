package de.seipler.ausgaben.datenversorgung.xml;

import de.seipler.ausgaben.datenversorgung.DatenversorgungException;
import de.seipler.ausgaben.datenversorgung.IDatenversorgungAnbieter;
import de.seipler.ausgaben.datenversorgung.IDatenversorgungFactory;
import de.seipler.ausgaben.datenversorgung.IDatenversorgungFixeKosten;
import de.seipler.ausgaben.datenversorgung.IDatenversorgungKaeufer;
import de.seipler.ausgaben.datenversorgung.IDatenversorgungKategorie;
import de.seipler.ausgaben.datenversorgung.IDatenversorgungVariableKosten;
import de.seipler.ausgaben.datenversorgung.IDatenversorgungZahlungsart;
import de.seipler.ausgaben.datenversorgung.IDatenversorgungZahlungsfrequenz;

/**
 * 
 * @author Georg Seipler
 */
public class DatenversorgungXmlFactory implements IDatenversorgungFactory {
    
    private IDatenversorgungAnbieter fDatenversorgungAnbieter;
    private IDatenversorgungKaeufer fDatenversorgungKaeufer;
    private IDatenversorgungKategorie fDatenversorgungKategorie;
    private IDatenversorgungZahlungsart fDatenversorgungZahlungsart;
    private IDatenversorgungZahlungsfrequenz fDatenversorgungZahlungsfrequenz;
    
    public DatenversorgungXmlFactory(IXmlConfiguration pXmlConfiguration) {
        fDatenversorgungAnbieter = new DatenversorgungXmlAnbieter(pXmlConfiguration);
        fDatenversorgungKaeufer = new DatenversorgungXmlKaeufer(pXmlConfiguration);
        fDatenversorgungKategorie = new DatenversorgungXmlKategorie(pXmlConfiguration);
        fDatenversorgungZahlungsart = new DatenversorgungXmlZahlungsart(pXmlConfiguration);
        fDatenversorgungZahlungsfrequenz = new DatenversorgungXmlZahlungsfrequenz(pXmlConfiguration);
    }
    
    public IDatenversorgungAnbieter getDatenversorgungAnbieter() {
        return fDatenversorgungAnbieter;
    }

    public IDatenversorgungFixeKosten getDatenversorgungFixeKosten() {
        return null;
    }

    public IDatenversorgungKaeufer getDatenversorgungKaeufer() {
        return fDatenversorgungKaeufer;
    }

    public IDatenversorgungKategorie getDatenversorgungKategorie() {
        return fDatenversorgungKategorie;
    }

    public IDatenversorgungVariableKosten getDatenversorgungVariableKosten() {
        return null;
    }

    public IDatenversorgungZahlungsart getDatenversorgungZahlungsart() {
        return fDatenversorgungZahlungsart;
    }

    public IDatenversorgungZahlungsfrequenz getDatenversorgungZahlungsfrequenz() {
        return fDatenversorgungZahlungsfrequenz;
    }

    public void init() throws DatenversorgungException {
        fDatenversorgungAnbieter.init();
        fDatenversorgungKaeufer.init();
        fDatenversorgungKategorie.init();
        fDatenversorgungZahlungsart.init();
        fDatenversorgungZahlungsfrequenz.init();
    }
    
    public void sync() throws DatenversorgungException {
        fDatenversorgungAnbieter.sync();
        fDatenversorgungKaeufer.sync();
        fDatenversorgungKategorie.sync();
        fDatenversorgungZahlungsart.sync();
        fDatenversorgungZahlungsfrequenz.sync();
    }    

    public void release() throws DatenversorgungException {
        fDatenversorgungAnbieter.release();
        fDatenversorgungKaeufer.release();
        fDatenversorgungKategorie.release();
        fDatenversorgungZahlungsart.release();
        fDatenversorgungZahlungsfrequenz.release();
    }

}
