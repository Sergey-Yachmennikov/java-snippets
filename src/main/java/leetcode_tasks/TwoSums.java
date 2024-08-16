package leetcode_tasks;

/**
 * @topic Arrays
 */
public class TwoSums {

    public int[] twoSums(int[] arr, int target) {
        int pointerA = 0;
        int pointerB = arr.length - 1;

        while (pointerA <= pointerB) {
            int sum = arr[pointerA] + arr[pointerB];

            if (sum > target) {
                pointerB -= 1;
            } else if (sum < target){
                pointerA += 1;
            } else {
                return new int[]{pointerA + 1, pointerB + 1};
            }
        }

        return new int[]{pointerA + 1, pointerB + 1};
    }
}
