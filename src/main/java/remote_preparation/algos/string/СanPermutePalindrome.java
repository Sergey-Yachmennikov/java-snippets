package remote_preparation.algos.string;

import java.util.HashMap;
import java.util.Map;

public class СanPermutePalindrome {

    public static void main(String[] args) {
        String[] tests = {
                "mmo",
                "yakak",
                "travel",
                "aabb",
                "aabbc",
                "aabbcd",
        };

        for (String test : tests) {
            System.out.println(
                    "\"" + test + "\" -> " + canPermutePalindrome(test)
            );
        }
    }

    public static boolean canPermutePalindrome(String s) {
        if (s == null) {
            return false;
        }

        Map<Character, Integer> counts = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            counts.put(ch, counts.getOrDefault(ch, 0) + 1);
        }

        int oddCount = 0;

        for (int count : counts.values()) {
            if (count % 2 == 1) {
                oddCount++;

                if (oddCount > 1) {
                    return false;
                }
            }
        }

        return true;
    }
}
