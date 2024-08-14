package data_structures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BinaryHeapTest {

    @Test
    void binaryHeapTest() {
        // Create a BinaryHeap with an initial capacity of 10
        BinaryHeap bh = new BinaryHeap(10);

        // Insert elements into the heap
        bh.insert(10);
        bh.insert(5);
        bh.insert(30);
        bh.insert(3);
        bh.insert(8);

        // Print the heap after inserting elements
        System.out.println("Heap after inserting elements:");
        bh.printHeap();

        // Extract the minimum element and print it
        System.out.println("Extracted minimum: " + bh.extractMin());
        // Print the heap after extracting the minimum element
        System.out.println("Heap after extracting the minimum:");
        bh.printHeap();
    }

    @Test
    void BinaryHeap2Test() {
        BinaryHeap2<Integer> bh = new BinaryHeap2<>(10);
        // Insert elements into the heap
        bh.insert(10);
        bh.insert(5);
        bh.insert(30);
        bh.insert(3);
        bh.insert(8);

        System.out.println(bh);
    }

}