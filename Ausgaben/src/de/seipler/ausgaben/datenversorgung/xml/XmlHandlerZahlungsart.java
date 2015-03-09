package de.seipler.ausgaben.datenversorgung.xml;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import de.seipler.ausgaben.entity.xml.XmlZahlungsart;

/**
 * 
 * @author Georg Seipler
 */
public class XmlHandlerZahlungsart extends DefaultHandler {

    private StringBuffer fValue;
    private XmlZahlungsart fZahlungsart;
    private XmlEntityCache fCache;
    
    public XmlHandlerZahlungsart(XmlEntityCache pCache) {
        fValue = new StringBuffer();
        fCache = pCache;
    }

    /**
     * @see org.xml.sax.ContentHandler#characters(char, int, int)
     */
    public void characters(char[] ch, int start, int length) {
        fValue.append(new String(ch, start, length));
    }

    /**
     * @see org.xml.sax.ContentHandler#endElement(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    public void endElement(String uri, String localName, String qName) {
        if (XmlZahlungsart.TAG_ZAHLUNGSART.equals(qName)) {
            fCache.put(fZahlungsart);
        }
        if (XmlZahlungsart.TAG_NAME.equals(qName)) {
            fZahlungsart.setName(fValue.toString().trim());
        }
        if (XmlZahlungsart.TAG_BESCHREIBUNG.equals(qName)) {
            fZahlungsart.setBeschreibung(fValue.toString().trim());
        }
        fValue = new StringBuffer();
    }

    /**
     * @see org.xml.sax.ContentHandler#startElement(java.lang.String,
     *      java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    public void startElement(String uri, String localName, String qName, Attributes atts) {
        if (XmlZahlungsart.TAG_ZAHLUNGSART.equals(qName)) {
            fZahlungsart = new XmlZahlungsart();
            String id = atts.getValue(XmlZahlungsart.ATTRIBUTE_ID).trim();
            fZahlungsart.setId(id);
        }
    }
    
}
