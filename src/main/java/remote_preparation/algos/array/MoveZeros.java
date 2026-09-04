package remote_preparation.algos.array;

import java.util.Arrays;

// Перемещение нулей»
// Напиши функцию `MoveZeroes(nums []int)`, которая перемещает все нули в конец массива,
// сохраняя порядок остальных элементов. Модифицируй срез на месте без дополнительного среза.
// Пример: `[0,1,0,3,12]` -> `[1,3,12,0,0]`
public class MoveZeros {

    public static void main(String[] args) {
        int[] nums = {0, 1, 0, 3, 12};

        moveZeroes(nums);

        System.out.println(Arrays.toString(nums));
    }

    public static void moveZeroes(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }

        int write = 0;

        for (int read = 0; read < nums.length; read++) {
            if (nums[read] != 0) {
                swap(nums, write, read);
                write++;
            }
        }
    }

    private static void swap(int[] nums, int first, int second) {
        int temp = nums[first];
        nums[first] = nums[second];
        nums[second] = temp;
    }
}