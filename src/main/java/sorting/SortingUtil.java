package sorting;

public class SortingUtil {

    private SortingUtil() {}

    // public API
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

    public static void quickSort(int[] array, int from, int to) {
        if (from < to) {
            int middleIndex = partition(array, from ,to);
            quickSort(array, from, middleIndex - 1);
            quickSort(array, middleIndex, to);
        }
    }

    public static void mergeSort(int[] array) {
        int[] temp,
              currentSrc = array,
              currentDest = new int[array.length];

        int size = 1; // size of merge arrays
        while (size < array.length) {

            for (int i = 0; i < array.length; i += 2 * size) {
                merge(currentSrc, i, currentSrc, i + size, currentDest, i, size);
            }

            temp = currentSrc;
            currentSrc = currentDest;
            currentDest = temp;
            size = size * 2;
        }
    }

    public static void countingSort(int[] array, final int maxValue) { //
        int[] count = new int[maxValue + 1];

        for (int value : array) {
            count[value] += 1;
        }

        int arrayIndex = 0;
        for (int i = 0; i < count.length; i++) {
            for (int j = 0; j < count[i]; j++) {
                array[arrayIndex] = i;
                arrayIndex++;
            }
        }
    }

    public static void insertionSort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            int current = array[i];
            int j = i;

            while (j > 0 && array[j - 1] > current) {
                array[j] = array[j - 1];
                j--;
            }

            array[j] = current;
        }
    }

    public static void combSort(int[] array) {
        int gap = array.length;
        boolean isSorted = false;
        while (!isSorted || gap != 1) {

            gap = gap > 1 ? gap * 10 / 13 : 1;
            isSorted = true;

            for (int i = gap; i < array.length; i++) {
                if (array[i] < array[i - gap]) {
                    swap(array, i, i - gap);
                    isSorted = false;
                }
            }
        }
    }

    public static void shellSort(int[] array) {
        for (int gap = array.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < array.length; i++) {
                int key = array[i];
                int j = i;
                while (j >= gap && array[j - gap] > key) {
                    array[j] = array[j - gap];
                    j -= gap;
                }
                array[j] = key;
            }
        }
    }

    // internal part
    private static int partition(int[] array, int from, int to) {
        int leftIndex = from;
        int rightIndex = to;
        int pivot = array[from];

        while (leftIndex <= rightIndex) {

            while (array[leftIndex] < pivot) {
                leftIndex++;
            }

            while (array[rightIndex] > pivot) {
                rightIndex--;
            }

            if (leftIndex <= rightIndex) {
                swap(array, leftIndex, rightIndex);
                leftIndex++;
                rightIndex--;
            }
        }

        return leftIndex;
    }

    private static void merge(int[] src1, int src1Start, int[] src2, int src2Start, int[] dest, int destStart, int size) {
        int index1 = src1Start,
            index2 = src2Start,
            src1End = Math.min(src1Start + size, src1.length),
            src2End = Math.min(src2Start + size, src2.length),
            iterationCount = src1End - src1Start + src2End - src2Start;

        for (int i = destStart; i < destStart + iterationCount; i++) {
            if (index1 < src1End && (index2 >= src2End || src1[index1] < src2[index2])) {
                dest[i] = src1[index1];
                index1++;
            } else {
                dest[i] = src2[index2];
                index2++;
            }
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
        array[i] = array[i] ^ array[j];
        array[j] = array[i] ^ array[j];
        array[i] = array[i] ^ array[j];
    }
}
