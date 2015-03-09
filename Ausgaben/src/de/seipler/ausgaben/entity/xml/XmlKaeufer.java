package de.seipler.ausgaben.entity.xml;

import de.seipler.ausgaben.datenversorgung.xml.XmlFormatter;
import de.seipler.ausgaben.entity.IKaeufer;


/**
 * 
 * @author Georg Seipler
 */
public class XmlKaeufer extends XmlEntity implements IKaeufer {

    public static final String TAG_KAEUFER = "kaeufer";
    public static final String TAG_KAEUFER_LISTE = TAG_KAEUFER + TAG_LISTE_SUFFIX;
    
    public static final String TAG_NAME = "name";
    public static final String TAG_BESCHREIBUNG = "beschreibung";

    private String fName;
    private String fBeschreibung;

    public XmlKaeufer() {
        super();
    }

    public String getName() {
        return this.fName;
    }

    public void setName(String pName) {
        fName = pName;
    }

    public String getBeschreibung() {
        return fBeschreibung;
    }

    public void setBeschreibung(String pBeschreibung) {
        fBeschreibung = pBeschreibung;
    }
    
    public String toXml(int pIndentation) {
        StringBuffer buffer = new StringBuffer();
        XmlFormatter.addElementHeader(buffer, pIndentation, TAG_KAEUFER, new String[] { ATTRIBUTE_ID }, new String[] { getId() });
        XmlFormatter.addElement(buffer, pIndentation + 1, TAG_NAME, getName());
        XmlFormatter.addElement(buffer, pIndentation + 1, TAG_BESCHREIBUNG, getBeschreibung());
        XmlFormatter.addElementFooter(buffer, pIndentation, TAG_KAEUFER);
        return buffer.toString();
    }    

}
