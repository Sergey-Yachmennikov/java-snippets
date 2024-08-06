package tasks.search;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SearchBasicAlgorithmsTest {

    int[] array;

    @BeforeEach
    void setUp () {
        array = new int[] {11, 22, 33, 44, 55, 66, 77, 88, 99, 100, 101, 102, 103};
    }

    @Test
    void binarySearchRecursiveTest() {
        assertEquals(3, SearchBasicAlgorithms.binarySearchRecursive(array, 0, array.length, 44));
        assertEquals(-1, SearchBasicAlgorithms.binarySearchRecursive(array, 0, array.length, 7));
    }

    @Test
    void binarySearchTest() {
        assertEquals(4, SearchBasicAlgorithms.binarySearch(array, 55));
        assertEquals(-1, SearchBasicAlgorithms.binarySearch(array, 200));
    }
}