package de.seipler.ausgaben.datenversorgung.sql;

import java.sql.SQLException;

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
public class DatenversorgungSqlFactory implements IDatenversorgungFactory {
    
    private IDbConfiguration fDbConfiguration;
    private IDatenversorgungAnbieter fDatenversorgungAnbieter;
    
    public DatenversorgungSqlFactory(IDbConfiguration pDbConfiguration) {
        fDbConfiguration = pDbConfiguration;
        fDatenversorgungAnbieter = new DatenversorgungSqlAnbieter(fDbConfiguration);
    }
    
    public IDatenversorgungAnbieter getDatenversorgungAnbieter() {
        return fDatenversorgungAnbieter;
    }

    public IDatenversorgungFixeKosten getDatenversorgungFixeKosten() {
        return null;
    }

    public IDatenversorgungKaeufer getDatenversorgungKaeufer() {
        return null;
    }

    public IDatenversorgungKategorie getDatenversorgungKategorie() {
        return null;
    }

    public IDatenversorgungVariableKosten getDatenversorgungVariableKosten() {
        return null;
    }

    public IDatenversorgungZahlungsart getDatenversorgungZahlungsart() {
        return null;
    }

    public IDatenversorgungZahlungsfrequenz getDatenversorgungZahlungsfrequenz() {
        return null;
    }

    public void init() throws DatenversorgungException {
        
        try {
            fDbConfiguration.init();
        } catch (SQLException se) {
            throw new DatenversorgungException(se);
        }
        
        fDatenversorgungAnbieter.init();
        
    }
    
    public void sync() throws DatenversorgungException {
    }
    
    public void release() throws DatenversorgungException {
        
        fDatenversorgungAnbieter.release();
        
        try {
            fDbConfiguration.release();
        } catch (SQLException se) {
            throw new DatenversorgungException(se);
        }
        
    }

}
