package math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FibonacciStarterTest {

    @Test
    void fibonacciRecursive() {
        int result = FibonacciStarter.fibonacciRecursive(10);
        assertEquals(55, result);
    }

    @Test
    void fibonacciLinear() {
        int result = FibonacciStarter.fibonacciLinear(10);
        assertEquals(55, result);
    }

    @Test
    void fibonacciStream() {
        int result = FibonacciStarter.fibonacciStream(10);
        assertEquals(55, result);
    }
}