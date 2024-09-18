package leetcode_tasks.dynamic_programming;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    void wordBreak() {
        assertTrue(DynamicProgrammingUtil.wordBreak("leetcode", List.of("leet","code")));
        assertTrue(DynamicProgrammingUtil.wordBreak("applepenapple", List.of("apple","pen")));
        assertFalse(DynamicProgrammingUtil.wordBreak("catsandog", List.of("cats","dog","sand","and","cat")));
    }

    @Test
    void minCostClimbingStairs() {
        assertEquals(15, DynamicProgrammingUtil.minCostClimbingStairs(new int[] {10,15,20}));
        assertEquals(6, DynamicProgrammingUtil.minCostClimbingStairs(new int[] {1,100,1,1,1,100,1,1,100,1}));

        assertEquals(15, DynamicProgrammingUtil.minCostClimbingStairsIterative(new int[] {10,15,20}));
        assertEquals(6, DynamicProgrammingUtil.minCostClimbingStairsIterative(new int[] {1,100,1,1,1,100,1,1,100,1}));

        assertEquals(15, DynamicProgrammingUtil.minCostClimbingStairsIterativeOptimized(new int[] {10,15,20}));
        assertEquals(6, DynamicProgrammingUtil.minCostClimbingStairsIterativeOptimized(new int[] {1,100,1,1,1,100,1,1,100,1}));
    }

    @Test
    void findMaxSubarraySum() {
        int[] array = {-2, -3, 4, -1, -2, 1, 5, -3};
        assertEquals(7, DynamicProgrammingUtil.findMaxSubarraySum(array));
        assertEquals(7, DynamicProgrammingUtil.findMaxSubarraySumBruteForce(array));
    }

    @Test
    void maxProduct() {
        assertEquals(6, DynamicProgrammingUtil.maxProduct(new int[] {2,3,-1,4}));
        assertEquals(0, DynamicProgrammingUtil.maxProduct(new int[] {-2,0,-1}));
    }

    @Test
    void canPartition() {
        assertTrue(DynamicProgrammingUtil.canPartition(new int[] {1,5,11,5}));
        assertFalse(DynamicProgrammingUtil.canPartition(new int[] {1,2,3,5}));
    }

    @Test
    void canPartitionOneDimension() {
        assertTrue(DynamicProgrammingUtil.canPartitionOneDimension(new int[] {1,5,11,5}));
        assertFalse(DynamicProgrammingUtil.canPartitionOneDimension(new int[] {1,2,3,5}));
    }
}