package de.seipler.ausgaben.datenversorgung.xml;

import java.io.StringReader;
import java.io.StringWriter;
import java.sql.SQLException;

import junit.framework.TestCase;
import de.seipler.ausgaben.datenversorgung.IDatenversorgungAnbieter;
import de.seipler.ausgaben.entity.IAnbieter;

/**
 * 
 * @author Georg Seipler
 */
public class TestDatenversorgungXmlAnbieter extends TestCase {
    
    private static String LS = XmlFormatter.LINE_SEPARATOR;
    
    private static final String ANBIETER_TEST_INPUT_1 =
          "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + LS
        + "<anbieter-liste>" + LS
        + "  <anbieter id=\"1\">" + LS
        + "    <name>Anbieter1</name>" + LS
        + "    <beschreibung>Beschreibung1</beschreibung>" + LS
        + "  </anbieter>" + LS
        + "  <anbieter id=\"2\">" + LS
        + "    <name>Anbieter2</name>" + LS
        + "    <beschreibung>Beschreibung2</beschreibung>" + LS
        + "  </anbieter>" + LS
        + "</anbieter-liste>"
    ;
  
    private static final String ANBIETER_TEST_OUTPUT_1 =
          "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + LS
        + "<anbieter-liste>" + LS
        + "  <anbieter id=\"1\">" + LS
        + "    <name>Anbieter1</name>" + LS
        + "    <beschreibung>Beschreibung1</beschreibung>" + LS
        + "  </anbieter>" + LS
        + "  <anbieter id=\"2\">" + LS
        + "    <name>Anbieter2</name>" + LS
        + "    <beschreibung>Beschreibung2</beschreibung>" + LS
        + "  </anbieter>" + LS
        + "  <anbieter id=\"3\">" + LS
        + "    <name>Anbieter3</name>" + LS
        + "    <beschreibung />" + LS
        + "  </anbieter>" + LS
        + "</anbieter-liste>"
    ;
  
    public TestDatenversorgungXmlAnbieter(String pName) {
        super(pName);
    }
    
    public void testAnbieterLesenUndAnlegen() throws SQLException {
        
        XmlConfigurationTest configuration = new XmlConfigurationTest();
        StringReader in = new StringReader(ANBIETER_TEST_INPUT_1);
        configuration.setReaderForKey(IXmlConfiguration.XML_ANBIETER, in);
        StringWriter out = new StringWriter();
        configuration.setWriterForKey(IXmlConfiguration.XML_ANBIETER, out);
        
        IDatenversorgungAnbieter datenversorgung = new DatenversorgungXmlAnbieter(configuration);
        datenversorgung.init();
        
        IAnbieter anbieter1 = datenversorgung.liesAnbieterMitId("1");
        assertEquals("1", anbieter1.getId());
        IAnbieter anbieter2 = datenversorgung.liesAnbieterMitId("2");
        assertEquals("2", anbieter2.getId());
        
        IAnbieter anbieter3 = datenversorgung.erzeugeAnbieter();
        anbieter3.setId("3");
        anbieter3.setName("Anbieter3");
        datenversorgung.schreibeAnbieter(anbieter3);       
        
        datenversorgung.sync();
        
        String output = out.getBuffer().toString().trim();
        assertEquals(ANBIETER_TEST_OUTPUT_1, output);
        // System.out.print(output);
        
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestDatenversorgungXmlAnbieter.class);
    }    
    
}
