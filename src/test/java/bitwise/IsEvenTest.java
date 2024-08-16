package bitwise;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IsEvenTest {

    @Test
    void isEven() {
        assertFalse(IsEven.isEven(1));
        assertTrue(IsEven.isEven(2));
        assertFalse(IsEven.isEven(3));
        assertTrue(IsEven.isEven(4));
        assertFalse(IsEven.isEven(5));
        assertTrue(IsEven.isEven(6));
        assertFalse(IsEven.isEven(7));
        assertTrue(IsEven.isEven(8));

        assertTrue(IsEven.isEven(0));
        assertFalse(IsEven.isEven(-1));
        assertTrue(IsEven.isEven(-2));
    }
}