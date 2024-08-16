package data_structures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomHeapTest {

    @Test
    void customHeapTest() {
        CustomHeap heap = new CustomHeap(15);

        heap.insert(15);
        heap.insert(13);
        heap.insert(7);
        heap.insert(5);
        heap.insert(52);
        heap.insert(23);
        heap.insert(16);
        heap.insert(9);
        heap.insert(21);

        assertEquals(52, heap.remove());
        assertEquals(0, heap.getHeap()[8]);

        assertEquals(23, heap.remove());
        assertEquals(0, heap.getHeap()[7]);

        assertEquals(21, heap.remove());
        assertEquals(0, heap.getHeap()[6]);

        assertEquals(16, heap.remove());
        assertEquals(0, heap.getHeap()[5]);

        assertEquals(15, heap.remove());
        assertEquals(0, heap.getHeap()[4]);

        assertEquals(13, heap.remove());
        assertEquals(0, heap.getHeap()[3]);

        assertEquals(9, heap.remove());
        assertEquals(0, heap.getHeap()[2]);

        assertEquals(7, heap.remove());
        assertEquals(0, heap.getHeap()[1]);

        assertEquals(5, heap.remove());
        assertEquals(0, heap.getHeap()[0]);
    }
}