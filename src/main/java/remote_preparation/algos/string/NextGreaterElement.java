package remote_preparation.algos.string;

import java.util.Arrays;
import java.util.Stack;

public class NextGreaterElement {

    public static void main(String[] args) {
        int[] nums1 = {2, 1, 2, 4, 3};
        int[] nums2 = {1, 2, 3, 4, 5};
        int[] nums3 = {5, 4, 3, 2, 1};

        System.out.println(Arrays.toString(nextGreaterElement(nums1)));
        System.out.println(Arrays.toString(nextGreaterElement(nums2)));
        System.out.println(Arrays.toString(nextGreaterElement(nums3)));
    }

    public static int[] nextGreaterElement(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new int[0];
        }

        int n = nums.length;
        int[] result = new int[n];
        Arrays.fill(result, -1);

        Stack<Integer> stack = new Stack<>();

        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && stack.peek() <= nums[i]) {
                stack.pop();
            }

            if (!stack.isEmpty()) {
                result[i] = stack.peek();
            }

            stack.push(nums[i]);
        }

        return result;
    }
}
