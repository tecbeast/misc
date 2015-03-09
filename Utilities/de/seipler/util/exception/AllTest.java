package de.seipler.util.exception;

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

    TestSuite suite = new TestSuite("All Exception Tests");

    suite.addTestSuite(ChainedExceptionTest.class);
    suite.addTestSuite(ChainedRuntimeExceptionTest.class);
    
    return suite;
    
  }

  /**
   * 
   */
  public static void main(String args[]) {
    junit.textui.TestRunner.run(suite());
  }

}
