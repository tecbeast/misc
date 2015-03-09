package de.seipler.ausgaben.entity.xml;

import java.util.Date;

import de.seipler.ausgaben.datenversorgung.xml.XmlFormatter;
import de.seipler.ausgaben.entity.IFixeKosten;
import de.seipler.ausgaben.entity.IZahlungsfrequenz;


/**
 * 
 * @author Georg Seipler
 */
public class XmlFixeKosten extends XmlKosten implements IFixeKosten {
    
    public static final String TAG_FIXE_KOSTEN = "fixeKosten";

    public static final String TAG_STARTDATUM = "startdatum";
    public static final String TAG_ENDEDATUM = "endedatum";
    public static final String TAG_ZAHLUNGSFREQUENZ = "zahlungsfrequenz";

    private Date fStartdatum;
    private Date fEndedatum;
    private IZahlungsfrequenz fZahlungsfrequenz;

    public XmlFixeKosten() {
        super();
    }

    public Date getStartdatum() {
        return fStartdatum;
    }

    public void setStartdatum(Date pStartdatum) {
        fStartdatum = pStartdatum;
    }

    public Date getEndedatum() {
        return fEndedatum;
    }

    public void setEndedatum(Date pEndedatum) {
        fEndedatum = pEndedatum;
    }

    public IZahlungsfrequenz getZahlungsfrequenz() {
        return fZahlungsfrequenz;
    }

    public void setZahlungsfrequenz(IZahlungsfrequenz pZahlungsfrequenz) {
        fZahlungsfrequenz = pZahlungsfrequenz;
    }
    
    public String toXml(int pIndentation) {
        StringBuffer buffer = new StringBuffer();
        XmlFormatter.addElementHeader(buffer, pIndentation, TAG_FIXE_KOSTEN, ATTRIBUTE_ID, getId());
        addElements(buffer, pIndentation + 1);
        XmlFormatter.addElement(buffer, pIndentation, TAG_STARTDATUM, XmlFormatter.dateToString(getStartdatum()));
        XmlFormatter.addElement(buffer, pIndentation, TAG_ENDEDATUM, XmlFormatter.dateToString(getEndedatum()));
        XmlFormatter.addElement(buffer, pIndentation, TAG_ZAHLUNGSFREQUENZ, getZahlungsfrequenz().getId());
        XmlFormatter.addElementFooter(buffer, pIndentation, TAG_FIXE_KOSTEN);
        return buffer.toString();
    }
    
}
