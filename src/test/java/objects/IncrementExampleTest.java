package objects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IncrementExampleTest {

    @Test
    void nextTest() {
        IncrementExample wrapper = new IncrementExample();
        assertEquals(1, wrapper.next()); // element by index 0
        assertEquals(1, wrapper.getIndex()); // index = 1
    }

    @Test
    void nextSecondTest() {
        IncrementExample wrapper = new IncrementExample();
        assertEquals(2, wrapper.nextSecond()); // element by index 1
        assertEquals(1, wrapper.getIndex()); // index = 1 too
    }
}