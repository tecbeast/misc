package de.seipler.ausgaben.entity.xml;

import de.seipler.ausgaben.datenversorgung.xml.XmlFormatter;
import de.seipler.ausgaben.entity.IKategorie;


/**
 * 
 * @author Georg Seipler
 */
public class XmlKategorie extends XmlEntity implements IKategorie {

    public static final String TAG_KATEGORIE = "kategorie";
    public static final String TAG_KATEGORIE_LISTE = TAG_KATEGORIE + TAG_LISTE_SUFFIX;
    
    public static final String TAG_NAME = "name";
    public static final String TAG_BESCHREIBUNG = "beschreibung";

    private String fName;
    private String fBeschreibung;

    public XmlKategorie() {
        super();
    }

    public String getName() {
        return fName;
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
        XmlFormatter.addElementHeader(buffer, pIndentation, TAG_KATEGORIE, new String[] { ATTRIBUTE_ID }, new String[] { getId() });
        XmlFormatter.addElement(buffer, pIndentation + 1, TAG_NAME, getName());
        XmlFormatter.addElement(buffer, pIndentation + 1, TAG_BESCHREIBUNG, getBeschreibung());
        XmlFormatter.addElementFooter(buffer, pIndentation, TAG_KATEGORIE);
        return buffer.toString();
    }    

}
