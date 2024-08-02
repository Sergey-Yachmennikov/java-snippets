package graphs;

import org.junit.jupiter.api.Test;
import tasks.search.GenericSearch;

import java.util.List;

class GraphTest {

    @Test
    void unweightGraphTest() {
        // Представляет 15 крупнейших MSA в США.
        UnweightedGraph<String> cityGraph = new UnweightedGraph<>(
                List.of("Seattle", "San Francisco", "Los Angeles", "Riverside", "Phoenix", "Chicago", "Boston",
                        "New York", "Atlanta", "Miami", "Dallas", "Houston", "Detroit", "Philadelphia", "Washington")
        );

        cityGraph.addEdge("Seattle", "Chicago");
        cityGraph.addEdge("Seattle", "San Francisco");
        cityGraph.addEdge("San Francisco", "Riverside");
        cityGraph.addEdge("San Francisco", "Los Angeles");
        cityGraph.addEdge("Los Angeles", "Riverside");
        cityGraph.addEdge("Los Angeles", "Phoenix");
        cityGraph.addEdge("Riverside", "Phoenix");
        cityGraph.addEdge("Riverside", "Chicago");
        cityGraph.addEdge("Phoenix", "Dallas");
        cityGraph.addEdge("Phoenix", "Houston");
        cityGraph.addEdge("Dallas", "Chicago");
        cityGraph.addEdge("Dallas", "Atlanta");
        cityGraph.addEdge("Dallas", "Houston");
        cityGraph.addEdge("Houston", "Atlanta");
        cityGraph.addEdge("Houston", "Miami");
        cityGraph.addEdge("Atlanta", "Chicago");
        cityGraph.addEdge("Atlanta", "Washington");
        cityGraph.addEdge("Atlanta", "Miami");
        cityGraph.addEdge("Miami", "Washington");
        cityGraph.addEdge("Chicago", "Detroit");
        cityGraph.addEdge("Detroit", "Boston");
        cityGraph.addEdge("Detroit", "Washington");
        cityGraph.addEdge("Detroit", "New York");
        cityGraph.addEdge("Boston", "New York");
        cityGraph.addEdge("New York", "Philadelphia");
        cityGraph.addEdge("Philadelphia", "Washington");

        // System.out.println(cityGraph);

        GenericSearch.Node<String> bfsResult = GenericSearch.dfs("Boston",
                v -> v.equals("Miami"),
                cityGraph::neighborsOf);
        if (bfsResult == null) {
            System.out.println("No solution found using breadth-first search!");
        } else {
            List<String> path = GenericSearch.nodeToPath(bfsResult);
            System.out.println("Path from Boston to Miami:");
            System.out.println(path);
        }
    }

    @Test
    void weightGraphTest() {
        // представляет 15 крупнейших MSA в США.
        WeightedGraph<String> cityGraph2 = new WeightedGraph<>(
                List.of("Seattle", "San Francisco", "Los Angeles", "Riverside", "Phoenix", "Chicago", "Boston",
                        "New York", "Atlanta", "Miami", "Dallas", "Houston", "Detroit", "Philadelphia", "Washington"));

        cityGraph2.addEdge("Seattle", "Chicago", 1737);
        cityGraph2.addEdge("Seattle", "San Francisco", 678);
        cityGraph2.addEdge("San Francisco", "Riverside", 386);
        cityGraph2.addEdge("San Francisco", "Los Angeles", 348);
        cityGraph2.addEdge("Los Angeles", "Riverside", 50);
        cityGraph2.addEdge("Los Angeles", "Phoenix", 357);
        cityGraph2.addEdge("Riverside", "Phoenix", 307);
        cityGraph2.addEdge("Riverside", "Chicago", 1704);
        cityGraph2.addEdge("Phoenix", "Dallas", 887);
        cityGraph2.addEdge("Phoenix", "Houston", 1015);
        cityGraph2.addEdge("Dallas", "Chicago", 805);
        cityGraph2.addEdge("Dallas", "Atlanta", 721);
        cityGraph2.addEdge("Dallas", "Houston", 225);
        cityGraph2.addEdge("Houston", "Atlanta", 702);
        cityGraph2.addEdge("Houston", "Miami", 968);
        cityGraph2.addEdge("Atlanta", "Chicago", 588);
        cityGraph2.addEdge("Atlanta", "Washington", 543);
        cityGraph2.addEdge("Atlanta", "Miami", 604);
        cityGraph2.addEdge("Miami", "Washington", 923);
        cityGraph2.addEdge("Chicago", "Detroit", 238);
        cityGraph2.addEdge("Detroit", "Boston", 613);
        cityGraph2.addEdge("Detroit", "Washington", 396);
        cityGraph2.addEdge("Detroit", "New York", 482);
        cityGraph2.addEdge("Boston", "New York", 190);
        cityGraph2.addEdge("New York", "Philadelphia", 81);
        cityGraph2.addEdge("Philadelphia", "Washington", 123);

        WeightedGraph.DijkstraResult losAngeles = cityGraph2.dijkstra("Los Angeles");
        System.out.println(losAngeles.toString());
    }

}