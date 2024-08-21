package leetcode_tasks;

public class NumberPalindrome {

    private NumberPalindrome() {}

    public static boolean isPalindrome(int v) {
        if (v < 0 || v % 10 == 0) return false;

        int reversed_v = 0;
        while (v >= reversed_v) {
            int pop = v % 10;
            v /= 10;
            reversed_v = reversed_v * 10 + pop;
        }

        return v == reversed_v / 10;
    }

    public static int reverseInteger(int v) {
        if (v < 0) v = Math.abs(v);
        if (v > 0 && v < 10) return v;

        int reversed_v = 0;
        while(v != 0) {
            int lastInt = v % 10;
            v /= 10;
            reversed_v = reversed_v * 10 + lastInt;
        }

        return reversed_v;
    }
}
