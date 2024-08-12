package greedy.bag_task;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ProblemOfTheBackpackTest {

    private static final List<Item> items = new ArrayList<>();

    @BeforeAll
    static void fillList() {
        items.add(new Item("гитара",7, 800));
        items.add(new Item("утюг",6, 500));
        items.add(new Item("чайник",3, 300));
        items.add(new Item("лампа",4, 500));
        items.add(new Item("телевизор",15, 2000));
        items.add(new Item("ваза",2, 450));
        items.add(new Item("миксер",1, 400));
        items.add(new Item("блендер",3, 200));

        Collections.sort(items);
    }

    @Test
    void fillBackpack() {
        Bag firstBag = new Bag(30);
        ProblemOfTheBackpack.fillBackpack(firstBag, items);
        System.out.println(firstBag);
        assertEquals(3700, firstBag.getCurrentCost());

    }

    @Test
    void effectiveFillBackpack() {
        Bag secondBag = new Bag(30);
        ProblemOfTheBackpack.effectiveFillBackpack(secondBag, items);
        System.out.println(secondBag);
        assertEquals(4150, secondBag.getCurrentCost());
    }
}