package bitwise;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class BitwiseExamplesTest {

    @Test
    void binaryShiftInteger() {
        int i = 0b00000010;
        int j = 0b00000100;
        int p = 0b00001000;
        int p1 = 0b00001001;
        int p2 = 0b00001011;
        int p3 = 0b00001111;
        assertEquals(2, i);
        assertEquals(4, j);
        assertEquals(8, p);
        assertEquals(9, p1);
        assertEquals(11, p2);
        assertEquals(15, p3);
        assertEquals(4, 0b00001000 >> 1);
        assertEquals(32, 0b00001000 << 2);
        assertEquals(4, 0b00001000 >> 1);
        assertEquals(2, 0b00001000 >> 2);

    }

    @Test
    void binaryBooleanTest() { // add more
        assertEquals(-101, ~100);
        assertEquals(100, ~-101);

        // logical or
        int n = 0b00001011;
        assertEquals(0b00001111, n | 0b00000100);
    }

    @Test
    void binaryCombineColorInOneInteger() {
        byte r = 64;
        int g = 128;
        int b = 32;
        int alpha = 255;
        int color = alpha << 24 | r << 16 | g << 8 | b;
        System.out.println(Integer.toBinaryString(color));
    }
}