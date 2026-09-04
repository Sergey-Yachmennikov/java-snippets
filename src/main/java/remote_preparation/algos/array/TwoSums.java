package remote_preparation.algos.array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

// Задача 1 — «Two Sum»
// Напиши функцию TwoSum(nums []int, target int) []int, которая возвращает индексы двух чисел,
// сумма которых равна target. Используй map[int]int для решения за O(n).
public class TwoSums {

    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15};
        int target = 9;

        int[] result = twoSum(nums, target);

        System.out.println(Arrays.toString(result));
    }

    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> indexes = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int current = nums[i];
            int complement = target - current;

            if (indexes.containsKey(complement)) {
                return new int[] {indexes.get(complement), i};
            }

            indexes.put(current, i);
        }

        return new int[0];
    }
}
