package sorting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void shellSortTest() {
        SortingUtil.shellSort(array);
        System.out.println(Arrays.toString(array));
    }

    @Test
    void topologicalSort() {
        int n = 6;
        // Edges
        int[][] edges = { { 0, 1 }, { 1, 2 }, { 2, 3 }, { 4, 5 }, { 5, 1 }, { 5, 2 } };

        // Graph represented as an adjacency list
        List<List<Integer>> adjList = new ArrayList<>();
        for (int i = 0; i < n; i++) adjList.add(new ArrayList<>());

        // Constructing adjacency list
        for (int[] edge : edges) adjList.get(edge[0]).add(edge[1]);

        assertArrayEquals(new int[] {0, 4, 5, 1, 2, 3}, SortingUtil.topologicalSort(adjList, 6));
    }
}