package de.seipler.ausgaben;

import de.seipler.ausgaben.datenversorgung.IDatenversorgungFactory;
import de.seipler.ausgaben.datenversorgung.xml.DatenversorgungXmlFactory;
import de.seipler.ausgaben.datenversorgung.xml.XmlConfiguration;

/**
 * 
 * @author Georg Seipler
 */
public class Ausgaben {
    
    private IDatenversorgungFactory fDatenversorgungFactory;
    
    protected Ausgaben() {
        // fDatenversorgungFactory = new DatenversorgungSqlFactory(new DbConfigurationHsqldb());
        fDatenversorgungFactory = new DatenversorgungXmlFactory(new XmlConfiguration());
    }
    
    public void run() throws Exception {
        fDatenversorgungFactory.init();
    }
    
    public void stop() throws Exception {
        fDatenversorgungFactory.release();
    }
    
    public static void main(String[] args) {
        try {
            Ausgaben ausgaben = new Ausgaben();
            ausgaben.run();
            ausgaben.stop();
        } catch (Exception all) {
            all.printStackTrace();
        }
    }
    
}
