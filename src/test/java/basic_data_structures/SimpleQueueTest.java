package basic_data_structures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleQueueTest {

    @Test
    void simpleQueueTest() {
        Queue<Integer> queue = new SimpleQueue<>();
        int number = 0;

        for (int i = 0; i < 10; i++) {
            queue.add(i);
        }

        while (!queue.isEmpty()) {
            assertEquals(number, queue.remove());
            number++;
        }
    }

}