package de.seipler.ausgaben.datenversorgung.xml;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import de.seipler.ausgaben.datenversorgung.DatenversorgungException;
import de.seipler.ausgaben.datenversorgung.IDatenversorgungKaeufer;
import de.seipler.ausgaben.entity.IKaeufer;
import de.seipler.ausgaben.entity.xml.XmlKaeufer;

/**
 * 
 * @author Georg Seipler
 */
public class DatenversorgungXmlKaeufer extends DatenversorgungXml implements IDatenversorgungKaeufer {
    
    protected DatenversorgungXmlKaeufer(IXmlConfiguration pConfiguration) {
        super(pConfiguration);
    }
    
    public IKaeufer liesKaeuferMitId(String pId) throws DatenversorgungException {
        return (XmlKaeufer) fCache.get(pId);
    }
    
    public void schreibeKaeufer(IKaeufer pKaeufer) throws DatenversorgungException {
        if (pKaeufer != null) {
            if ((pKaeufer.getId() == null) || (pKaeufer.getId().length() == 0)) {
                throw new DatenversorgungException("Invalid id.");
            }
            fCache.put((XmlKaeufer) pKaeufer);
        }
    }    
    
    public IKaeufer erzeugeKaeufer() {
        XmlKaeufer kaeufer = new XmlKaeufer();
        kaeufer.setDirty(true);
        return kaeufer;
    }
    
    public void init() throws DatenversorgungException {
        Reader in = null;        
        try {
            in = fConfiguration.reader(IXmlConfiguration.XML_KAEUFER);
            fCache = new XmlEntityCache();
            DatenversorgungXmlHelper.parseXml(in, new XmlHandlerKaeufer(fCache));
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
                out = fConfiguration.writer(IXmlConfiguration.XML_KAEUFER);
                DatenversorgungXmlHelper.writeCacheToXml(out, fCache, XmlKaeufer.TAG_KAEUFER_LISTE);
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
