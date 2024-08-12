package greedy.bag_task;

import java.util.ArrayList;
import java.util.List;

public class Bag {
    private final int maxWeight;
    private List<Item> items;
    private int currentWeight;
    private int currentCost;

    public Bag(int maxWeight) {
        this.maxWeight = maxWeight;
        items = new ArrayList<>();
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public int getCurrentCost() {
        return currentCost;
    }

    public int getCurrentWeight() {
        return currentWeight;
    }

    public void addItem(Item item) {
        items.add(item);
        currentWeight += item.weight();
        currentCost += item.cost();
    }

    @Override
    public String toString() {
        return "Bag{" +
                "maxWeight=" + maxWeight +
                ", currentWeight=" + currentWeight +
                ", currentCost=" + currentCost +
                '}';
    }
}
