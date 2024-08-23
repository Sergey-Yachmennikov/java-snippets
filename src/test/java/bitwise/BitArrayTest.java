package bitwise;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BitArrayTest {

    @Test
    void bitArrayTest() {
        BitArray bitArray = new BitArray(10);
        bitArray.set(0, 1);
        bitArray.set(9, 1);
        bitArray.set(5, 1);
        bitArray.set(5, 0);
        System.out.println(bitArray);
    }

}