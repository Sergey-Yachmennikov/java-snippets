package graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// V — тип вершин графа
// E — тип ребер
public abstract class Graph<V, E extends Edge> {
    private ArrayList<V> vertices = new ArrayList<>();
    protected ArrayList<ArrayList<E>> edges = new ArrayList<>();

    protected Graph() {}

    protected Graph(List<V> vertices) {
        this.vertices.addAll(vertices);
        for (V vertex : vertices) {
            edges.add(new ArrayList<>());
        }
    }

    // Количество вершин
    public int getVertexCount() {
        return vertices.size();
    }

    // Количество ребер
    public int getEdgeCount() {
        return edges.stream().mapToInt(ArrayList::size).sum();
    }

    // Добавляем вершину в граф и возвращаем ее индекс
    public int addVertex(V vertex) {
        vertices.add(vertex);
        edges.add(new ArrayList<>());
        return getVertexCount() - 1;
    }

    // Поиск вершины по индексу
    public V vertexAt(int index) {
        return vertices.get(index);
    }
    // Поиск индекса вершины в графе
    public int indexOf(V vertex) {
        return vertices.indexOf(vertex);
    }
    // Поиск вершин, с которыми связана вершина с заданным индексом
    public List<V> neighborsOf(int index) {
        return edges.get(index).stream()
                .map(edge -> vertexAt(edge.to))
                .toList();
    }

    // Поиск индекса вершины; возвращает ее соседей (удобный метод)
    public List<V> neighborsOf(V vertex) {
        return neighborsOf(indexOf(vertex));
    }

    // Возвращает все ребра, связанные с вершиной, имеющей заданный индекс
    public List<E> edgesOf(int index) {
        return edges.get(index);
    }

    // Поиск индекса вершины; возвращает ее ребра (удобный метод)
    public List<E> edgesOf(V vertex) {
        return edgesOf(indexOf(vertex));
    }

    // Упрощенный красивый вывод графа
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < getVertexCount(); i++) {
            sb.append(vertexAt(i));
            sb.append(" -> ");
            sb.append(Arrays.toString(neighborsOf(i).toArray()));
            sb.append(System.lineSeparator());
        }

        return sb.toString();
    }
}
