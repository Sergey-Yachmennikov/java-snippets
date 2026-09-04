package remote_preparation.algos.array;

import java.util.Arrays;

// Разворот массива на месте
// Напиши функцию ReverseInPlace(nums []int), которая разворачивает
// срез без выделения дополнительной памяти. Используй два указателя — left и right.
// Объясни: почему срез изменится в вызывающем коде, а не его копия?
// Пример: [1,2,3,4,5] -> [5,4,3,2,1]
public class ReverseArray {

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 5};

        reverseInPlace(nums);

        System.out.println(Arrays.toString(nums));
    }

    public static void reverseInPlace(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }

        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;

            left++;
            right--;
        }
    }
}
