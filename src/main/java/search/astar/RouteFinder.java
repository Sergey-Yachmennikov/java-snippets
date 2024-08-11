package search.astar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class RouteFinder<T extends GraphNode> {
    private static final Logger log = LoggerFactory.getLogger(RouteFinder.class);
    private final Graph<T> graph;
    private final Scorer<T> nextNodeScorer;
    private final Scorer<T> targetScorer;

    public RouteFinder(Graph<T> graph, Scorer<T> nextNodeScorer, Scorer<T> targetScorer) {
        this.graph = graph;
        this.nextNodeScorer = nextNodeScorer;
        this.targetScorer = targetScorer;
    }

    public List<T> findRoute(T from, T to) {
        Map<T, RouteNode<T>> allNodes = new HashMap<>();
        Queue<RouteNode> openSet = new PriorityQueue<>();

        RouteNode<T> start = new RouteNode<>(from, null, 0d, targetScorer.computeCost(from, to));
        allNodes.put(from, start);
        openSet.add(start);

        while (!openSet.isEmpty()) {
            RouteNode<T> next = openSet.poll();
            if (next.getCurrent().equals(to)) {

                List<T> routeByPoints = new ArrayList<>();
                RouteNode<T> current = next;
                do {
                    routeByPoints.add(current.getCurrent());
                    current = allNodes.get(current.getPrevious());
                } while (current != null);

                return routeByPoints.reversed();
            }

            Set<T> connections = graph.getConnections(next.getCurrent());
            connections.forEach(connection -> {

                double newScore = next.getRouteScore() + nextNodeScorer.computeCost(next.getCurrent(), connection);
                RouteNode<T> nextNode = allNodes.getOrDefault(connection, new RouteNode<>(connection));
                allNodes.put(connection, nextNode);

                if (nextNode.getRouteScore() > newScore) {
                    nextNode.setPrevious(next.getCurrent());
                    nextNode.setRouteScore(newScore);
                    nextNode.setEstimatedScore(newScore + targetScorer.computeCost(connection, to));
                    openSet.add(nextNode);
                }
            });
        }

        throw new IllegalStateException("No route found");
    }

}