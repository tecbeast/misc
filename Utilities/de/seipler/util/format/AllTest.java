package de.seipler.util.format;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * 
 * @author Georg Seipler
 */
public class AllTest {

  /**
   *
   */
  public static Test suite() { 

    TestSuite suite = new TestSuite("All Formatting Tests");

    suite.addTestSuite(StringFormatterTest.class);
    
    return suite;
    
  }

  /**
   * 
   */
  public static void main(String args[]) {
    junit.textui.TestRunner.run(suite());
  }

}
