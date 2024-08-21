package leetcode_tasks;

public class RotateArray {

    private RotateArray() {}

    public static void rotate(int[] array, int k) {
        int n = array.length;
        k = k % n; // in case where k > array.length
        reverse(array, 0, n - 1);
        reverse(array, 0, k - 1);
        reverse(array, k, n - 1);
    }

    private static void reverse(int[] array, int start, int end) {
        while (start < end) {
            swap(array, start, end);
            start += 1;
            end -= 1;
        }
    }

    private static void swap(int[] array, int i, int j) {
        array[i] = array[i] ^ array[j];
        array[j] = array[i] ^ array[j];
        array[i] = array[i] ^ array[j];
    }
}
