package de.seipler.ausgaben.entity.xml;

import de.seipler.ausgaben.datenversorgung.xml.XmlFormatter;
import de.seipler.ausgaben.entity.IZahlungsart;


/**
 * 
 * @author Georg Seipler
 */
public class XmlZahlungsart extends XmlEntity implements IZahlungsart {

    public static final String TAG_ZAHLUNGSART = "zahlungsart";
    public static final String TAG_ZAHLUNGSART_LISTE = TAG_ZAHLUNGSART + TAG_LISTE_SUFFIX;
    
    public static final String TAG_NAME = "name";
    public static final String TAG_BESCHREIBUNG = "beschreibung";

    private String fName;
    private String fBeschreibung;

    public XmlZahlungsart() {
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

    public void setBeschreibung(String beschreibung) {
        fBeschreibung = beschreibung;
    }
    
    public String toXml(int pIndentation) {
        StringBuffer buffer = new StringBuffer();
        XmlFormatter.addElementHeader(buffer, pIndentation, TAG_ZAHLUNGSART, new String[] { ATTRIBUTE_ID }, new String[] { getId() });
        XmlFormatter.addElement(buffer, pIndentation + 1, TAG_NAME, getName());
        XmlFormatter.addElement(buffer, pIndentation + 1, TAG_BESCHREIBUNG, getBeschreibung());
        XmlFormatter.addElementFooter(buffer, pIndentation, TAG_ZAHLUNGSART);
        return buffer.toString();
    }

}
