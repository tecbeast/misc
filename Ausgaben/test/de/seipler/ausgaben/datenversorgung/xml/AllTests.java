package de.seipler.ausgaben.datenversorgung.xml;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

    public static Test suite() {
        TestSuite suite = new TestSuite("ausgaben.datenversorgung.sql");
        suite.addTestSuite(TestDatenversorgungXmlAnbieter.class);
        return suite;
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

}
