package graphs;

// u = from, v = to

public class WeightedEdge extends Edge implements Comparable<WeightedEdge> {
    public final double weight;

    public WeightedEdge(int from, int to, double weight) {
        super(from, to);
        this.weight = weight;
    }

    @Override
    public WeightedEdge reversed() {
        return new WeightedEdge(from, to, weight);
    }

    // так можно упорядочить ребра по весу и найти ребро с минимальным весом
    @Override
    public int compareTo(WeightedEdge other) {
        Double mine = weight;
        Double theirs = other.weight;
        return mine.compareTo(theirs);
    }

    @Override
    public String toString() {
        return from + " " + weight + "> " + to;
    }
}