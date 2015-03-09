package de.seipler.util.exception;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Abstrakte Basisklasse fuer alle unchecked ChainedExceptions in oskar.
 * 
 * @author Georg Seipler
 */
public abstract class ChainedRuntimeException extends RuntimeException implements IndentedTrace {

  private Throwable cause = null;
  private List traceHeaders = null;
  private List traceLines = null;
  private List traceIndents = null;

  /**
   * Hidden anonymous constructor.
   */
  public ChainedRuntimeException() {
    super();
  }  

  /**
   * Standard Konstruktor.
   *
   * @param message Fehlertext der Exception
   */
  public ChainedRuntimeException(String message) {
    this(message, null);
  }

  /**
   * Standard Konstruktor.
   *
   * @param message Fehlertext der Exception
   * @param cause urspruenglicher Fehler (innere Exception)
   */
  public ChainedRuntimeException(String message, Throwable cause) {
    super(message);
    this.cause = cause;
  }

  /**
   * Standard Konstruktor.
   *
   * @param cause urspruenglicher Fehler (innere Exception)
   */
  public ChainedRuntimeException(Throwable cause) {
    super();
    this.cause = cause;
  }

  /**
   * Parst die Stacktraces der inneren und aeusseren Exception und teilt diese
   * in die Attribute <code>traceLines</code>, <code>traceHeaders</code> und
   * <code>traceIndents</code> auf.
   */
  private void buildTraceLinesAndHeaders() {

    this.traceHeaders = new ArrayList();
    this.traceLines = new ArrayList();
    this.traceIndents = new ArrayList();

    // im Falle einer mehrfach verketteten Exception, die ermittelten Attribute
    // der inneren Exception als Grundlage nehmen

    if (this.cause instanceof IndentedTrace) {

      IndentedTrace innerException = (IndentedTrace) getCause();
      this.traceLines.addAll(innerException.getTraceLines());
      this.traceIndents.addAll(innerException.getTraceIndents());
      this.traceHeaders.addAll(innerException.getTraceHeaders());

      // andernfalls (bei einer primitiven Exception) den inneren Stacktrace ermitteln

    } else if (this.cause != null) {

      StringWriter swInner = new StringWriter();
      PrintWriter pwInner = new PrintWriter(swInner);
      getCause().printStackTrace(pwInner);
      pwInner.close();

      String innerTrace = swInner.getBuffer().toString().trim();
      List innerTraceLinesBuffer = parseStackTrace(innerTrace);
      this.traceHeaders.add(innerTraceLinesBuffer.get(0));

      int innerSeparator = findThrowableInStackTrace(this.cause, innerTraceLinesBuffer) + 1;
      for (int i = innerSeparator; i < innerTraceLinesBuffer.size(); i++) {
        this.traceLines.add(innerTraceLinesBuffer.get(i));
      }

    }

    // in beiden Faellen den ausseren Stacktrace mit dem inneren vergleichen
    // und die Einrueckungen entsprechend ergaenzen

    StringWriter swOuter = new StringWriter();
    PrintWriter pwOuter = new PrintWriter(swOuter);
    super.printStackTrace(pwOuter);
    pwOuter.close();

    String outerTrace = swOuter.getBuffer().toString().trim();
    List outerTraceLinesBuffer = parseStackTrace(outerTrace);
    this.traceHeaders.add(0, outerTraceLinesBuffer.get(0));

    // wenn keine innere Exception besteht, muss auch keine Differenz ermittelt werden

    if (this.cause == null) {

      int outerSeparator = findThrowableInStackTrace(this, outerTraceLinesBuffer) + 1;
      for (int i = outerSeparator; i < outerTraceLinesBuffer.size(); i++) {
        this.traceLines.add(outerTraceLinesBuffer.get(i));
      }

    } else {

      int o = outerTraceLinesBuffer.size();
      int t = traceLines.size();
      while ((--o >= 0) && (--t >= 0)) {
        if (!outerTraceLinesBuffer.get(o).equals(traceLines.get(t))) {
          break;
        }
      }

      traceIndents.add(new Integer(t));

    }

  }

  /**
   * Findet die letzte Zeile des Exceptionbaums im Stacktrace. Mit der Folgezeile
   * beginnt der eigentliche Stacktrace. Die Suche erfolgt von unten nach oben,
   * auf diese Weise wird die Zeile mit dem Fehlertext (die auf jeden Fall den
   * Namen der Exyception enthaelt) zuletzt gefunden.
   *
   * @param throwable die zu suchende Exception
   * @param stackTraceLines Liste mit den einzelnen Zeilen des StackTraces
   */
  private int findThrowableInStackTrace(Throwable throwable, List stackTraceLines) {

    int nrOfLineWithThrowable = -1;

    int i = stackTraceLines.size();
    while ((--i >= 0) && (nrOfLineWithThrowable < 0)) {
      if (((String) stackTraceLines.get(i)).startsWith(throwable.getClass().getName())) {
        nrOfLineWithThrowable = i;
      }
    }

    return nrOfLineWithThrowable;

  }

  /**
   * Liefert die innere (weitergeworfene) Exception dieser Chain (Verkettung).
   */
  public Throwable getCause() {
    return this.cause;
  }

  /**
   * Liefert die Liste aller Exceptionnamen in dieser Chain (Verkettung).
   * Die Exception sind dabei in der Reihenfolge von aussen nach innen sortiert.
   */
  public List getTraceHeaders() {
    if (this.traceHeaders == null) { buildTraceLinesAndHeaders(); }
    return this.traceHeaders;
  }

  /**
   * Liefert eine Liste von Zeilennummern bezogen auf <code>getTraceLines</code>,
   * die wiedergeben bis zu welcher Zeile eine Exception (von innen nach aussen)
   * gueltig war.
   */
  public List getTraceIndents() {
    if (this.traceIndents == null) { buildTraceLinesAndHeaders(); }
    return this.traceIndents;
  }

  /**
   * Liefert eine Liste von allen Zeilen des vollstaendigen Stacktraces dieser
   * chained Exception (entspricht dem Stacktrace der innersten Exception).
   */
  public List getTraceLines() {
    if (this.traceLines == null) { buildTraceLinesAndHeaders(); }
    return this.traceLines;
  }

  /**
   * Gibt auf dem angegebenen Printwriter die angegebene Anzahl Einrueckungen aus.
   */
  private void indent(PrintWriter out, int steps) {
    for (int i = 0; i < steps; i++) {
      out.print("  ");
    }
  }

  /**
   * Zerlegt den uebergebenen String mit einem StackTrace in einzelne Zeilen.
   */
  private List parseStackTrace(String stackTrace) {

    String lineSeparator = System.getProperty("line.separator");
    int separatorLength = lineSeparator.length();

    List result = new ArrayList();

    int lastPos = 0;
    int length = stackTrace.length();
    while (lastPos < length) {
      int endOfLine = stackTrace.indexOf(lineSeparator, lastPos);
      if (endOfLine > 0) {
        result.add(stackTrace.substring(lastPos, endOfLine).trim());
        lastPos = endOfLine + separatorLength;
      } else {
        result.add(stackTrace.substring(lastPos).trim());
        lastPos = length;
      }
    }

    return result;

  }

  /**
   * Gibt einen vollstaendigen Stacktrace auf <code>System.err</code> aus.
   * Dabei werden zunaechst von aussen nach innen alle Fehlertexte asuagegeben,
   * gefolgt von einem vollstaendigen Stacktrace der innersten Exception.
   * Die Einrueckung von Fehlertexten und Stacktrace verdeutlicht dabei, innerhalb
   * welcher Methode die jeweilige Exception aufgetreten ist.
   */
  public void printStackTrace() {
    printStackTrace(System.err);
  }

  /**
   * Gibt einen vollstaendigen Stacktrace auf dem angegeben <code>PrintStream</code> aus.
   * Dabei werden zunaechst von aussen nach innen alle Fehlertexte asuagegeben,
   * gefolgt von einem vollstaendigen Stacktrace der innersten Exception.
   * Die Einrueckung von Fehlertexten und Stacktrace verdeutlicht dabei, innerhalb
   * welcher Methode die jeweilige Exception aufgetreten ist.
   */
  public void printStackTrace(PrintStream outStream) {
    printStackTrace(new PrintWriter(outStream, true));
  }

  /**
   * Gibt einen vollstaendigen Stacktrace auf dem angegeben <code>PrintWriter</code> aus.
   * Dabei werden zunaechst von aussen nach innen alle Fehlertexte asuagegeben,
   * gefolgt von einem vollstaendigen Stacktrace der innersten Exception.
   * Die Einrueckung von Fehlertexten und Stacktrace verdeutlicht dabei, innerhalb
   * welcher Methode die jeweilige Exception aufgetreten ist.
   */
  public void printStackTrace(PrintWriter out) {

    // alle Header ausgeben - mit abnehmender Einrueckung

    int headerSize = getTraceHeaders().size();
    for (int i = 0; i < headerSize; i++) {
      indent(out, headerSize - i - 1);
      out.println(getTraceHeaders().get(i));
    }

    // den StackTrace ausgeben - mit blockweise zunehmender Einrueckung

    int nextIndentLine = -1;
    int currentIndentStep = -1;
    int nrOfTraceLines = getTraceLines().size();
    Iterator indentIterator = getTraceIndents().iterator();
    for (int i = 0; i < nrOfTraceLines; i++) {
      if (i >= nextIndentLine) {
        currentIndentStep++;
        if (indentIterator.hasNext()) {
          nextIndentLine = ((Integer) indentIterator.next()).intValue();
        } else {
          nextIndentLine = nrOfTraceLines;
        }
      }
      indent(out, currentIndentStep);
      out.println(getTraceLines().get(i));
    }

  }
  
}
