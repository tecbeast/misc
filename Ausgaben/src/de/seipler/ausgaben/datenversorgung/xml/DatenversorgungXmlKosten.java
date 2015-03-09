package de.seipler.ausgaben.datenversorgung.xml;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import de.seipler.ausgaben.datenversorgung.DatenversorgungException;
import de.seipler.ausgaben.datenversorgung.IDatenversorgungAnbieter;
import de.seipler.ausgaben.datenversorgung.IDatenversorgungKaeufer;
import de.seipler.ausgaben.datenversorgung.IDatenversorgungKategorie;
import de.seipler.ausgaben.datenversorgung.IDatenversorgungZahlungsart;
import de.seipler.ausgaben.datenversorgung.IDatenversorgungZahlungsfrequenz;
import de.seipler.ausgaben.entity.xml.XmlKosten;

/**
 * 
 * @author Georg Seipler
 */
public abstract class DatenversorgungXmlKosten {
    
    protected IXmlConfiguration fConfiguration;
    protected IDatenversorgungAnbieter fDatenversorgungAnbieter;
    protected IDatenversorgungKaeufer fDatenversorgungKaeufer;
    protected IDatenversorgungKategorie fDatenversorgungKategorie;
    protected IDatenversorgungZahlungsart fDatenversorgungZahlungsart;
    protected IDatenversorgungZahlungsfrequenz fDatenversorgungZahlungsfrequenz;
    
    private XmlEntityCache fCache;
    
    protected DatenversorgungXmlKosten(
        IXmlConfiguration pConfiguration,
        IDatenversorgungAnbieter pDatenversorgungAnbieter,
        IDatenversorgungKaeufer pDatenversorgungKaeufer,
        IDatenversorgungKategorie pDatenversorgungKategorie,
        IDatenversorgungZahlungsart pDatenversorgungZahlungsart,
        IDatenversorgungZahlungsfrequenz pDatenversorgungZahlungsfrequenz
    ) {
        fConfiguration = pConfiguration;
        fDatenversorgungAnbieter = pDatenversorgungAnbieter;
        fDatenversorgungKaeufer = pDatenversorgungKaeufer;
        fDatenversorgungKategorie = pDatenversorgungKategorie;
        fDatenversorgungZahlungsart = pDatenversorgungZahlungsart;
        fDatenversorgungZahlungsfrequenz = pDatenversorgungZahlungsfrequenz;
    }

    public void release() throws DatenversorgungException {
        sync();
        fCache = null;
    }
    
    public abstract void sync() throws DatenversorgungException;
    
    protected void init(Reader pReader) throws DatenversorgungException {
        Reader in = null;        
        try {
            fCache = new XmlEntityCache();
            XmlHandlerKosten xmlHandler = new XmlHandlerKosten(
                fCache,
                fDatenversorgungAnbieter,
                fDatenversorgungKaeufer,
                fDatenversorgungKategorie,
                fDatenversorgungZahlungsart,
                fDatenversorgungZahlungsfrequenz
            );
            DatenversorgungXmlHelper.parseXml(pReader, xmlHandler);
        } catch (Exception all) {
            throw new DatenversorgungException(all);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ioe) {
                    throw new DatenversorgungException(ioe);
                }
            }
        }
    }
    
    protected void sync(Writer pWriter) throws DatenversorgungException {
        if (fCache.isDirty()) {
            Writer out = null;
            try {
                DatenversorgungXmlHelper.writeCacheToXml(pWriter, fCache, XmlKosten.TAG_KOSTEN_LISTE);
            } catch (Exception all) {
                throw new DatenversorgungException(all);
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException ioe) {
                        throw new DatenversorgungException(ioe);
                    }
                }
            }
        }
    }
    
}
