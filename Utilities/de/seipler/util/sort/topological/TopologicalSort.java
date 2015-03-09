package de.seipler.util.sort.topological;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Sortiert einen Graphen topologisch, d.h. nach den Ebenen der Knoten.
 * Die Ebene 0 ist dadurch definiert, dass es keine eingehenden Kanten
 * gibt. Die Nummerierung der folgenden Ebenen ergibt sich aus der
 * hoechsten Ebene von der es eingehende Kanten gibt.
 * 
 * @author Georg Seipler
 */
public class TopologicalSort {

  /**
   * Standard Konstruktor.
   */
  public TopologicalSort() {
    super();
  }
  
  /**
   * Liefert die in dem uebergebenen Graphen enthaltenen Knoten nach Ebenen
   * sortiert in einem Array zurueck.
   */
  public TopologicalNode[] sort(TopologicalGraph graph) {

    int numberOfNodes = graph.getNumberOfNodes();
    TopologicalNode[] result = new TopologicalNode[numberOfNodes];

    // Liste der Knoten mit Ebene 0 initialisieren (Knoten ohne eingehende Kanten)
    LinkedList queue = new LinkedList();
    Iterator nodeIterator = graph.iteratorOverNodes();
    while (nodeIterator.hasNext()) {
      TopologicalNode node = (TopologicalNode) nodeIterator.next();
      if (node.getNumberOfPredecessors() == 0) { queue.add(node); }
    }
        
    // die Elemente der Warteschlange bearbeiten
    int position = 0;
    int currentLevel = 0;
    int positionLevelChange = queue.size();
    while (queue.size() > 0) {
      
      // auf eventuellen Ebenenwechsel pruefen
      if (position >= positionLevelChange) {
        currentLevel++;
        positionLevelChange += queue.size();
      }
      
      // aktuellen Knoten holen und Ebene setzen
      TopologicalNode node = (TopologicalNode) queue.removeFirst();
      node.setLevel(currentLevel);
      result[position] = node;
      position++;

      // Nachfolger verarbeiten
      Iterator successorIterator = node.iteratorOverSuccessors();
      while (successorIterator.hasNext()) {
        TopologicalNode successor = (TopologicalNode) successorIterator.next();
        // Anzahl eingehender Kanten vermindern
        successor.setNumberOfPredecessors(successor.getNumberOfPredecessors() - 1);
        // Nachfolger zu den "fertigen" Knoten hinzufuegen, falls noetig
        if (successor.getNumberOfPredecessors() == 0) { queue.addLast(successor); }
      }
      
    }

    if (position < numberOfNodes) {
      throw new TopologicalSortException("Graph has cycles: "+ (numberOfNodes - position) + " unprocessed nodes.");
    }

    return result;
    
  }

  /**
   * Ausfuehrbarer Test dieser technischen Klasse.
   */
  public static void main(String[] args) {

    int max = 50000;

    System.out.println("Erzeuge Knoten ...");    

    TopologicalNode[] knoten = new TopologicalNode[max];
    for (int i = 0; i < max; i++) {
      knoten[i] = new TopologicalNode(new Integer(i));
    }
    
    TopologicalGraph graph = new TopologicalGraph();
    
    System.out.println("Erzeuge Graph ...");

    /*    
    // full graph
    for (int i = 0; i < max; i++) {
      for (int j = i + 1; j < max; j++) {
        graph.addKante(knoten[i], knoten[j]);
      }
    }
    */

    /*
    // reverse order
    for (int i=0; i<max-1;i++) {
      graph.addKante(knoten[i+1], knoten[i]);
    }
    */

    /*
    // (2i+1,2i) pairs
    for (int i = 0; i < max - 1; i += 2) {
      graph.addKante(knoten[i+1], knoten[i]);
    }
    */

    // some element last
    int last = max/5;
    System.out.println("last should be " + last);
    for (int i=0; i<max; i++) {
      if (i!=last) {
        graph.addEdge(knoten[i], knoten[last]);
      }
    }

    /*
    // circle (throws exception)
    int last = max/5;
    System.out.println("last should be " + last);
    for (int i=0; i<max; i++) {
      graph.addKante(knoten[i], knoten[last]);
    }
    */
        
    System.out.println("Sort ...");
    
    TopologicalSort topologicalSort = new TopologicalSort();
    
    long start = System.currentTimeMillis();
    
    TopologicalNode[] result = topologicalSort.sort(graph);
    
    long ende = System.currentTimeMillis();
    long benoetigteZeit = ende - start;
    
    System.out.println("Time for " + graph.getNumberOfNodes() + " nodes and " + graph.getNumberOfEdges() + " edges: " + benoetigteZeit + " Millisekunden.");

    for (int i = 0; i < result.length; i++) {
      TopologicalNode sorted = (TopologicalNode) result[i];
      System.out.println("key " + sorted.getKey() + " is level " + sorted.getLevel());
    }
    
  }
  

}
