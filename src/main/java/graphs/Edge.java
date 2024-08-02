package graphs;

public class Edge {
    public final int from; // вершина "откуда"
    public final int to; // вершина "куда"

    public Edge(int from, int to) {
        this.from = from;
        this.to = to;
    }

    public Edge reversed() {
        return new Edge(from, to);
    }

    @Override
    public String toString() {
        return from + " -> " + to;
    }
}