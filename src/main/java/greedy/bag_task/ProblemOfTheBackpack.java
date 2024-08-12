package greedy.bag_task;

import java.util.*;

public class ProblemOfTheBackpack {

    private ProblemOfTheBackpack() {}

    public static void fillBackpack(Bag bag, List<Item> items) {
        for (Item item : items) {
            if(bag.getMaxWeight() > bag.getCurrentWeight() + item.weight()) {
                bag.addItem(item);
            }
        }
    }

    public static void effectiveFillBackpack(Bag bag, List<Item> items) {
        Map<Double, Item> sortByRatio = new TreeMap<>(Comparator.reverseOrder());
        for (Item item : items) {
            sortByRatio.put((double) item.cost() / item.weight(), item);
        }

        for (Map.Entry<Double, Item> entry : sortByRatio.entrySet()) {
            if(bag.getMaxWeight() > bag.getCurrentWeight() + entry.getValue().weight()) {
                bag.addItem(entry.getValue());
            }
        }
    }
}
