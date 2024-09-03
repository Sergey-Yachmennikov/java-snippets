package leetcode_tasks;

import java.util.HashMap;
import java.util.Map;

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

    public static int findLongestHarmoniousSubsequence(int[] nums) {
        int count = 0;
        HashMap<Integer,Integer> map = new HashMap<>();

        for(int val: nums) {
            if(map.containsKey(val)){
                map.put(val, map.get(val)+1);
            } else {
                map.put(val,1);
            }
        }

        for(int val: nums) {
            if(map.containsKey(val + 1)){
                int freq = map.get(val) + map.get(val + 1);
                count = Math.max(count, freq);
            }
        }

        return count;
    }

    public static int lengthOfTheLongestSubstringTwoDistinct(String s) {
        char[] arr = s.toCharArray();
        int n = arr.length;
        if (n < 3) return n;
        int L = 0;
        int R = 0;
        Map<Character, Integer> map = new HashMap<>();
        int maxLength = 0;

        while (R < n) {
            map.put(arr[R], map.getOrDefault(arr[R], 0) + 1); // increment count of repetition of each value

            while (map.size() > 2) { // if map contains more than 2 keys, decrement the very first char repetition count
                map.put(arr[L], map.get(arr[L]) - 1);
                if (map.get(arr[L]) == 0) {
                    map.remove(arr[L]);
                }
                L++;
            }

            maxLength = Math.max(maxLength, R - L + 1);
            R++;
        }

        return maxLength;
    }

    public static int lengthOfTheLongestSubstringWithoutRepeatingCharacters(String s) {
        char[] arr = s.toCharArray();
        int n = arr.length;
        if (n < 2) return n;
        int L = 0;
        int R = 0;
        Map<Character, Integer> map = new HashMap<>();
        int maxLength = 0;

        while (R < n) {
            map.put(arr[R], map.getOrDefault(arr[R], 0) + 1);

            while (map.size() != R - L + 1) {
                map.put(arr[L], map.get(arr[L]) - 1);
                if (map.get(arr[L]) == 0) {
                    map.remove(arr[L]);
                }
                L++;
            }

            maxLength = Math.max(maxLength, R - L + 1);
            R++;
        }

        return maxLength;
    }
}
