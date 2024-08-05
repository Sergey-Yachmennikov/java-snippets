package tasks.search;

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
}