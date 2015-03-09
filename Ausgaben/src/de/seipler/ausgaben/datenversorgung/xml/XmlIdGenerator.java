package de.seipler.ausgaben.datenversorgung.xml;

import java.util.Iterator;



/**
 * 
 * @author Georg Seipler
 */
public class XmlIdGenerator {
    
    private int fNaechsteId;
    
    public XmlIdGenerator() {
        super();
    }

    public void init(XmlEntityCache pEntityCache) {
        fNaechsteId = 0;
        if (pEntityCache != null) {
            Iterator idIterator = pEntityCache.idIterator();
            while (idIterator.hasNext()) {
                int id = ((Integer) idIterator.next()).intValue();
                if (id > fNaechsteId) {
                    fNaechsteId = id;
                }
            }
        }
        fNaechsteId++;
    }
    
    public int getId() {
        return fNaechsteId++;
    }
    
}
