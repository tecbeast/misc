package de.seipler.ausgaben.datenversorgung.xml;

import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import de.seipler.ausgaben.datenversorgung.ConfigurationException;

/**
 * 
 * @author Georg Seipler
 */
public class XmlConfigurationTest implements IXmlConfiguration {

    private Map fReaderPerKey;
    private Map fWriterPerKey;
    
    public XmlConfigurationTest() {
        fReaderPerKey = new HashMap();
        fWriterPerKey = new HashMap();
    }
    
    public Reader reader(String pConfigurationKey) throws ConfigurationException {
        return (Reader) fReaderPerKey.get(pConfigurationKey);
    }
    
    public void setReaderForKey(String pConfigurationKey, Reader pReader) {
        fReaderPerKey.put(pConfigurationKey, pReader);
    }
    
    public Writer writer(String pConfigurationKey) throws ConfigurationException {
        return (Writer) fWriterPerKey.get(pConfigurationKey);
    }
    
    public void setWriterForKey(String pConfigurationKey, Writer pWriter) {
        fWriterPerKey.put(pConfigurationKey, pWriter);
    }
    
}
