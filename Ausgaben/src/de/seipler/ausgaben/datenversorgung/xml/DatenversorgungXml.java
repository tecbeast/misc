package de.seipler.ausgaben.datenversorgung.xml;

import java.io.IOException;
import java.io.Reader;

import org.xml.sax.ContentHandler;

import de.seipler.ausgaben.datenversorgung.DatenversorgungException;
import de.seipler.ausgaben.datenversorgung.IDatenversorgung;

public abstract class DatenversorgungXml implements IDatenversorgung {

    protected XmlEntityCache fCache;
    protected IXmlConfiguration fConfiguration;
    
    protected DatenversorgungXml(IXmlConfiguration pConfiguration) {
        fConfiguration = pConfiguration;
    }
    
    public void init() throws DatenversorgungException {
    }
    
    public void sync() throws DatenversorgungException {
    }
    
    public void release() throws DatenversorgungException {
        sync();
        fCache = null;
    }
    
    protected void init(Reader pReader, ContentHandler pContentHandler) throws DatenversorgungException {
        try {
            fCache = new XmlEntityCache();
            DatenversorgungXmlHelper.parseXml(pReader, pContentHandler);
        } catch (Exception all) {
            throw new DatenversorgungException(all);
        } finally {
            if (pReader != null) {
                try {
                    pReader.close();
                } catch (IOException ioe) {
                    throw new DatenversorgungException(ioe);
                }
            }
        }
    }
    
}
