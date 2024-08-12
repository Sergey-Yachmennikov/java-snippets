package interpretator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TwoStackAlgorithmTest {
    static final String expTemplate = "( 1 + ( ( 2 + 3 ) * ( 4 * 5) ) )";

    @Test
    void calculate() {
        System.out.println();
        assertEquals(101, TwoStackAlgorithm.calculate(expTemplate));
    }
}