package de.seipler.util.sort.topological;

/**
 * Ausnahme fuer Fehler bei der topologischen Sortierung.
 * 
 * @author Georg Seipler
 */
public class TopologicalSortException extends RuntimeException {

  /**
   * Standard Konstruktor.
   */
  public TopologicalSortException() {
    super();
  }

  /**
   * Standard Konstruktor.
   * @param message Fehlertext dieser Ausnahme.
   */
  public TopologicalSortException(String message) {
    super(message);
  }

}
