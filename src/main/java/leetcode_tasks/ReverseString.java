package leetcode_tasks;

/**
 * @topic String
 */
public class ReverseString {

    private ReverseString() {}

    public static String reverse(String str) {
        char[] chars = str.toCharArray();
        int start = 0;
        int middle = str.length() >> 1;

        while (start <= middle) {
            swap(chars, start, chars.length - 1 - start);
            start++;
        }

        return String.valueOf(chars);
    }

    private static void swap(char[] chars, int a, int b) {
        char temp = chars[a];
        chars[a] = chars[b];
        chars[b] = temp;
    }
}
