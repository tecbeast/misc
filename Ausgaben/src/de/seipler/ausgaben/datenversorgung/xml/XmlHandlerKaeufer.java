package de.seipler.ausgaben.datenversorgung.xml;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import de.seipler.ausgaben.entity.xml.XmlKaeufer;

/**
 * 
 * @author Georg Seipler
 */
public class XmlHandlerKaeufer extends DefaultHandler {

    private StringBuffer fValue;
    private XmlKaeufer fKaeufer;
    private XmlEntityCache fCache;
    
    public XmlHandlerKaeufer(XmlEntityCache pCache) {
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
        if (XmlKaeufer.TAG_KAEUFER.equals(qName)) {
            fCache.put(fKaeufer);
        }
        if (XmlKaeufer.TAG_NAME.equals(qName)) {
            fKaeufer.setName(fValue.toString().trim());
        }
        if (XmlKaeufer.TAG_BESCHREIBUNG.equals(qName)) {
            fKaeufer.setBeschreibung(fValue.toString().trim());
        }
        fValue = new StringBuffer();
    }

    /**
     * @see org.xml.sax.ContentHandler#startElement(java.lang.String,
     *      java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    public void startElement(String uri, String localName, String qName, Attributes atts) {
        if (XmlKaeufer.TAG_KAEUFER.equals(qName)) {
            fKaeufer = new XmlKaeufer();
            String id = atts.getValue(XmlKaeufer.ATTRIBUTE_ID).trim();
            fKaeufer.setId(id);
        }
    }
    
}
