package de.seipler.ausgaben.datenversorgung.xml;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import de.seipler.ausgaben.datenversorgung.DatenversorgungException;
import de.seipler.ausgaben.datenversorgung.IDatenversorgungKategorie;
import de.seipler.ausgaben.entity.IKategorie;
import de.seipler.ausgaben.entity.xml.XmlKategorie;

/**
 * 
 * @author Georg Seipler
 */
public class DatenversorgungXmlKategorie implements IDatenversorgungKategorie {
    
    private XmlEntityCache fCache;
    private IXmlConfiguration fConfiguration;
    
    protected DatenversorgungXmlKategorie(IXmlConfiguration pConfiguration) {
        fConfiguration = pConfiguration;
    }
    
    public IKategorie liesKategorieMitId(String pId) {
        return (XmlKategorie) fCache.get(pId);
    }
    
    public void schreibeKategorie(IKategorie pKategorie) throws DatenversorgungException {
        if (pKategorie != null) {
            if ((pKategorie.getId() == null) || (pKategorie.getId().length() == 0)) {
                throw new DatenversorgungException("Invalid id.");
            }
            fCache.put((XmlKategorie) pKategorie);
        }
    }    
    
    public IKategorie erzeugeKategorie() {
        XmlKategorie kategorie = new XmlKategorie();
        kategorie.setDirty(true);
        return kategorie;
    }
    
    public void init() throws DatenversorgungException {
        Reader in = null;        
        try {
            in = fConfiguration.reader(IXmlConfiguration.XML_KATEGORIE);
            fCache = new XmlEntityCache();
            DatenversorgungXmlHelper.parseXml(in, new XmlHandlerKategorie(fCache));
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
                out = fConfiguration.writer(IXmlConfiguration.XML_KATEGORIE);
                DatenversorgungXmlHelper.writeCacheToXml(out, fCache, XmlKategorie.TAG_KATEGORIE_LISTE);
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
