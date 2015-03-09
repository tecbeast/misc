package de.seipler.ausgaben.datenversorgung.xml;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import de.seipler.ausgaben.datenversorgung.DatenversorgungException;
import de.seipler.ausgaben.datenversorgung.IDatenversorgungZahlungsart;
import de.seipler.ausgaben.entity.IZahlungsart;
import de.seipler.ausgaben.entity.xml.XmlZahlungsart;

/**
 * 
 * @author Georg Seipler
 */
public class DatenversorgungXmlZahlungsart implements IDatenversorgungZahlungsart {
    
    private XmlEntityCache fCache;
    private IXmlConfiguration fConfiguration;
    
    protected DatenversorgungXmlZahlungsart(IXmlConfiguration pConfiguration) {
        fConfiguration = pConfiguration;
    }

    public IZahlungsart liesZahlungsartMitId(String pId) throws DatenversorgungException {
        return (XmlZahlungsart) fCache.get(pId);
    }

    public void schreibeZahlungsart(IZahlungsart pZahlungsart) throws DatenversorgungException {
        if (pZahlungsart != null) {
            if ((pZahlungsart.getId() == null) || (pZahlungsart.getId().length() == 0)) {
                throw new DatenversorgungException("Invalid id.");
            }
            fCache.put((XmlZahlungsart) pZahlungsart);
        }
    }
    
    public IZahlungsart erzeugeZahlungsart() throws DatenversorgungException {
        XmlZahlungsart zahlungsart = new XmlZahlungsart();
        zahlungsart.setDirty(true);
        return zahlungsart;
    }
    
    public void init() throws DatenversorgungException {
        Reader in = null;        
        try {
            in = fConfiguration.reader(IXmlConfiguration.XML_ZAHLUNGSART);
            fCache = new XmlEntityCache();
            DatenversorgungXmlHelper.parseXml(in, new XmlHandlerZahlungsart(fCache));
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
                out = fConfiguration.writer(IXmlConfiguration.XML_ZAHLUNGSART);
                DatenversorgungXmlHelper.writeCacheToXml(out, fCache, XmlZahlungsart.TAG_ZAHLUNGSART_LISTE);
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
