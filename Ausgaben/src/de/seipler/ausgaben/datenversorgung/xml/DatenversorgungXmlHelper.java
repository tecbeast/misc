package de.seipler.ausgaben.datenversorgung.xml;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import de.seipler.ausgaben.entity.xml.XmlEntity;

public final class DatenversorgungXmlHelper {
    
    public static void parseXml(Reader pIn, ContentHandler pContentHandler) throws IOException, SAXException, ParserConfigurationException {
        SAXParserFactory xmlParserFactory = SAXParserFactory.newInstance();
        xmlParserFactory.setNamespaceAware(false);
        xmlParserFactory.setValidating(false);
        XMLReader xmlReader = xmlParserFactory.newSAXParser().getXMLReader();
        xmlReader.setContentHandler(pContentHandler);
        InputSource inputSource = new InputSource(pIn);
        xmlReader.parse(inputSource);
    }
    
    public static void writeCacheToXml(Writer pOut, XmlEntityCache pCache, String pListTag) throws IOException {
        StringBuffer buffer = new StringBuffer();
        XmlFormatter.addXmlHeader(buffer);
        XmlFormatter.addElementHeader(buffer, 0, pListTag);
        Iterator idIterator = pCache.idIterator();
        while (idIterator.hasNext()) {
            String id = (String) idIterator.next();
            XmlEntity entity = (XmlEntity) pCache.get(id);
            buffer.append(entity.toXml(1));
            entity.setDirty(false);
        }
        XmlFormatter.addElementFooter(buffer, 0, pListTag);
        pOut.write(buffer.toString());
    }
    
}
