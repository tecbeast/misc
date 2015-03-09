package de.seipler.ausgaben.entity.xml;

import de.seipler.ausgaben.entity.IEntity;

/**
 * 
 * @author Georg Seipler
 */
public abstract class XmlEntity implements IEntity {
    
    public static final String ATTRIBUTE_ID = "id";

    protected static final String TAG_LISTE_SUFFIX = "-liste";
    
    private String fId;
    private boolean fDirty;

    protected XmlEntity() {
        super();
    }

    public String getId() {
        return fId;
    }

    public void setId(String pId) {
        fId = pId;
    }

    public void setDirty(boolean pDirty) {
        fDirty = pDirty; 
    }
    
    public boolean isDirty() {
        return fDirty;
    }
    
    public abstract String toXml(int indentation);

}
