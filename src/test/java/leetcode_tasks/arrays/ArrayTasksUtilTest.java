package leetcode_tasks.arrays;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayTasksUtilTest {

    @Test
    void merge() {
        int[][] expectedResult = new int[][]{ {1,6}, {8,10}, {15,18} };

        // mergeV1
        int[][] merged = ArrayTasksUtil.merge(new int[][]{ {1, 3}, {2, 6}, {8, 10}, {15, 18} });
        for (int i = 0; i < merged.length; i++) {
            assertArrayEquals(expectedResult[i], merged[i]);
        }

        // mergeV2
        int[][] merged2 = ArrayTasksUtil.mergeV2(new int[][]{ {1, 3}, {2, 6}, {8, 10}, {15, 18} });
        for (int i = 0; i < merged2.length; i++) {
            assertArrayEquals(expectedResult[i], merged2[i]);
        }
    }
}