package de.seipler.util.exception;

import java.io.PrintWriter;
import java.util.List;

/**
 * Schnittstelle fuer chained (verkettete) Exceptions in Oskar.
 * Eine chained Exception ist eine weitergeworfene Exception, die den
 * urspruenglichen Fehler als Attribut mit sich traegt. Auf diese Weise
 * ist auch nach mehrmaligem &quot;Neuverpacken&quot; eines Fehlers
 * der komplette Verlauf nachvollziehbar.
 *
 * @author Georg Seipler (GSE)
 */
public interface IndentedTrace {
  
  /**
   * Liefert die innere (weitergeworfene) Exception dieser Chain (Verkettung).
   */
  public Throwable getCause();
  
  /**
   * Liefert die Liste aller Exceptionnamen in dieser Chain (Verkettung).
   * Die Exception sind dabei in der Reihenfolge von aussen nach innen sortiert.
   */
  public List getTraceHeaders();
  
  /**
   * Liefert eine Liste von Zeilennummern bezogen auf <code>getTraceLines</code>,
   * die wiedergeben bis zu welcher Zeile eine Exception (von innen nach aussen)
   * gueltig war.
   */
  public List getTraceIndents();
  
  /**
   * Liefert eine Liste von allen Zeilen des vollstaendigen Stacktraces dieser
   * chained Exception (entspricht dem Stacktrace der innersten Exception).
   */
  public List getTraceLines();
  
  /**
   * Gibt einen vollstaendigen Stacktrace auf dem angegeben <code>PrintWriter</code> aus.
   * Dabei werden zunaechst von aussen nach innen alle Fehlertexte asuagegeben,
   * gefolgt von einem vollstaendigen Stacktrace der innersten Exception.
   * Die Einrueckung von Fehlertexten und Stacktrace verdeutlicht dabei, innerhalb
   * welcher Methode die jeweilige Exception aufgetreten ist.
   */
  public void printStackTrace(PrintWriter out);
  
}
