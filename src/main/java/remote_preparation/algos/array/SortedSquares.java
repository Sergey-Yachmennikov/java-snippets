package remote_preparation.algos.array;

import java.util.Arrays;

public class SortedSquares {

    public static void main(String[] args) {
        int[] nums1 = {-4, -1, 0, 3, 10};
        int[] nums2 = {-7, -3, 2, 3, 11};
        int[] nums3 = {-3, -2, -1};

        System.out.println(Arrays.toString(sortedSquares(nums1)));
        System.out.println(Arrays.toString(sortedSquares(nums2)));
        System.out.println(Arrays.toString(sortedSquares(nums3)));
    }

    public static int[] sortedSquares(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new int[0];
        }

        int n = nums.length;
        int[] result = new int[n];

        int left = 0;
        int right = n - 1;
        int pos = n - 1;

        while (left <= right) {
            int leftSq = nums[left] * nums[left];
            int rightSq = nums[right] * nums[right];

            if (leftSq > rightSq) {
                result[pos] = leftSq;
                left++;
            } else {
                result[pos] = rightSq;
                right--;
            }

            pos--;
        }

        return result;
    }
}
