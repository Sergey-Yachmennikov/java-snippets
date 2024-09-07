package leetcode_tasks.prefix_sum;

import java.util.Arrays;
import java.util.HashMap;

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
        HashMap<Integer, Integer> map = new HashMap<>();
        int sum = 0;
        map.put(0, -1);
        int max = 0;

        for(int i = 0; i < nums.length; i++){
            sum += (nums[i] == 0 ? -1 : 1);
            if(map.containsKey(sum)) {
                max = Math.max(max, i - map.get(sum));
            } else
                map.put(sum, i);
        }

        return max;
    }
}
