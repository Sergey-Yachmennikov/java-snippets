package remote_preparation.algos.string;

public class LongestOnes {

    static int longestOnes(int[] nums, int k) {
        int left = 0;
        int zeros = 0;
        int best = 0;

        for (int right = 0; right < nums.length; right++) {
            if (nums[right] == 0) {
                zeros++;
            }

            while (zeros > k) {
                if (nums[left] == 0) {
                    zeros--;
                }
                left++;
            }

            best = Math.max(best, right - left + 1);
        }

        return best;
    }

    public static void main(String[] args) {
        check(longestOnes(new int[]{1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0}, 2), 6,
                "пример из условия");
        check(longestOnes(new int[]{0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1}, 3), 10,
                "второй классический пример LeetCode 1004");
        check(longestOnes(new int[]{1, 1, 1, 1}, 0), 4, "нулей нет вообще, k=0");
        check(longestOnes(new int[]{0, 0, 0}, 0), 0, "все нули, замен нет");
        check(longestOnes(new int[]{0, 0, 0}, 5), 3, "k больше числа нулей — весь массив");
        check(longestOnes(new int[]{}, 2), 0, "пустой массив");
        check(longestOnes(new int[]{0}, 0), 0, "один ноль, k=0");
        check(longestOnes(new int[]{1}, 0), 1, "одна единица, k=0");

        System.out.println("Все проверки пройдены");
    }

    private static void check(int actual, int expected, String label) {
        if (actual != expected) {
            throw new AssertionError(label + ": ожидали " + expected + ", получили " + actual);
        }
        System.out.println("OK  " + label);
    }
}
