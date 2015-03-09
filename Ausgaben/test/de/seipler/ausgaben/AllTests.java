package de.seipler.ausgaben;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

    public static Test suite() {
        TestSuite suite = new TestSuite("ausgaben");
        suite.addTest(de.seipler.ausgaben.datenversorgung.sql.AllTests.suite());
        return suite;
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

}
