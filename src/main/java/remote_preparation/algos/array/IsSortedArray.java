package remote_preparation.algos.array;


public class IsSortedArray {

    public static boolean isSorted(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] > nums[i]) {
                return false;
            }
        }
        return true;
    }

    /** Аналогично, но по невозрастанию. */
    public static boolean isSortedDesc(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] < nums[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        check(isSorted(new int[]{}), true, "isSorted: пустой массив");
        check(isSorted(new int[]{5}), true, "isSorted: один элемент");
        check(isSorted(new int[]{1, 2, 2, 3}), true, "isSorted: неубывающий с повтором");
        check(isSorted(new int[]{1, 3, 2}), false, "isSorted: нарушение порядка");
        check(isSorted(new int[]{-3, -1, 0, 5}), true, "isSorted: с отрицательными");

        check(isSortedDesc(new int[]{}), true, "isSortedDesc: пустой массив");
        check(isSortedDesc(new int[]{5}), true, "isSortedDesc: один элемент");
        check(isSortedDesc(new int[]{5, 3, 3, 1}), true, "isSortedDesc: невозрастающий с повтором");
        check(isSortedDesc(new int[]{5, 1, 3}), false, "isSortedDesc: нарушение порядка");

        System.out.println("Все проверки пройдены");
    }

    private static void check(boolean actual, boolean expected, String label) {
        if (actual != expected) {
            throw new AssertionError(label + ": ожидали " + expected + ", получили " + actual);
        }
        System.out.println("OK  " + label);
    }
}
