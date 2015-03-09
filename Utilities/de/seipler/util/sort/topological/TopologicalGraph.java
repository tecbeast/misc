package de.seipler.util.sort.topological;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Ein Graph mit topologisch sortierbaren Knoten.
 * 
 * @author Georg Seipler
 */
public class TopologicalGraph {
  
  /** Anzahl der Kanten in diesem Graphen **/
  private int numberOfEdges;
  /** Abbildung der Schluessel auf die zugehoerigen Knoten **/
  private Map nodePerKey;

  /**
   * Standard Konstruktor.
   */
  public TopologicalGraph() {
    this.numberOfEdges = 0;
    this.nodePerKey = new HashMap();
  }

  /**
   * Hinzufuegen einer neuen Kante anhand der Schluessel der enthaltenen Knoten.
   * @see findeKnotenUeberSchluessel(Object)
   */
  public boolean addEdgeViaKey(Object predecessor, Object successor) {
    return addEdge(findNodeViaKey(predecessor), findNodeViaKey(successor));
  }

  /**
   * Hinzufuegen einer neuen Kante anhand der enthaltenen Knoten.
   * Die Kante wird nur hinzugefuegt, wenn zwischen den beiden angegebenen
   * Knote nicht bereits eine Kante besteht.
   */
  public boolean addEdge(TopologicalNode predecessor, TopologicalNode successor) {

    if (predecessor == null) {
      throw new IllegalArgumentException("Parameter predecessor must nor be null.");
    }
    if (successor == null) {
      throw new IllegalArgumentException("Parameter successor must not be null.");
    }

    boolean newEdge = false;
    if (this.nodePerKey.get(predecessor.getKey()) == null) {
      newEdge = true;
      this.nodePerKey.put(predecessor.getKey(), predecessor);
    }
    if (this.nodePerKey.get(successor.getKey()) == null) {
      newEdge = true;
      this.nodePerKey.put(successor.getKey(), successor);
    }

    if (!newEdge) {
      newEdge = true;
      Iterator successorIterator = predecessor.iteratorOverSuccessors();
      while (newEdge && successorIterator.hasNext()) {
        if (successor.equals(successorIterator.next())) { newEdge = false; }
      } 
    }   

    if (newEdge) {
      successor.setNumberOfPredecessors(successor.getNumberOfPredecessors() + 1);
      predecessor.addSuccessor(successor);
      this.numberOfEdges++;
    }

    return newEdge;
  }
  
  /**
   * Liefert die Anzahl der im Graph enthaltenen Kanten.
   */
  public int getNumberOfEdges() {
    return this.numberOfEdges;
  }

  /**
   * Liefert die Anzahl der im Graph enthaltenen Knoten.
   */
  public int getNumberOfNodes() {
    return this.nodePerKey.size();
  }
  
  /**
   * Liefert einen Iterator ueber die enthaltenen Knoten.
   */
  public Iterator iteratorOverNodes() {
    return this.nodePerKey.values().iterator();
  }

  /**
   * Liefert einen Knoten anhand des uebergebenen Schluessels.
   * Wenn noch kein Knoten mit diesem Schluessel definiert ist, wird ein neuer angelegt.
   */
  public TopologicalNode findNodeViaKey(Object key) {
    TopologicalNode node = (TopologicalNode) this.nodePerKey.get(key);
    if (node == null) { node = new TopologicalNode(key); }
    return node;
  }
  
}
