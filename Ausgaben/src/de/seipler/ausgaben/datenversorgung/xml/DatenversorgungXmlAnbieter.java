package de.seipler.ausgaben.datenversorgung.xml;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import de.seipler.ausgaben.datenversorgung.DatenversorgungException;
import de.seipler.ausgaben.datenversorgung.IDatenversorgungAnbieter;
import de.seipler.ausgaben.entity.IAnbieter;
import de.seipler.ausgaben.entity.xml.XmlAnbieter;

/**
 * 
 * @author Georg Seipler
 */
public class DatenversorgungXmlAnbieter extends DatenversorgungXml implements IDatenversorgungAnbieter {
    
    protected DatenversorgungXmlAnbieter(IXmlConfiguration pConfiguration) {
        super(pConfiguration);
    }
    
    public IAnbieter liesAnbieterMitId(String pId) throws DatenversorgungException {
        return (XmlAnbieter) fCache.get(pId);
    }
    
    public void schreibeAnbieter(IAnbieter pAnbieter) throws DatenversorgungException {
        if (pAnbieter != null) {
            if ((pAnbieter.getId() == null) || (pAnbieter.getId().length() == 0)) {
                throw new DatenversorgungException("Invalid id.");
            }
            fCache.put((XmlAnbieter) pAnbieter);
        }
    }    
    
    public IAnbieter erzeugeAnbieter() {
        XmlAnbieter anbieter = new XmlAnbieter();
        anbieter.setDirty(true);
        return anbieter;
    }
    
    public void init() throws DatenversorgungException {
        Reader in = null;        
        try {
            in = fConfiguration.reader(IXmlConfiguration.XML_ANBIETER);
            fCache = new XmlEntityCache();
            DatenversorgungXmlHelper.parseXml(in, new XmlHandlerAnbieter(fCache));
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
    
    public void sync() throws DatenversorgungException {
        if (fCache.isDirty()) {
            Writer out = null;
            try {
                out = fConfiguration.writer(IXmlConfiguration.XML_ANBIETER);
                DatenversorgungXmlHelper.writeCacheToXml(out, fCache, XmlAnbieter.TAG_ANBIETER_LISTE);
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
