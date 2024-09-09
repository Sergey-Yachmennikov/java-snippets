package leetcode_tasks.prefix_sum.stack.monotonic_stack;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MonotonicStackUtilTest {

    @Test
    void nextGreaterElement() {
        assertArrayEquals(new int[]{-1, 3, -1}, MonotonicStackUtil.nextGreaterElement(new int[]{4,1,2}, new int[]{1,3,4,2}));
    }

    @Test
    void dailyTemperatures() {
        assertArrayEquals(new int[]{1,1,4,2,1,1,0,0}, MonotonicStackUtil.dailyTemperatures(new int[]{73,74,75,71,69,72,76,73}));
    }
}