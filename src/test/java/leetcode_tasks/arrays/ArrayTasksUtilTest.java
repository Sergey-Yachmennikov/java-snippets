package leetcode_tasks.arrays;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class ArrayTasksUtilTest {

    @Test
    void merge() {
        int[][] expectedResult = new int[][]{ {1,6}, {8,10}, {15,18} };

        // mergeV1
        int[][] merged = ArrayTasksUtil.merge(new int[][]{ {1,3}, {2,6}, {8,10}, {15,18} });
        assertArrayEquals(expectedResult, merged);

        // mergeV2
        int[][] merged2 = ArrayTasksUtil.mergeV2(new int[][]{ {1,3}, {2,6}, {8,10}, {15,18} });
        assertArrayEquals(expectedResult, merged2);
    }

    @Test
    void insertInterval() {
        int[][] expectedResult = new int[][]{ {1,5}, {6,9} };
        int[][] resultSet = ArrayTasksUtil.insertInterval(new int[][]{ {1,3}, {6,9} }, new int[]{2,5});
        assertArrayEquals(expectedResult, resultSet);

        int[][] expectedResult2 = new int[][]{ {1,2}, {3,10}, {12,16}};
        int[][] resultSet2 = ArrayTasksUtil.insertInterval(new int[][]{ {1,2}, {3,5}, {6,7}, {8,10}, {12,16} }, new int[]{4,8});
        assertArrayEquals(expectedResult2, resultSet2);
    }
}