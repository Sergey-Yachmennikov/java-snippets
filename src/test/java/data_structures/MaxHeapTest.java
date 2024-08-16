package data_structures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MaxHeapTest {

    @Test
    void maxHeapTest() {
        System.out.println("The Max Heap is ");
        MaxHeap maxHeap = new MaxHeap(20);

        maxHeap.add(15);
        maxHeap.add(13);
        maxHeap.add(7);
        maxHeap.add(5);
        maxHeap.add(52);
        maxHeap.add(23);
        maxHeap.add(16);
        maxHeap.add(9);
        maxHeap.add(21);


        assertEquals(52, maxHeap.poll());
        assertEquals(23, maxHeap.poll());
        assertEquals(21, maxHeap.poll());
        assertEquals(16, maxHeap.poll());
        assertEquals(15, maxHeap.poll());
        assertEquals(13, maxHeap.poll());
        System.out.println(maxHeap);
    }

}