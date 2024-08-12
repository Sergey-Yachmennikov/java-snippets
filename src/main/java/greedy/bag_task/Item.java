package greedy.bag_task;

public record Item(String name, int weight, int cost) implements Comparable<Item> {

    @Override
    public int compareTo(Item o) {
        return this.cost > o.cost ? -1 : 1;
    }
}