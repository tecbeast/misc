package de.seipler.ausgaben.datenversorgung.sql;

import java.sql.Connection;
import java.sql.SQLException;

import junit.framework.TestCase;
import de.seipler.ausgaben.datenversorgung.IDatenversorgungAnbieter;
import de.seipler.ausgaben.entity.xml.XmlAnbieter;

/**
 * 
 * @author Georg Seipler
 */
public class TestDatenversorgungSqlAnbieter extends TestCase {
    
    public TestDatenversorgungSqlAnbieter(String pName) {
        super(pName);
    }
    
    public void testAnbieterInsertAndSelect() throws SQLException {
        
        IDbConfiguration dbConfiguration = new DbConfigurationTest();
        dbConfiguration.init();
        
        IDatenversorgungAnbieter datenversorgung = new DatenversorgungSqlAnbieter(dbConfiguration);
        datenversorgung.init();
        
        XmlAnbieter anbieterPut1 = datenversorgung.erzeugeAnbieter();
        anbieterPut1.setName("Anbieter1");
        anbieterPut1.setBeschreibung("Beschreibung1");
        XmlAnbieter anbieterPut2 = datenversorgung.erzeugeAnbieter();
        anbieterPut2.setName("Anbieter2");
        anbieterPut2.setBeschreibung("Beschreibung2");
        datenversorgung.release();
        
        datenversorgung =  new DatenversorgungSqlAnbieter(dbConfiguration);
        datenversorgung.init();
        
        XmlAnbieter anbieterGet1 = datenversorgung.liesAnbieterMitId(anbieterPut1.getId());
        assertNotSame(anbieterPut1, anbieterGet1);
        assertEquals(anbieterPut1.getId(), anbieterGet1.getId());
        assertEquals(anbieterPut1.getName(), anbieterGet1.getName());
        assertEquals(anbieterPut1.getBeschreibung(), anbieterGet1.getBeschreibung());
        XmlAnbieter anbieterGet2 = datenversorgung.liesAnbieterMitId(anbieterPut2.getId());
        assertNotSame(anbieterPut2, anbieterGet2);
        assertEquals(anbieterPut2.getId(), anbieterGet2.getId());
        assertEquals(anbieterPut2.getName(), anbieterGet2.getName());
        assertEquals(anbieterPut2.getBeschreibung(), anbieterGet2.getBeschreibung());
        
        Connection connection = dbConfiguration.getConnectionPool().getConnection();
        DisplaySqlTableHelper.displayTable(connection, "SELECT * FROM anbieter");
        connection.close();
        
        dbConfiguration.release();
        
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestDatenversorgungSqlAnbieter.class);
    }    
    
}
