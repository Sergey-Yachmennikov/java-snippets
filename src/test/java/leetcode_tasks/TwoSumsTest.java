package leetcode_tasks;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TwoSumsTest {

    @Test
    void twoSums() {
        TwoSums ts = new TwoSums();
        int[] sums = ts.twoSums(new int[]{2, 7, 11, 15}, 9);
        assertArrayEquals(new int[]{1, 2}, sums);
    }
}