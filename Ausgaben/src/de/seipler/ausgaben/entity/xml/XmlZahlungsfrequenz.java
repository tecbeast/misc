package de.seipler.ausgaben.entity.xml;

import de.seipler.ausgaben.datenversorgung.xml.XmlFormatter;
import de.seipler.ausgaben.entity.IZahlungsfrequenz;


/**
 * 
 * @author Georg Seipler
 */
public class XmlZahlungsfrequenz extends XmlEntity implements IZahlungsfrequenz {

    public static final String TAG_ZAHLUNGSFREQUENZ = "zahlungsfrequenz";
    public static final String TAG_ZAHLUNGSFREQUENZ_LISTE = TAG_ZAHLUNGSFREQUENZ + TAG_LISTE_SUFFIX;
    
    public static final String TAG_NAME = "name";
    public static final String TAG_BESCHREIBUNG = "beschreibung";

    private String fName;
    private String fBeschreibung;

    public XmlZahlungsfrequenz() {
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
        XmlFormatter.addElementHeader(buffer, pIndentation, TAG_ZAHLUNGSFREQUENZ, new String[] { ATTRIBUTE_ID }, new String[] { getId() });
        XmlFormatter.addElement(buffer, pIndentation + 1, TAG_NAME, getName());
        XmlFormatter.addElement(buffer, pIndentation + 1, TAG_BESCHREIBUNG, getBeschreibung());
        XmlFormatter.addElementFooter(buffer, pIndentation, TAG_ZAHLUNGSFREQUENZ);
        return buffer.toString();
    }    

}
