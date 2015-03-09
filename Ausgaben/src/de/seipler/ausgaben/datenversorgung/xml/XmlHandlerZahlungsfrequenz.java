package de.seipler.ausgaben.datenversorgung.xml;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import de.seipler.ausgaben.entity.xml.XmlZahlungsfrequenz;

/**
 * 
 * @author Georg Seipler
 */
public class XmlHandlerZahlungsfrequenz extends DefaultHandler {

    private StringBuffer fValue;
    private XmlZahlungsfrequenz fZahlungsfrequenz;
    private XmlEntityCache fCache;
    
    public XmlHandlerZahlungsfrequenz(XmlEntityCache pCache) {
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
        if (XmlZahlungsfrequenz.TAG_ZAHLUNGSFREQUENZ.equals(qName)) {
            fCache.put(fZahlungsfrequenz);
        }
        if (XmlZahlungsfrequenz.TAG_NAME.equals(qName)) {
            fZahlungsfrequenz.setName(fValue.toString().trim());
        }
        if (XmlZahlungsfrequenz.TAG_BESCHREIBUNG.equals(qName)) {
            fZahlungsfrequenz.setBeschreibung(fValue.toString().trim());
        }
        fValue = new StringBuffer();
    }

    /**
     * @see org.xml.sax.ContentHandler#startElement(java.lang.String,
     *      java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    public void startElement(String uri, String localName, String qName, Attributes atts) {
        if (XmlZahlungsfrequenz.TAG_ZAHLUNGSFREQUENZ.equals(qName)) {
            fZahlungsfrequenz = new XmlZahlungsfrequenz();
            String id = atts.getValue(XmlZahlungsfrequenz.ATTRIBUTE_ID).trim();
            fZahlungsfrequenz.setId(id);
        }
    }
    
}
