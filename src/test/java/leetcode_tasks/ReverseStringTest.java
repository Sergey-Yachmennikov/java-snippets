package leetcode_tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReverseStringTest {

    @Test
    void reverse() {
        String s = "abc def xyz";
        System.out.println(ReverseString.reverse(s));
    }
}