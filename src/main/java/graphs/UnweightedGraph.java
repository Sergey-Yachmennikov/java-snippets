package graphs;

import java.util.List;

public class UnweightedGraph<V> extends Graph<V, Edge> {

    public UnweightedGraph(List<V> vertices) {
        super(vertices);
    }

    // Это неориентированный граф, поэтому мы всегда
    // добавляем ребра в обоих направлениях.
    public void addEdge(Edge edge) {
        edges.get(edge.from).add(edge);
        edges.get(edge.to).add(edge.reversed());
    }

    // Добавление ребра с помощью индексов вершин (удобный метод)
    public void addEdge(int from, int to) {
        addEdge(new Edge(from, to));
    }

    // Добавление ребра путем просмотра индексов вершин (удобный метод)
    public void addEdge(V first, V second) {
        addEdge(new Edge(indexOf(first), indexOf(second)));
    }
}