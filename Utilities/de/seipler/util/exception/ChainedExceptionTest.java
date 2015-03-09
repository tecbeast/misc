package de.seipler.util.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

/**
 * Test der Klasse <code>OskarRuntimeException</code>.
 *
 * @author Georg Seipler (GSE)
 */
public class ChainedExceptionTest extends TestCase {
  
  private static final boolean CONSOLE_OUTPUT = false;
  private static final String lineSeparator = System.getProperty("line.separator", "\n");
  
  /**
   * Standard Konstruktor.
   */
  public ChainedExceptionTest(String name) {
    super(name);
  }

  /**
   * Hilfsmethode zur Erzeugung einer <code>NullPointerException</code>.
   */
  private void provokeException() throws Exception {
    Object o = null;
    o.toString();
  }

  /**
   * Hilfsmethode zur Erzeugung einer ChainedException mit einer primitiven
   * Exception als Ursache.
   */
  private void rethrowExceptionOnce() throws TestChainedException {
    try {
      provokeException();
    } catch (Exception e) {
      throw new TestChainedException("mein Fehlertext innen", e);
    }
  }

  /**
   * Hilfsmethode zur Erzeugung einer ChainedException mit einer anderen
   * ChainedException als Ursache.
   */
  private void rethrowExceptionTwice() throws TestChainedException {
    try {
      rethrowExceptionOnce();
    } catch (Exception e) {
      throw new TestChainedException("mein Fehlertext aussen", e);
    }
  }

  /**
   * Zerlegt den uebergebenen String mit einem StackTrace in einzelne Zeilen.
   */
  private List parseStackTrace(String stackTrace) {

    int separatorLength = lineSeparator.length();

    List result = new ArrayList();

    int lastPos = 0;
    int length = stackTrace.length();
    while (lastPos < length) {
      int endOfLine = stackTrace.indexOf(lineSeparator, lastPos);
      if (endOfLine > 0) {
        result.add(stackTrace.substring(lastPos, endOfLine));
        lastPos = endOfLine + separatorLength;
      } else {
        result.add(stackTrace.substring(lastPos));
        lastPos = length;
      }
    }

    return result;

  }

  /**
   * Testvorbereitungen.
   *
   * Diese Methode wird vor jedem Aufruf einer der in dieser Klassen enthaltenen
   * Testmethoden durch JUnit einmal aufgerufen.
   */
  public void setUp() throws Exception {
    super.setUp();
  }

  /**
   * Aufraeumarbeiten.
   *
   * Diese Methode wird nach jedem Aufruf einer der in dieser Klassen enthaltenen
   * Testmethoden durch JUnit einmal aufgerufen.
   */
  public void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Testet das Erstellen eines vollstaendigen StackTraces einer ChainedException.
   *
   * <p>
   * <b>Vorzustand:</b>
   * <ul>
   *   <li>Fangen einer ChainedException mit einer anderen ChainedException als Ursache</li>
   * </ul>
   * <b>Methodenparameter:</b>
   * <ul>
   *   <li>keine</li>
   * </ul>
   * <b>Wirkungen:</b>
   * <ul>
   *   <li>Ausgabe eines vollstaendigen StackTraces - Validierung ueber Stringvergleich</li>
   * </ul>
   * </p>
   */
  public void testPrintStackTrace() {

    String testChainedException = TestChainedException.class.getName();
    String chainedExceptionTest = this.getClass().getName();

    List expectedOutput = new ArrayList();
    expectedOutput.add("    " + testChainedException + ": mein Fehlertext aussen");
    expectedOutput.add("  " + testChainedException + ": mein Fehlertext innen");
    expectedOutput.add("java.lang.NullPointerException");
    expectedOutput.add("at " + chainedExceptionTest + ".provokeException(");
    expectedOutput.add("  at " + chainedExceptionTest + ".rethrowExceptionOnce(");
    expectedOutput.add("    at " + chainedExceptionTest + ".rethrowExceptionTwice(");
    expectedOutput.add("    at " + chainedExceptionTest + ".testPrintStackTrace(");

    try {

      rethrowExceptionTwice();

    } catch (ChainedException ce) {

      if (CONSOLE_OUTPUT) { ce.printStackTrace(); }
      
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      ce.printStackTrace(pw);
      pw.close();

      List stackTrace = parseStackTrace(sw.getBuffer().toString());
      Iterator expectedIterator = expectedOutput.iterator();
      Iterator traceIterator = stackTrace.iterator();
      while (expectedIterator.hasNext()) {
        String expectedLine = (String) expectedIterator.next();
        String traceLine = (String) traceIterator.next();
        assertTrue(traceLine.startsWith(expectedLine));
      }
      
    }

  }
  
}
