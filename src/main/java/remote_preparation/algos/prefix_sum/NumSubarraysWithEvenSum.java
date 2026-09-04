package remote_preparation.algos.prefix_sum;

import java.util.HashMap;
import java.util.Map;

public class NumSubarraysWithEvenSum {

    static int numSubarraysWithEvenSum(int[] nums) {
        // freq[0] — сколько раз встречался чётный префикс, freq[1] — нечётный
        int[] freq = new int[2];
        freq[0] = 1; // prefix[0] = 0, до начала обхода

        int prefix = 0;
        int count = 0;

        for (int num : nums) {
            prefix += num;
            int parity = Math.floorMod(prefix, 2); // floorMod, а не %, из-за отрицательных чисел
            count += freq[parity];
            freq[parity]++;
        }

        return count;
    }

    public static void main(String[] args) {
        check(numSubarraysWithEvenSum(new int[]{1, 2, 3, 4}), 4, "классический пример LeetCode 1524");
        check(numSubarraysWithEvenSum(new int[]{}), 0, "пустой массив");
        check(numSubarraysWithEvenSum(new int[]{2, 4, 6}), 6, "все элементы чётные: все C(3+1,2) подмассивов чётны");
        check(numSubarraysWithEvenSum(new int[]{1, 1}), 1, "только сумма [1,1]=2 чётна");
        check(numSubarraysWithEvenSum(new int[]{2}), 1, "один чётный элемент");
        check(numSubarraysWithEvenSum(new int[]{1}), 0, "один нечётный элемент");
        check(numSubarraysWithEvenSum(new int[]{-1, -2, -3}), 2,
                "отрицательные числа: [-1] нечёт, [-1,-2] нечёт, [-1,-2,-3] чёт, [-2] чёт, [-2,-3] нечёт, [-3] нечёт");

        System.out.println("Все проверки пройдены");
    }

    private static void check(int actual, int expected, String label) {
        if (actual != expected) {
            throw new AssertionError(label + ": ожидали " + expected + ", получили " + actual);
        }
        System.out.println("OK  " + label);
    }
}
