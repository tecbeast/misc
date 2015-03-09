package de.seipler.ausgaben.datenversorgung.xml;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import de.seipler.ausgaben.datenversorgung.DatenversorgungException;
import de.seipler.ausgaben.datenversorgung.IDatenversorgungZahlungsfrequenz;
import de.seipler.ausgaben.entity.IZahlungsfrequenz;
import de.seipler.ausgaben.entity.xml.XmlKaeufer;
import de.seipler.ausgaben.entity.xml.XmlZahlungsfrequenz;

/**
 * 
 * @author Georg Seipler
 */
public class DatenversorgungXmlZahlungsfrequenz implements IDatenversorgungZahlungsfrequenz {
    
    private XmlEntityCache fCache;
    private IXmlConfiguration fConfiguration;
    
    protected DatenversorgungXmlZahlungsfrequenz(IXmlConfiguration pConfiguration) {
        fConfiguration = pConfiguration;
    }

    public IZahlungsfrequenz liesZahlungsfrequenzMitId(String pId) throws DatenversorgungException {
        return (XmlZahlungsfrequenz) fCache.get(pId);
    }
    
    public void schreibeZahlungsfrequenz(IZahlungsfrequenz pZahlungsfrequenz) throws DatenversorgungException {
        if (pZahlungsfrequenz != null) {
            if ((pZahlungsfrequenz.getId() == null) || (pZahlungsfrequenz.getId().length() == 0)) {
                throw new DatenversorgungException("Invalid id.");
            }
            fCache.put((XmlKaeufer) pZahlungsfrequenz);
        }
    }

    public IZahlungsfrequenz erzeugeZahlungsfrequenz() throws DatenversorgungException {
        XmlZahlungsfrequenz zahlungsfrequenz = new XmlZahlungsfrequenz();
        zahlungsfrequenz.setDirty(true);
        return zahlungsfrequenz;
    }

    public void init() throws DatenversorgungException {
        Reader in = null;        
        try {
            in = fConfiguration.reader(IXmlConfiguration.XML_ZAHLUNGSFREQUENZ);
            fCache = new XmlEntityCache();
            DatenversorgungXmlHelper.parseXml(in, new XmlHandlerZahlungsfrequenz(fCache));
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
    
    public void release() throws DatenversorgungException {
        sync();
        fCache = null;
    }
    
    public void sync() throws DatenversorgungException {
        if (fCache.isDirty()) {
            Writer out = null;
            try {
                out = fConfiguration.writer(IXmlConfiguration.XML_ZAHLUNGSFREQUENZ);
                DatenversorgungXmlHelper.writeCacheToXml(out, fCache, XmlZahlungsfrequenz.TAG_ZAHLUNGSFREQUENZ_LISTE);
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
