package leetcode_tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NumberPalindromeTest {

    @Test
    void isPalindrome() {
        assertTrue(NumberPalindrome.isPalindrome(1020201));
        assertFalse(NumberPalindrome.isPalindrome(100000));
    }

    @Test
    void reverseInteger() {
        assertEquals(987654321, NumberPalindrome.reverseInteger(123456789));
        assertEquals(9, NumberPalindrome.reverseInteger(9));
        assertEquals(5, NumberPalindrome.reverseInteger(-5));
        assertEquals(995, NumberPalindrome.reverseInteger(-599));
    }
}