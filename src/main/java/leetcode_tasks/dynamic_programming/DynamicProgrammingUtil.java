package leetcode_tasks.dynamic_programming;

import java.util.List;

public class DynamicProgrammingUtil {

    private DynamicProgrammingUtil() {}

    public static int climbStairs(int n) {

        if(n <= 1) return 1;

        int[] dp = new int[n + 1];
        dp[0] = dp[1] = 1;

        for(int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n];
    }

    // tabulation approach
    public static boolean wordBreak(String s, List<String> wordDict) {
        boolean[] dp = new boolean[s.length()];
        for (int i  = 0 ; i < s.length(); i++) {
            for (String word : wordDict) {
                if (i < word.length() - 1) continue;
                int start = i - word.length();

                if (i == word.length() - 1 || dp[start]) {
                    if (s.substring(start + 1, i + 1).equals(word)) {
                        dp[i] = true;
                        break;
                    }
                }
            }
        }

        return dp[s.length() - 1];
    }
}
