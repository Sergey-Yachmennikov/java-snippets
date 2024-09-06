package leetcode_tasks.prefix_sum;

public class PrefixSumUtil {

    private PrefixSumUtil() {}

    public static class NumArray {
        private final int[] prefix;

        NumArray(int[] nums) {
            prefix = new int[nums.length];
            prefix[0] = nums[0];
            for (int i = 1; i < nums.length; i++) {
                prefix[i] = prefix[i - 1] + nums[i];
            }
        }

        public int sumRange(int left, int right) {
            if (left == 0) return prefix[right];
            return prefix[right] - prefix[left - 1];
        }
    }
}
