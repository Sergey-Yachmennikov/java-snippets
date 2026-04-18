package graphs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UndirectedGraphTest {

    @Test
    void addEdgeTest() {
        UndirectedGraph<String> graph = new UndirectedGraph<>();
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("B", "D");
        graph.addEdge("C", "D");
        System.out.println(graph);
    }

}