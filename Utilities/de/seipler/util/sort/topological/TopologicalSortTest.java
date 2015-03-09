package de.seipler.util.sort.topological;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

/**
 * 
 * @author Georg Seipler
 */
public class TopologicalSortTest extends TestCase {
  
  private Map nodeCache;
  private TopologicalNode node1;
  private TopologicalNode node2;
  private TopologicalNode node3;
  private TopologicalNode node4;

  /**
   * Constructor for TopologicalSortTest.
   * @param arg0
   */
  public TopologicalSortTest(String arg0) {
    super(arg0);
  }

  /**
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
    nodeCache = new HashMap();
    node1 = createNode("1");
    node2 = createNode("2");
    node3 = createNode("3");
    node4 = createNode("4");
  }

  /**
   * Sortierung mit einen "leeren" Graph.
   * 
   * @wirkung keine Knoten
   */
  public void testSortiere01() {
    TopologicalGraph graph = new TopologicalGraph();
    TopologicalSort topologicalSort = new TopologicalSort();

    // Test
    TopologicalNode[] nodes = topologicalSort.sort(graph);
    assertEquals("no nodes", 0, nodes.length);
  }

  /**
   * Sortierung mit einer Kante im Graph:
   * k0
   * 
   * @wirkung zwei sortierte Knoten
   */
  public void testSortiere02() {
    TopologicalGraph graph = new TopologicalGraph();
    graph.addEdge(node1, node2);

    TopologicalSort topologicalSort = new TopologicalSort();

    // Test
    TopologicalNode[] nodes = topologicalSort.sort(graph);
    assertEquals("no nodes", 2, nodes.length);
    assertEquals("node 1", node1, nodes[0]);
    assertEquals("node 2", node2, nodes[1]);
  }

  /**
   * Sortierung mit zwei Kante im Graph:
   * ka(k1->k2)
   * ka(k3->k4)
   * 
   * @wirkung zwei sortierte Knoten
   */
  public void testSortiere03() {
    TopologicalGraph graph = new TopologicalGraph();
    graph.addEdge(node1, node2);
    graph.addEdge(node3, node4);

    TopologicalSort topologicalSort = new TopologicalSort();

    // Test
    TopologicalNode[] nodes = topologicalSort.sort(graph);
    assertEquals("no nodes", 4, nodes.length);
    // Die Reihenfolge der Knoten in gleicher Ebene ist durch 
    // Sortieralgorythmus vorgegeben. (Von Hinten alle Vorgaenger, dann Nachfolger)
    assertEquals("node 3", node3, nodes[0]);
    assertEquals("node 1", node1, nodes[1]);
    assertEquals("node 4", node4, nodes[2]);
    assertEquals("node 2", node2, nodes[3]);
  }

  /**
   * Sortierung mit zwei Kante im Graph:
   * ka(k1->k2) 
   * --ka(k2->k3)
   * 
   * @wirkung zwei sortierte Knoten
   */
  public void testSortiere04() {
    TopologicalGraph graph = new TopologicalGraph();
    graph.addEdge(node1, node2);
    graph.addEdge(node2, node3);

    TopologicalSort topologicalSort = new TopologicalSort();

    // Test
    TopologicalNode[] nodes = topologicalSort.sort(graph);
    assertEquals("no nodes", 3, nodes.length);
    assertEquals("node 1", node1, nodes[0]);
    assertEquals("node 2", node2, nodes[1]);
    assertEquals("node 3", node3, nodes[2]);
  }

  /**
   * Sortierung mit drei Kante im Graph: 
   * ka(k1->k2) 
   * --ka(k2->k3) 
   * ----k2(k3->k4)
   * Zusaetzlich Pruefung der Ebeneninformation.
   * 
   * @wirkung zwei sortierte Knoten
   */
  public void testSortiere05() {
    TopologicalGraph graph = new TopologicalGraph();

    graph.addEdge(node1, node2);
    graph.addEdge(node3, node4);
    graph.addEdge(node2, node3);

    TopologicalSort topologicalSort = new TopologicalSort();

    // Test
    TopologicalNode[] nodes = topologicalSort.sort(graph);
    assertEquals("no nodes", 4, nodes.length);
    assertEquals("node 1", node1, nodes[0]);
    assertEquals("level of node 1", node1.getLevel(), 0);
    assertEquals("node 2", node2, nodes[1]);
    assertEquals("level of node 2", node2.getLevel(), 1);
    assertEquals("node 3", node3, nodes[2]);
    assertEquals("level of node 3", node3.getLevel(), 2);
    assertEquals("node 4", node4, nodes[3]);
    assertEquals("level of node 4", node4.getLevel(), 3);
  }

  /**
   * Erzeugt einen neuen Knoten fuer die gegebene id, falls dieser Knoten noch
   * nicht existiert. Andernfalls wird der vorhandene Knoten geliefert.
   * @param id
   * @return TopologicalNode
   */
  private TopologicalNode createNode(String id) {
    TopologicalNode node = (TopologicalNode) nodeCache.get(id);
    if (node == null) {
      node = new TopologicalNode(id);
      nodeCache.put(id, node);
    }
    return node;
  }

}
