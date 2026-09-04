package remote_preparation.algos.array;

import java.util.Arrays;
// @repeat
public class RemoveDuplicatesInSortesArray {

    public static void main(String[] args) {
        int[] nums = {1, 1, 2, 3, 3};

        int length = removeDuplicates(nums);

        System.out.println("Новая длина: " + length);
        System.out.println("Уникальные элементы: " + Arrays.toString(Arrays.copyOf(nums, length)));

        // Весь исходный массив может выглядеть так:
        System.out.println("Исходный массив: " + Arrays.toString(nums));
    }

    public static int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int write = 1;

        for (int read = 1; read < nums.length; read++) {
            if (nums[read] != nums[write - 1]) {
                nums[write] = nums[read];
                write++;
            }
        }

        return write;
    }
}
