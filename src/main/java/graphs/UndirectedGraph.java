package graphs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UndirectedGraph<T> {
    private final Map<T, Set<T>> adjacency = new HashMap<>();

    public void addVertex(T vertex) {
        adjacency.putIfAbsent(vertex, new HashSet<>());
    }

    public void addEdge(T u, T v) {
        adjacency.computeIfAbsent(u, k -> new HashSet<>()).add(v);
        adjacency.computeIfAbsent(v, k -> new HashSet<>()).add(u); // ключевое отличие
    }

    public void removeEdge(T u, T v) {
        adjacency.getOrDefault(u, Set.of()).remove(v);
        adjacency.getOrDefault(v, Set.of()).remove(u); // убираем в обе стороны
    }

    public Set<T> neighbors(T vertex) {
        return adjacency.getOrDefault(vertex, Set.of());
    }

    public int degree(T vertex) {
        return neighbors(vertex).size();
    }

    public Set<T> vertices() {
        return adjacency.keySet();
    }

    public int edgeCount() {
        // каждое ребро посчитано дважды
        return adjacency.values().stream()
                .mapToInt(Set::size).sum() / 2;
    }

    @Override
    public String toString() {
        return adjacency.toString();
    }
}