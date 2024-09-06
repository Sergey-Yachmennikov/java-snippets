package leetcode_tasks.prefix_sum;

import java.util.Arrays;

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

    public static int findMaxLength(int[] nums) {
        int[] indices = new int[nums.length * 2 + 1];
        Arrays.fill(indices, -2);
        indices[nums.length] = -1;
        int count = 0;
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            count += nums[i] == 0 ? -1 : 1;
            int last = indices[nums.length - count];
            if (last >= -1) {
                max = Math.max(max, i - last);
            } else {
                indices[nums.length - count] = i;
            }
        }

        return max;
    }
}
