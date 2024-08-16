package leetcode_tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LongestPalindromicSubstringTest {
    private static final String template1 = "racecar";
    private static final String template2 = "aabbaa";
    private static final String template3 = "erredsfdfdfd";
    private static final String template4 = "abcdfeg";

    @Test
    void longestPalindromicSubstringTest() {
        var palindromic = new LongestPalindromicSubstring();
        System.out.println(palindromic.longestPalindrome(template1));
        System.out.println(palindromic.longestPalindrome(template2));
        System.out.println(palindromic.longestPalindrome(template3));
        System.out.println(palindromic.longestPalindrome(template4));
    }
}