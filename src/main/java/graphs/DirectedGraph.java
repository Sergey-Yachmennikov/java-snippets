package graphs;

import java.util.*;

public class DirectedGraph<T> {
    private final Map<T, List<T>> adjacency = new HashMap<>();

    public void addVertex(T vertex) {
        adjacency.putIfAbsent(vertex, new ArrayList<>());
    }

    public void addEdge(T from, T to) {
        adjacency.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
        adjacency.putIfAbsent(to, new ArrayList<>()); // to тоже должна быть в графе
    }

    public List<T> neighbors(T vertex) {
        return adjacency.getOrDefault(vertex, List.of());
    }

    public Set<T> vertices() {
        return adjacency.keySet();
    }

    @Override
    public String toString() {
        return adjacency.toString();
    }
}