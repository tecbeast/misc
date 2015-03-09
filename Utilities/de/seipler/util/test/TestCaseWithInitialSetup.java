package de.seipler.util.test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

/**
 * Enthaelt allgemeine Funktionalitaet aller TestCases
 *
 * @author Georg Seipler
 */
public abstract class TestCaseWithInitialSetup extends TestCase {

  protected static Map testSuitesPerClass = new HashMap();
  protected static Map maxNrOfTestCasesPerClass = new HashMap();

  /**
   * Diesen TestCase merken, damit bei der Testausfuehrung setUpOnce() und tearDownOnce() bestimmt werden koennen.
   */
  protected static void addTestCase(TestCase testCase) {
    Set testCasesPerSuite = (Set) testSuitesPerClass.get(testCase.getClass());
    if (testCasesPerSuite == null) {
      testCasesPerSuite = new HashSet();
      testSuitesPerClass.put(testCase.getClass(), testCasesPerSuite);
    }
    testCasesPerSuite.add(testCase);
    maxNrOfTestCasesPerClass.put(testCase.getClass(), new Integer(testCasesPerSuite.size()));
  }

  /**
   * Ueberschreibt die Testausfuehrung und ergaenzt sie um setUpOnce() und tearDownOnce().
   */
  public void runBare() throws Throwable {
    Set currentTestCasesInSuite = (Set) testSuitesPerClass.get(getClass());
    if (currentTestCasesInSuite.size() == ((Integer) maxNrOfTestCasesPerClass.get(getClass())).intValue()) {
      setUpOnce();
    }
    setUp();
    try {
      runTest();
    } finally {
      currentTestCasesInSuite.remove(this);
      tearDown();
      if (currentTestCasesInSuite.size() == 0) {
        tearDownOnce();
      }
    }
  }

  /**
   * Standard Konstruktor.
   */
  public TestCaseWithInitialSetup(String name) {
    super(name);
    addTestCase(this);
  }

  /**
   * Im Gegensatz zu @see TestCase#startUp() wird #startUpOnce() nur einmal 
   * vor allen Tests dieser Klasse ausgefuehrt; sollte dazu benutzt werden, 
   * den TestCase zu konfigurieren. 
   */
  protected void setUpOnce() throws Exception {
  }

  /**
   * Im Gegensatz zu @see TestCase#tearDown() wird #tearDownOnce() nur einmal 
   * am Ende aller Tests ausgefuehrt; sollte dazu benutzt werden, die gesetzten 
   * Ressourcen freizugeben. 
   */
  protected void tearDownOnce() throws Exception {
  }

}
