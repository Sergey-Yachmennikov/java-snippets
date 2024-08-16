package data_structures;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MinHeapTest {

    @Test
    void minHeap() {
        MinHeap minHeap = new MinHeap(7);
        minHeap.insert(3);
        minHeap.insert(13);
        minHeap.insert(7);
        minHeap.insert(16);
        minHeap.insert(21);
        minHeap.insert(12);
        minHeap.insert(9);
        minHeap.minHeap();

        System.out.println("The Min Heap is : " + Arrays.toString(minHeap.getHeap()));
        minHeap.printHeap();
        System.out.println("\nThe Min Value is : " + minHeap.remove());
        System.out.println("\nThe Min Heap is :"+ Arrays.toString(minHeap.getHeap()));
        minHeap.printHeap();
    }

}