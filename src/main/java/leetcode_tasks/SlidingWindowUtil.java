package leetcode_tasks;

public class SlidingWindowUtil {

    private SlidingWindowUtil() {}

    public static double findMaxAverage(int[] nums, int k) {
        int sum = 0;
        for (int i = 0; i < k; i++) sum += nums[i];
        int preResult = sum;

        for (int i = k; i < nums.length; i++) {
            sum += nums[i] - nums[i - k];
            preResult = Math.max(preResult, sum);
        }

        return (double) preResult / k;
    }

    public static int minSubArrayLength(int target, int[] nums) {
        int left = 0;
        int sumOfCurrentWindow = 0;
        int result = Integer.MAX_VALUE;

        for (int right = 0; right < nums.length; right++) {
            sumOfCurrentWindow += nums[right];

            while(sumOfCurrentWindow >= target) {
                result = Math.min(result, right - left + 1);
                sumOfCurrentWindow -= nums[left++];
            }
        }

        return result == Integer.MAX_VALUE ? 0 : result;
    }
}
