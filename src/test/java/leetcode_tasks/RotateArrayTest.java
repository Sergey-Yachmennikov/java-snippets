package leetcode_tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RotateArrayTest {
    private static int[] array = new int[]{1,2,3,4,5,6,7};
    private static int[] expectedResult = new int[]{5,6,7,1,2,3,4};
    private static final int k = 3;

    @Test
    void rotate() {
        RotateArray.rotate(array, k);
        assertArrayEquals(expectedResult, array);
    }
}