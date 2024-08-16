package data_structures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BinaryMaxHeapTest {

    @Test
    void binaryMaxHeapTest() {
        BinaryMaxHeap heap = new BinaryMaxHeap(15);

        heap.insert(1);
        heap.insert(2);
        heap.insert(3);
        heap.insert(4);
        heap.insert(5);
        heap.insert(6);
        heap.insert(7);
        heap.insert(8);
        heap.insert(9);
        heap.insert(10);
        heap.insert(11);
        heap.insert(12);
        heap.insert(13);
        heap.insert(14);
        heap.insert(15);

        System.out.println("AS A TREE:");
        BinaryHeapUtil.displayTree(heap.getHeap(), heap.getSize());
        System.out.println("AS ARRAY: " + heap);

        assertEquals(15, heap.remove());
        assertEquals(0, heap.getHeap()[14]);

        assertEquals(14, heap.remove());
        assertEquals(0, heap.getHeap()[13]);

        assertEquals(13, heap.remove());
        assertEquals(0, heap.getHeap()[12]);

        assertEquals(12, heap.remove());
        assertEquals(0, heap.getHeap()[11]);

        assertEquals(11, heap.remove());
        assertEquals(0, heap.getHeap()[10]);

        assertEquals(10, heap.remove());
        assertEquals(0, heap.getHeap()[9]);

        assertEquals(9, heap.remove());
        assertEquals(0, heap.getHeap()[8]);

        assertEquals(8, heap.remove());
        assertEquals(0, heap.getHeap()[7]);

        assertEquals(7, heap.remove());
        assertEquals(0, heap.getHeap()[6]);

        assertEquals(6, heap.remove());
        assertEquals(0, heap.getHeap()[5]);

        assertEquals(5, heap.remove());
        assertEquals(0, heap.getHeap()[4]);

        assertEquals(4, heap.remove());
        assertEquals(0, heap.getHeap()[3]);

        assertEquals(3, heap.remove());
        assertEquals(0, heap.getHeap()[2]);

        assertEquals(2, heap.remove());
        assertEquals(0, heap.getHeap()[1]);

        assertEquals(1, heap.remove());
        assertEquals(0, heap.getHeap()[0]);

        System.out.println("AS ARRAY: " + heap);
    }

}