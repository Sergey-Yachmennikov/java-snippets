package remote_preparation.algos.prefix_sum;

import java.util.HashSet;
import java.util.Set;

public class HasZeroSumSubarray {

    static boolean hasZeroSumSubarray(int[] nums) {
        Set<Integer> seen = new HashSet<>();
        seen.add(0); // префикс "до начала" — покрывает случай без отдельной ветки кода

        int prefix = 0;
        for (int num : nums) {
            prefix += num;
            if (!seen.add(prefix)) {
                // add() вернул false -> такое значение префикса уже было
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        check(hasZeroSumSubarray(new int[]{4, 2, -3, 1, 6}), true, "2 + (-3) + 1 = 0");
        check(hasZeroSumSubarray(new int[]{4, 2, 0, 1, 6}), true, "сам ноль как элемент массива");
        check(hasZeroSumSubarray(new int[]{1, 2, 3}), false, "все положительные, нулевой суммы нет");
        check(hasZeroSumSubarray(new int[]{}), false, "пустой массив");
        check(hasZeroSumSubarray(new int[]{0}), true, "единственный элемент — ноль");
        check(hasZeroSumSubarray(new int[]{1}), false, "единственный элемент — не ноль");
        check(hasZeroSumSubarray(new int[]{-1, 1}), true, "два элемента, взаимно гасят друг друга");
        check(hasZeroSumSubarray(new int[]{5, -5, 5, -5}), true, "несколько вложенных нулевых подмассивов");
        check(hasZeroSumSubarray(new int[]{3, 4, -7, 3, 1, 3, 1, -4}), true, "длинный массив с нулевым подмассивом в середине");

        System.out.println("Все проверки пройдены");
    }

    private static void check(boolean actual, boolean expected, String label) {
        if (actual != expected) {
            throw new AssertionError(label + ": ожидали " + expected + ", получили " + actual);
        }
        System.out.println("OK  " + label);
    }
}
