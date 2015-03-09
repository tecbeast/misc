package de.seipler.ausgaben.datenversorgung.xml;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import de.seipler.ausgaben.entity.xml.XmlKategorie;

/**
 * 
 * @author Georg Seipler
 */
public class XmlHandlerKategorie extends DefaultHandler {

    private StringBuffer fValue;
    private XmlKategorie fKategorie;
    private XmlEntityCache fCache;
    
    public XmlHandlerKategorie(XmlEntityCache pCache) {
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
        if (XmlKategorie.TAG_KATEGORIE.equals(qName)) {
            fCache.put(fKategorie);
        }
        if (XmlKategorie.TAG_NAME.equals(qName)) {
            fKategorie.setName(fValue.toString().trim());
        }
        if (XmlKategorie.TAG_BESCHREIBUNG.equals(qName)) {
            fKategorie.setBeschreibung(fValue.toString().trim());
        }
        fValue = new StringBuffer();
    }

    /**
     * @see org.xml.sax.ContentHandler#startElement(java.lang.String,
     *      java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    public void startElement(String uri, String localName, String qName, Attributes atts) {
        if (XmlKategorie.TAG_KATEGORIE.equals(qName)) {
            fKategorie = new XmlKategorie();
            String id = atts.getValue(XmlKategorie.ATTRIBUTE_ID).trim();
            fKategorie.setId(id);
        }
    }
    
}
