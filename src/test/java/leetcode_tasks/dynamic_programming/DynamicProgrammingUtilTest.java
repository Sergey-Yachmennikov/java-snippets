package leetcode_tasks.dynamic_programming;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DynamicProgrammingUtilTest {

    @Test
    void climbStairs() {
        assertEquals(1, DynamicProgrammingUtil.climbStairs(0));
        assertEquals(1, DynamicProgrammingUtil.climbStairs(1));
        assertEquals(2, DynamicProgrammingUtil.climbStairs(2));
        assertEquals(3, DynamicProgrammingUtil.climbStairs(3));
        assertEquals(5, DynamicProgrammingUtil.climbStairs(4));
        assertEquals(89, DynamicProgrammingUtil.climbStairs(10));
    }
}