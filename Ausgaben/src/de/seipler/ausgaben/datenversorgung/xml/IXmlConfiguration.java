package de.seipler.ausgaben.datenversorgung.xml;

import java.io.Reader;
import java.io.Writer;

import de.seipler.ausgaben.datenversorgung.ConfigurationException;

public interface IXmlConfiguration {
    
    public static final String XML_ANBIETER = "/data/anbieter.xml";
    public static final String XML_KAEUFER = "/data/kaeufer.xml";
    public static final String XML_KATEGORIE = "/data/kategorie.xml"; 
    public static final String XML_ZAHLUNGSART = "/data/zahlungsart.xml"; 
    public static final String XML_ZAHLUNGSFREQUENZ = "/data/zahlungsfrequenz.xml";
    public static final String XML_VARIABLE_KOSTEN = "/data/variableKosten.xml";
    public static final String XML_FIXE_KOSTEN = "/data/fixeKosten.xml";
    
    public abstract Reader reader(String pConfigurationKey) throws ConfigurationException;
    public abstract Writer writer(String pConfigurationKey) throws ConfigurationException;

}