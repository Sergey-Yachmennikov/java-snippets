package leetcode_tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SlidingWindowUtilTest {

    @Test
    void findMaxAverage() {
        int[] nums = new int[]{1,12,-5,-6,50,3};
        int k = 4;
        assertEquals(12.75, SlidingWindowUtil.findMaxAverage(nums, k));
    }

    @Test
    void minSubArrayLength() {
        int[] nums = new int[]{2,3,1,2,4,3};
        int target = 7;
        assertEquals(2, SlidingWindowUtil.minSubArrayLength(target, nums));
    }

    @Test
    void findLHS() {
        int[] nums = new int[]{1,3,2,2,5,2,3,7};
        assertEquals(5, SlidingWindowUtil.findLongestHarmoniousSubsequence(nums));
    }

    @Test
    void lengthOfTheLongestSubstringTwoDistinct() {
        assertEquals(5, SlidingWindowUtil.lengthOfTheLongestSubstringTwoDistinct("ccaabbb"));
    }

    @Test
    void lengthOfTheLongestSubstringWithoutRepeatingCharacters() {
        assertEquals(3, SlidingWindowUtil.lengthOfTheLongestSubstringWithoutRepeatingCharacters("abcabcbb"));
        assertEquals(1, SlidingWindowUtil.lengthOfTheLongestSubstringWithoutRepeatingCharacters("bbbbb"));
        assertEquals(3, SlidingWindowUtil.lengthOfTheLongestSubstringWithoutRepeatingCharacters("pwwkew"));
    }

    @Test
    void findMaxConsecutiveOnes1() {
        assertEquals(3, SlidingWindowUtil.findMaxConsecutiveOnes1(new int[]{1,1,0,1,1,1}));
        assertEquals(3, SlidingWindowUtil.findMaxConsecutiveOnes1(new int[]{1,1,1,0,1,1}));
    }

    @Test
    void findMaxConsecutiveOnes2() {
        assertEquals(4, SlidingWindowUtil.findMaxConsecutiveOnes2(new int[]{1,0,1,1,0,1}));
    }

    @Test
    void checkPermutationInclusion() {
        assertTrue(SlidingWindowUtil.checkPermutationInclusion("ab", "eidbaooo"));
    }
}