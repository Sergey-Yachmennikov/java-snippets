package remote_preparation.algos.prefix_sum;

import java.util.HashMap;
import java.util.Map;

public class SubarraySum {

    static int subarraySum(int[] nums, int k) {
        Map<Integer, Integer> freq = new HashMap<>();
        freq.put(0, 1); // префикс "до начала" — та же роль, что seen.add(0) в задаче 35

        int prefix = 0;
        int count = 0;

        for (int num : nums) {
            prefix += num;
            count += freq.getOrDefault(prefix - k, 0);
            freq.merge(prefix, 1, Integer::sum);
        }

        return count;
    }

    public static void main(String[] args) {
        check(subarraySum(new int[]{1, 1, 1}, 2), 2, "пример из условия: [1,1] дважды");
        check(subarraySum(new int[]{1, 2, 3}, 3), 2, "[1,2] и [3]");
        check(subarraySum(new int[]{1, 1, 1, 1}, 2), 3, "три пары соседних единиц");
        check(subarraySum(new int[]{}, 0), 0, "пустой массив");
        check(subarraySum(new int[]{0, 0, 0}, 0), 6, "все C(4,2)=6 подмассивов из нулей суммируются в 0");
        check(subarraySum(new int[]{-1, -1, 1}, 0), 1, "с отрицательными числами");
        check(subarraySum(new int[]{1}, 1), 1, "один элемент, совпадает с k");
        check(subarraySum(new int[]{1}, 0), 0, "один элемент, не совпадает с k");
        check(subarraySum(new int[]{3, 4, 7, 2, -3, 1, 4, 2}, 7), 4, "смешанный массив");

        System.out.println("Все проверки пройдены");
    }

    private static void check(int actual, int expected, String label) {
        if (actual != expected) {
            throw new AssertionError(label + ": ожидали " + expected + ", получили " + actual);
        }
        System.out.println("OK  " + label);
    }
}
