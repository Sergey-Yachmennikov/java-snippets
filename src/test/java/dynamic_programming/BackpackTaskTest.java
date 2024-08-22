package dynamic_programming;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BackpackTaskTest {

    @Test
    void fillTheBackpack() {
        int[] weights = {3,4,5,8,9};
        int[] prices = {1,6,4,7,6};
        final int maxWeight = 13;
        List<Integer> result = BackpackTask.fillTheBackpack(weights, prices, maxWeight);

        assertEquals(2, result.getFirst());
        assertEquals(4, result.getLast());
    }
}