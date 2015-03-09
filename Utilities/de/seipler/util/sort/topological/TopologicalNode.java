package de.seipler.util.sort.topological;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Ein topologisch sortierbarer Knoten. Auf den Knoten wird
 * im Rahmen der Sortierung ueber den enthaltenen Schluessels
 * zugegriffen. Zusaetzlich kann ein beliebiger Wert als
 * &quot;Nutzlast&quot; mitsortiert werden.
 * 
 * @author Georg Seipler
 */
public class TopologicalNode {

	/** Schluessel fuer den Zugriff */
  private Object key;
  /** &quot;Nutzlast&quot; des Knotens */
  private Object value;
  /** Anzahl der Vorgaengerknoten (eingehende Kanten) */
  private int numberOfPredecessors;
  /** Menge der Nachfolgerknoten (ausgehende Kanten) */
  private List listOfSuccessors;
  /** Ebene des Knotens */
  private int level;

  /**
   * Standard Konstruktor ohne Wert.
	 * @param schluessel Schluessel fuer den Zugriff auf den knoten.
   */
  public TopologicalNode(Object key) {
    this(key, null);
  }

  /**
   * Standard Konstruktor.
   * @param schluessel Schluessel fuer den Zugriff auf den knoten.
   * @param wert &quot;Nutzlast&quot; des Knotens.
   */
  public TopologicalNode(Object key, Object value) {
    if (key == null) {
      throw new IllegalArgumentException("Parameter key must not be null.");
    } else {
      this.key = key;
      setValue(value);
      setNumberOfPredecessors(0);
      this.listOfSuccessors = new ArrayList();
    }
    setLevel(-1);
  }

  /**
   * Fuegt einen Nachfolgerknoten zu diesem Knoten hinzu (bildet eine Kante).
   */
  public void addSuccessor(TopologicalNode successor) {
    this.listOfSuccessors.add(successor);
  }

  /**
   * Liefert einen Iterator ueber die Nachfolgerknoten.
   */
  public Iterator iteratorOverSuccessors() {
    return this.listOfSuccessors.iterator();
  }

  /**
   * Liefert die Anzahl der Vorgaenger dieses Knotens (eingehende Kanten).
   */
  public int getNumberOfPredecessors() {
    return this.numberOfPredecessors;
  }

  /**
   * Liefert die Anzahl der Nachfolger dieses Knotens (ausgehende Kanten).
   */
  public int getNumberOfSuccessors() {
    return this.listOfSuccessors.size();
  }

  /**
   * Setzt die Anzahl der Vorgaenger dieses Knotens.
   */
  public void setNumberOfPredecessors(int numberOfPredecessors) {
    this.numberOfPredecessors = numberOfPredecessors;
  }

  /**
   * Liefert den Schluessel dieses Knotens. 
   */
  public Object getKey() {
    return this.key;
  }
  
  /**
   * Liefert den Wert dieses Knotens. 
   */
  public Object getValue() {
    return this.value;
  }
  
  /**
   * Setzt den Wert dieses Knotens. 
   */
  public void setValue(Object value) {
    this.value = value;
  }

  /**
   * Der Hashcode des Knotens entspricht dem Hashcode des Schluessels.
   */
  public int hashCode() {
    return this.key.hashCode();
  }

  /**
   * Liefert die topologische Ebene dieses Knotens (-1 wenn unsortiert).
   */
  public int getLevel() {
    return this.level;
  }

  /**
   * Setzt die topologische Ebene dieses Knotens.
   */
  public void setLevel(int level) {
    this.level = level;
  }

}
