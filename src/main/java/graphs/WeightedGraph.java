package graphs;

import java.util.*;
import java.util.function.IntConsumer;

public class WeightedGraph<V> extends Graph<V, WeightedEdge> {

    public WeightedGraph(List<V> vertices) {
        super(vertices);
    }

    // Это невзвешенный граф, поэтому мы всегда добавляем ребра в обоих направлениях
    public void addEdge(WeightedEdge edge) {
        edges.get(edge.from).add(edge);
        edges.get(edge.to).add(edge.reversed());
    }

    public void addEdge(int from, int to, float weight) {
        addEdge(new WeightedEdge(from, to, weight));
    }

    public void addEdge(V first, V second, float weight) {
        addEdge(indexOf(first), indexOf(second), weight);
    }

    public static double totalWeight(List<WeightedEdge> path) {
        return path.stream().mapToDouble(we -> we.weight).sum();
    }

    public List<WeightedEdge> mst(int start) {
        LinkedList<WeightedEdge> result = new LinkedList<>(); // здесь мы уже
        // были
        if (start < 0 || start > (getVertexCount() - 1)) {
            return result;
        }

        PriorityQueue<WeightedEdge> pq = new PriorityQueue<>();
        boolean[] visited = new boolean[getVertexCount()];
        // это похоже на внутреннюю функцию "visit"
        IntConsumer visit = index -> {
            visited[index] = true; // пометить как прочитанное
            for (WeightedEdge edge : edgesOf(index)) {
            // добавляем все ребра отсюда в pq
                if (!visited[edge.to]) {
                    pq.offer(edge);
                }
            }
        };

        visit.accept(start); // первая вершина, с которой все начинается
        while (!pq.isEmpty()) { // продолжаем, пока остаются необработанные

            // вершины
            WeightedEdge edge = pq.poll();
            if (visited[edge.to]) {
                continue; // никогда не просматриваем дважды
            }

            // на данный момент это минимальный вес, так что добавляем в дерево
            result.add(edge);
            visit.accept(edge.to); // посетите места соединений
        }
        return result;
    }

    public void printWeightedPath(List<WeightedEdge> wp) {
        for (WeightedEdge edge : wp) {
            System.out.println(vertexAt(edge.from) + " " + edge.weight + "> " + vertexAt(edge.to));
        }

        System.out.println("Total Weight: " + totalWeight(wp));
    }

    // упрощенная печать графика
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getVertexCount(); i++) {
            sb.append(vertexAt(i));
            sb.append(" -> ");
            sb.append(Arrays.toString(edgesOf(i).stream()
                    .map(we -> "(" + vertexAt(we.to) + ", " + we.weight +
                            ")").toArray()));
            sb.append(System.lineSeparator());
        }

        return sb.toString();
    }

    public static final class DijkstraNode implements Comparable<DijkstraNode> {
        public final int vertex;
        public final double distance;

        public DijkstraNode(int vertex, double distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        @Override
        public int compareTo(DijkstraNode other) {
            Double mine = distance;
            Double theirs = other.distance;
            return mine.compareTo(theirs);
        }
    }

    public static final class DijkstraResult {
        public final double[] distances;
        public final Map<Integer, WeightedEdge> pathMap;

        public DijkstraResult(double[] distances, Map<Integer, WeightedEdge> pathMap) {
            this.distances = distances;
            this.pathMap = pathMap;
        }
    }

    public DijkstraResult dijkstra(V root) {
        int first = indexOf(root); // найти начальный индекс
        // вначале расстояния неизвестны
        double[] distances = new double[getVertexCount()];
        distances[first] = 0; // корневая вершина равна 0
        boolean[] visited = new boolean[getVertexCount()];
        visited[first] = true;
        // как добраться до каждой вершины?
        HashMap<Integer, WeightedEdge> pathMap = new HashMap<>();
        PriorityQueue<DijkstraNode> pq = new PriorityQueue<>();
        pq.offer(new DijkstraNode(first, 0));
        while (!pq.isEmpty()) {
            int index = pq.poll().vertex; // исследовать ближайшую вершину
            double distU = distances[index]; // это мы уже, должно быть, видели
            // все ребра и вершины для данной вершины
            for (WeightedEdge we : edgesOf(index)) {
                // старое расстояние до этой вершины
                double distV = distances[we.to];
                // новое расстояние до этой вершины
                double pathWeight = we.weight + distU;
                // новая вершина или найден более короткий путь?
                if (!visited[we.to] || (distV > pathWeight)) {
                    visited[we.to] = true;
                    // изменить расстояние до этой вершины
                    distances[we.to] = pathWeight;
                    // заменить ребро на более короткий путь к этой вершине
                    pathMap.put(we.to, we);
                    // вскоре мы это проверим
                    pq.offer(new DijkstraNode(we.to, pathWeight));
                }
            }
        }

        return new DijkstraResult(distances, pathMap);
    }


}