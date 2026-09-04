package remote_preparation.algos.prefix_sum;

import java.util.Arrays;

public class RangeSum {

    static int[] rangeSum(int[] nums, int[][] queries) {
        int[] prefix = buildPrefixSums(nums);

        int[] answers = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int left = queries[i][0];
            int right = queries[i][1];
            validateRange(left, right, nums.length);
            answers[i] = prefix[right + 1] - prefix[left];
        }
        return answers;
    }

    /** Отдельная функция — переиспользуема, если понадобится сама структура префиксов. */
    static int[] buildPrefixSums(int[] nums) {
        int[] prefix = new int[nums.length + 1];
        for (int i = 0; i < nums.length; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }
        return prefix;
    }

    private static void validateRange(int left, int right, int n) {
        if (left < 0 || right >= n || left > right) {
            throw new IllegalArgumentException(
                    "Некорректный диапазон [" + left + ", " + right + "] для массива длины " + n);
        }
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 5};

        check(rangeSum(nums, new int[][]{{0, 2}, {1, 3}, {0, 4}}),
                new int[]{6, 9, 15}, "пример: суммы диапазонов");

        check(rangeSum(nums, new int[][]{{0, 0}}), new int[]{1}, "диапазон из одного элемента слева");
        check(rangeSum(nums, new int[][]{{4, 4}}), new int[]{5}, "диапазон из одного элемента справа");
        check(rangeSum(nums, new int[][]{{0, 4}}), new int[]{15}, "весь массив целиком");
        check(rangeSum(new int[]{-1, -2, 3}, new int[][]{{0, 2}}), new int[]{0}, "с отрицательными числами");
        check(rangeSum(new int[]{5}, new int[][]{{0, 0}}), new int[]{5}, "массив из одного элемента");
        check(rangeSum(nums, new int[][]{}), new int[]{}, "нет запросов");

        try {
            rangeSum(nums, new int[][]{{2, 1}});
            throw new AssertionError("ожидали исключение при left > right");
        } catch (IllegalArgumentException e) {
            System.out.println("OK  left > right корректно отвергается");
        }

        try {
            rangeSum(nums, new int[][]{{0, 5}});
            throw new AssertionError("ожидали исключение при right за границей массива");
        } catch (IllegalArgumentException e) {
            System.out.println("OK  right за границей массива корректно отвергается");
        }

        try {
            rangeSum(nums, new int[][]{{-1, 2}});
            throw new AssertionError("ожидали исключение при отрицательном left");
        } catch (IllegalArgumentException e) {
            System.out.println("OK  отрицательный left корректно отвергается");
        }

        System.out.println("Все проверки пройдены");
    }

    private static void check(int[] actual, int[] expected, String label) {
        if (!Arrays.equals(actual, expected)) {
            throw new AssertionError(label + ": ожидали " + Arrays.toString(expected)
                    + ", получили " + Arrays.toString(actual));
        }
        System.out.println("OK  " + label);
    }
}
