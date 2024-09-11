package leetcode_tasks.heap;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HeapTasksUtilTest {

    @Test
    void findKthLargest() {
        assertEquals(5, HeapTasksUtil.findKthLargest(new int[]{3,2,1,5,6,4}, 2));
        assertEquals(4, HeapTasksUtil.findKthLargest(new int[]{3,2,3,1,2,4,5,5,6}, 4));
    }

    @Test
    void topKFrequent() {
        assertArrayEquals(new int[]{1,2}, HeapTasksUtil.topKFrequent(new int[]{1,1,1,2,2,3}, 2));
        assertArrayEquals(new int[]{1}, HeapTasksUtil.topKFrequent(new int[]{1}, 1));
    }

    @Test
    void kSmallestPairs() {
        int[][] expectedResult = new int[][]{
                new int[] {1,2},
                new int[] {1,4},
                new int[] {1,6},
        };

        List<List<Integer>> lists = HeapTasksUtil.kSmallestPairs(new int[]{1, 7, 11}, new int[]{2, 4, 6}, 3);

        for (int i = 0; i < expectedResult.length; i++) {
            int[] ints = expectedResult[i];
            List<Integer> vals = lists.get(i);
            assertEquals(ints[0], vals.getFirst());
            assertEquals(ints[1], vals.getLast());
        }
    }
}