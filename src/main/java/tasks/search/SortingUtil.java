package tasks.search;

public class SortingUtil {

    public static void bubbleSort(int[] array) {
        boolean isSorted = false;
        while (!isSorted) {
            isSorted = true;
            for (int i = 1; i < array.length; i++) {
                if (array[i] < array[i - 1]) {
                    swap(array, i, i - 1);
                    isSorted = false;
                }
            }
        }
    }

    public static void selectionSort(int[] array) {
        for (int step = 0; step < array.length; step++) {
            int minIndex = min(array, step);
            swap(array, step, minIndex);
        }
    }

    private static int min(int[] array, int startIndex) {
        int minElement = array[startIndex];
        int minIndex = startIndex;
        for (int i = startIndex + 1; i < array.length; i++) {
            if (array[i] < minElement) {
                minElement = array[i];
                minIndex = i;
            }
        }

        return minIndex;
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
