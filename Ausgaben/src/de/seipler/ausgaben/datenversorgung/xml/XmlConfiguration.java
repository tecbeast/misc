package de.seipler.ausgaben.datenversorgung.xml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import de.seipler.ausgaben.datenversorgung.ConfigurationException;
import de.seipler.util.ResourceManager;
import de.seipler.util.ResourceManagerException;

/**
 * 
 * @author Georg Seipler
 */
public class XmlConfiguration implements IXmlConfiguration {
    
    private static final Map _CONFIGURATION_KEY_TO_XML_FILENAME;
    
    static {
        _CONFIGURATION_KEY_TO_XML_FILENAME = new HashMap();
        _CONFIGURATION_KEY_TO_XML_FILENAME.put(XML_ANBIETER, "/data/anbieter.xml");
        _CONFIGURATION_KEY_TO_XML_FILENAME.put(XML_KAEUFER, "/data/kaeufer.xml");
        _CONFIGURATION_KEY_TO_XML_FILENAME.put(XML_KATEGORIE, "/data/kategorie.xml"); 
        _CONFIGURATION_KEY_TO_XML_FILENAME.put(XML_ZAHLUNGSART, "/data/zahlungsart.xml"); 
        _CONFIGURATION_KEY_TO_XML_FILENAME.put(XML_ZAHLUNGSFREQUENZ, "/data/zahlungsfrequenz.xml");
        _CONFIGURATION_KEY_TO_XML_FILENAME.put(XML_VARIABLE_KOSTEN, "/data/variableKosten.xml");
        _CONFIGURATION_KEY_TO_XML_FILENAME.put(XML_FIXE_KOSTEN, "/data/fixeKosten.xml");
    }
    
    private ResourceManager fResourceLoader;
    
    public XmlConfiguration() {
        fResourceLoader = new ResourceManager();
    }
    
    public Reader reader(String pConfigurationKey) throws ConfigurationException {
        String xmlFilename = (String) _CONFIGURATION_KEY_TO_XML_FILENAME.get(pConfigurationKey);
        if (xmlFilename == null) {
            throw new ConfigurationException("Invalid configuration key.");
        }
        try {
            return new BufferedReader(new InputStreamReader(fResourceLoader.getResourceAsInputStream(pConfigurationKey)));
        } catch (ResourceManagerException rme) {
            throw new ConfigurationException(rme);
        }
    }
    
    public Writer writer(String pConfigurationKey) throws ConfigurationException {
        String xmlFilename = (String) _CONFIGURATION_KEY_TO_XML_FILENAME.get(pConfigurationKey);
        if (xmlFilename == null) {
            throw new ConfigurationException("Invalid configuration key.");
        }
        return new BufferedWriter(new OutputStreamWriter(fResourceLoader.getResourceAsOutputStream(pConfigurationKey, true)));
    }
    
}
