package remote_preparation.algos.array;

public class MaxSubarraySum {

    static int maxSubarraySum(int[] nums, int k) {
        if (k <= 0 || k > nums.length) {
            throw new IllegalArgumentException(
                    "k должен быть в диапазоне (0, nums.length], k=" + k + ", length=" + nums.length);
        }

        // Первое окно считаем честной суммой k элементов — базовый случай,
        // с которого начинается скольжение
        int windowSum = 0;
        for (int i = 0; i < k; i++) {
            windowSum += nums[i];
        }

        int maxSum = windowSum;

        // Сдвигаем окно: i — индекс элемента, ВХОДЯЩЕГО справа.
        // Элемент, ПОКИДАЮЩИЙ окно слева, — это nums[i - k]
        for (int i = k; i < nums.length; i++) {
            windowSum += nums[i] - nums[i - k];
            maxSum = Math.max(maxSum, windowSum);
        }

        return maxSum;
    }

    public static void main(String[] args) {
        check(maxSubarraySum(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3), 16,
                "пример из условия: окно [5,3,6]");
        check(maxSubarraySum(new int[]{2, 1, 5, 1, 3, 2}, 3), 9, "окно [5,1,3]");
        check(maxSubarraySum(new int[]{-1, -2, -3}, 2), -3, "все отрицательные");
        check(maxSubarraySum(new int[]{5}, 1), 5, "один элемент, k=1");
        check(maxSubarraySum(new int[]{1, 2, 3}, 3), 6, "k равен длине массива");
        check(maxSubarraySum(new int[]{4, -1, 4, -1, 4}, 1), 4, "k=1: ищем просто максимум");

        try {
            maxSubarraySum(new int[]{1, 2}, 3);
            throw new AssertionError("ожидали исключение при k > длины массива");
        } catch (IllegalArgumentException e) {
            System.out.println("OK  k больше длины массива корректно отвергается");
        }

        try {
            maxSubarraySum(new int[]{1, 2, 3}, 0);
            throw new AssertionError("ожидали исключение при k = 0");
        } catch (IllegalArgumentException e) {
            System.out.println("OK  k = 0 корректно отвергается");
        }

        System.out.println("Все проверки пройдены");
    }

    private static void check(int actual, int expected, String label) {
        if (actual != expected) {
            throw new AssertionError(label + ": ожидали " + expected + ", получили " + actual);
        }
        System.out.println("OK  " + label);
    }
}
