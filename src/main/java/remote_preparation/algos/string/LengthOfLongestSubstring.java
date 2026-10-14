package remote_preparation.algos.string;

import java.util.HashMap;
import java.util.Map;

public class LengthOfLongestSubstring {

    static int lengthOfLongestSubstring(String s) {
        int[] arr = s.codePoints().toArray();
        Map<Integer, Integer> lastSeen = new HashMap<>();

        int left = 0;
        int maxLen = 0;

        for (int right = 0; right < arr.length; right++) {
            Integer prevIndex = lastSeen.get(arr[right]);
            if (prevIndex != null) {
                // max — обязателен, см. пояснение выше про "abba"
                left = Math.max(left, prevIndex + 1);
            }

            lastSeen.put(arr[right], right);
            maxLen = Math.max(maxLen, right - left + 1);
        }

        return maxLen;
    }

    public static void main(String[] args) {
        check(lengthOfLongestSubstring("abcabcbb"), 3, "abcabcbb -> abc");
        check(lengthOfLongestSubstring("pwwkew"), 3, "pwwkew -> wke (или pwk)");
        check(lengthOfLongestSubstring(""), 0, "пустая строка");
        check(lengthOfLongestSubstring("a"), 1, "один символ");
        check(lengthOfLongestSubstring("bbbbb"), 1, "все одинаковые");
        check(lengthOfLongestSubstring("abba"), 2, "ловушка на left без max(): abba -> ab/ba");
        check(lengthOfLongestSubstring("dvdf"), 3, "аналогичная ловушка: dvdf -> vdf");
        check(lengthOfLongestSubstring("привет"), 6, "кириллица без повторов");
        check(lengthOfLongestSubstring("аааабвгд"), 5, "кириллица с повтором в начале");

        System.out.println("Все проверки пройдены");
    }

    private static void check(int actual, int expected, String label) {
        if (actual != expected) {
            throw new AssertionError(label + ": ожидали " + expected + ", получили " + actual);
        }
        System.out.println("OK  " + label);
    }
}
