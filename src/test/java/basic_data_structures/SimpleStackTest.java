package basic_data_structures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleStackTest {

    @Test
    void simpleQueueTest() {
        Stack<Integer> stack = new SimpleStack<>();
        int number = 9;

        for (int i = 0; i < 10; i++) {
            stack.push(i);
        }

        while (!stack.isEmpty()) {
            assertEquals(number, stack.pop());
            number--;
        }
    }
}