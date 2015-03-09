package de.seipler.ausgaben.entity.xml;

import java.util.Date;

import de.seipler.ausgaben.datenversorgung.xml.XmlFormatter;
import de.seipler.ausgaben.entity.IVariableKosten;


/**
 * 
 * @author Georg Seipler
 */
public class XmlVariableKosten extends XmlKosten implements IVariableKosten {

    public static final String TAG_VARIABLE_KOSTEN = "variableKosten";

    public static final String TAG_KAUFDATUM = "kaufdatum";

    private Date fKaufdatum;

    public XmlVariableKosten() {
        super();
    }

    public Date getKaufdatum() {
        return fKaufdatum;
    }

    public void setKaufdatum(Date pKaufdatum) {
        fKaufdatum = pKaufdatum;
    }
    
    public String toXml(int pIndentation) {
        StringBuffer buffer = new StringBuffer();
        XmlFormatter.addElementHeader(buffer, pIndentation, TAG_VARIABLE_KOSTEN, ATTRIBUTE_ID, getId());
        addElements(buffer, pIndentation + 1);
        XmlFormatter.addElement(buffer, pIndentation, TAG_KAUFDATUM, XmlFormatter.dateToString(getKaufdatum()));
        XmlFormatter.addElementFooter(buffer, pIndentation, TAG_VARIABLE_KOSTEN);
        return buffer.toString();
    }
    
}
