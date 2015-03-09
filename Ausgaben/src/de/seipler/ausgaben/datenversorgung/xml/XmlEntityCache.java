package de.seipler.ausgaben.datenversorgung.xml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import de.seipler.ausgaben.entity.xml.XmlEntity;

/**
 * 
 * @author Georg Seipler
 */
public class XmlEntityCache {
    
    private Map fEntityById;

    public XmlEntityCache() {
        fEntityById = new HashMap();
    }

    public XmlEntity get(String pId) {
        return (XmlEntity) fEntityById.get(pId);
    }
    
    public XmlEntity put(XmlEntity pEntity) {
        return (XmlEntity) fEntityById.put(pEntity.getId(), pEntity);
    }
    
    public Iterator idIterator() {
        List idList = new ArrayList(fEntityById.keySet());
        Collections.sort(idList);
        return idList.iterator();
    }
    
    public boolean isDirty() {
        boolean dirty = false;
        Iterator idIterator = fEntityById.keySet().iterator();
        while (!dirty && idIterator.hasNext()) {
            XmlEntity entitaet = get((String) idIterator.next());
            dirty |= entitaet.isDirty();
        }
        return dirty;
    }

}
