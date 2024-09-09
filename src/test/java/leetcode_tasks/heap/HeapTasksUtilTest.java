package leetcode_tasks.heap;

import org.junit.jupiter.api.Test;

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
}