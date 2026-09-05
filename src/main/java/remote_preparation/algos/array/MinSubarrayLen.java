package remote_preparation.algos.array;

// @repeat @dynamicSlidingWindow
public class MinSubarrayLen {

    static int minSubarrayLen(int target, int[] nums) {
        if (target <= 0) {
            return 0;
        }

        int left = 0;
        int sum = 0;
        int best = Integer.MAX_VALUE;

        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];

            while (sum >= target) {
                best = Math.min(best, right - left + 1);
                sum -= nums[left];
                left++;
            }
        }

        return best == Integer.MAX_VALUE ? 0 : best;
    }

    public static void main(String[] args) {
        check(minSubarrayLen(7, new int[]{2, 3, 1, 2, 4, 3}), 2, "пример из условия: [4,3]");
        check(minSubarrayLen(11, new int[]{1, 1, 1, 1, 1, 1, 1, 1}), 0, "сумма всего массива меньше target");
        check(minSubarrayLen(4, new int[]{1, 4, 4}), 1, "один элемент уже удовлетворяет");
        check(minSubarrayLen(100, new int[]{1, 2, 3}), 0, "target недостижим");
        check(minSubarrayLen(15, new int[]{1, 2, 3, 4, 5}), 5, "нужен весь массив");
        check(minSubarrayLen(3, new int[]{1, 1}), 0, "сумма массива меньше target на границе");
        check(minSubarrayLen(1, new int[]{1}), 1, "один элемент, ровно равен target");
        check(minSubarrayLen(0, new int[]{1, 2}), 0,
                "target <= 0: вырожденный случай, без явной проверки уводит left за границы массива");
        check(minSubarrayLen(-5, new int[]{1, 2}), 0, "отрицательный target — та же вырожденность");

        System.out.println("Все проверки пройдены");
    }

    private static void check(int actual, int expected, String label) {
        if (actual != expected) {
            throw new AssertionError(label + ": ожидали " + expected + ", получили " + actual);
        }
        System.out.println("OK  " + label);
    }
}