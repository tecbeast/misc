package de.seipler.ausgaben.datenversorgung.xml;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import de.seipler.ausgaben.entity.xml.XmlAnbieter;

/**
 * 
 * @author Georg Seipler
 */
public class XmlHandlerAnbieter extends DefaultHandler {

    private StringBuffer fValue;
    private XmlAnbieter fAnbieter;
    private XmlEntityCache fCache;
    
    public XmlHandlerAnbieter(XmlEntityCache pCache) {
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
        if (XmlAnbieter.TAG_ANBIETER.equals(qName)) {
            fCache.put(fAnbieter);
        }
        if (XmlAnbieter.TAG_NAME.equals(qName)) {
            fAnbieter.setName(fValue.toString().trim());
        }
        if (XmlAnbieter.TAG_BESCHREIBUNG.equals(qName)) {
            fAnbieter.setBeschreibung(fValue.toString().trim());
        }
        fValue = new StringBuffer();
    }

    /**
     * @see org.xml.sax.ContentHandler#startElement(java.lang.String,
     *      java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    public void startElement(String uri, String localName, String qName, Attributes atts) {
        if (XmlAnbieter.TAG_ANBIETER.equals(qName)) {
            fAnbieter = new XmlAnbieter();
            String id = atts.getValue(XmlAnbieter.ATTRIBUTE_ID).trim();
            fAnbieter.setId(id);
        }
    }
    
}
