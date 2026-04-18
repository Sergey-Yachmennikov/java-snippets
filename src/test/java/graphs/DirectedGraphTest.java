package graphs;

import org.junit.jupiter.api.Test;

class DirectedGraphTest {

    @Test
    void addEdgeTest() {
        DirectedGraph<String> graph = new DirectedGraph<>();
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("B", "D");
        graph.addEdge("C", "D");
        System.out.println(graph);
    }
}