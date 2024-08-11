package math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FactorialTest {

    @Test
    void factorial() {
        assertEquals(120, Factorial.fact(5));
        assertEquals(720, Factorial.factLinear(6));
        assertEquals(5040, Factorial.factLinear(7));
    }

}