package sorting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class SortingUtilTest {
    int[] array;

    @BeforeEach
    void setUp () {
        array = new int[] {64, 42, 73, 41, 31, 53, 16, 24, 57, 42, 74, 55, 36};
    }

    @Test
    void bubbleSortTest() {
        SortingUtil.bubbleSort(array);
        System.out.println(Arrays.toString(array));
    }

    @Test
    void selectionSortTest() {
        SortingUtil.selectionSort(array);
        System.out.println(Arrays.toString(array));
    }

    @Test
    void quickSortTest() {
        SortingUtil.quickSort(array, 0, array.length - 1);
        System.out.println(Arrays.toString(array));
    }

    @Test
    void mergeSortTest() {
        SortingUtil.mergeSort(array);
        System.out.println(Arrays.toString(array));
    }

    @Test
    void countingSortTest() {
        SortingUtil.countingSort(array, Arrays.stream(array).max().orElse(0));
        System.out.println(Arrays.toString(array));
    }

    @Test
    void insertionSortTest() {
        SortingUtil.insertionSort(array);
        System.out.println(Arrays.toString(array));
    }

    @Test
    void combSortTest() {
        SortingUtil.combSort(array);
        System.out.println(Arrays.toString(array));
    }

    // shellSort
    @Test
    void shellSortTest() {
        SortingUtil.shellSort(array);
        System.out.println(Arrays.toString(array));
    }
}