package leetcode_tasks.dynamic_programming;

import java.util.Arrays;
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

    public static int minCostClimbingStairs(int[] cost) {
        int n = cost.length;
        return Math.min(minCost(cost, n - 1), minCost(cost, n - 2));
    }

    private static int minCost(int[] cost, int n) {
        if (n < 0) return 0;
        if (n == 0 || n == 1) return cost[n];
        return cost[n] + Math.min(minCost(cost, n - 1), minCost(cost, n - 2));
    }

    public static int minCostClimbingStairsIterative(int[] cost) {
        int n = cost.length;
        int[] dp = new int[n];
        for (int i = 0; i < n; i++) {
            if (i < 2) dp[i] = cost[i];
            else dp[i] = cost[i] + Math.min(dp[i - 1], dp[i - 2]);
        }

        return Math.min(dp[n - 1], dp[n - 2]);
    }

    public static int minCostClimbingStairsIterativeOptimized(int[] cost) {
        int first = cost[0];
        int second = cost[1];
        for (int i = 2; i < cost.length; i++) {
            int current = cost[i] + Math.min(first, second);
            first = second;
            second = current;
        }

        return Math.min(first, second);
    }

    // Kadane’s Algorithm
    public static int findMaxSubarraySum(int[] numbers) {
        int currentSum = 0;
        int maxSum = numbers[0];

        for (int num : numbers) {
            currentSum = Math.max(currentSum + num, num);
            maxSum = Math.max(maxSum, currentSum);
        }

        return maxSum;
    }

    // brute force solution for previous one
    public static int findMaxSubarraySumBruteForce(int[] numbers) {
        int maxSum = numbers[0];
        for (int i = 0; i < numbers.length; i++) {

            int subarraySum = 0;
            for (int j = i; j < numbers.length; j++) {
                subarraySum = subarraySum + numbers[j];
                maxSum = Math.max(maxSum, subarraySum);
            }
        }

        return maxSum;
    }

    public static int maxProduct(int[] nums) {
        int n = nums.length;
        int l = 1;
        int r = 1;
        int outcome = nums[0];

        for (int i = 0; i < n; i++) {
            l = l == 0 ? 1 : l;
            r = r == 0 ? 1 : r;

            l *= nums[i];
            r *= nums[n - 1 - i];

            outcome = Math.max(outcome, Math.max(l, r));
        }

        return outcome;
    }

    public static boolean canPartition(int[] nums) {
        int sum = Arrays.stream(nums)
                .reduce(0, Integer::sum);

        if (sum % 2 != 0) return false;
        boolean[][] dp = new boolean[nums.length + 1][(sum / 2) + 1];
        for (int i = 0; i < nums.length; i++) dp[i][0] = true;

        for (int i = nums.length - 1; i >= 0; i--) {
            for (int j = 1; j <= sum / 2; j++) {
                if (j - nums[i] >= 0) dp[i][j] = dp[i + 1][j - nums[i]];
                dp[i][j] = dp[i][j] || dp[i + 1][j];
            }
        }

        return dp[0][sum / 2];
    }
}
