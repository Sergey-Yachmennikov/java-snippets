package data_structures;

import java.util.Arrays;

public class BinaryHeap {

    private int[] heap;
    private int elementsCount;
    private int capacity;

    public BinaryHeap(int capacity) {
        this.capacity = capacity;
        this.heap = new int[capacity];
        this.elementsCount = 0;
    }

    private int parent(int i) {
        return (i - 1) / 2;
    }

    private int leftChild(int i) {
        return 2 * i + 1;
    }

    private int rightChild(int i) {
        return 2 * i + 2;
    }

    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    private void resize() {
        capacity *= 2;
        heap = Arrays.copyOf(heap, capacity);
    }

    // 10, 5, 30
    public void insert(int value) {
        if (elementsCount >= capacity) {
            resize(); // resize array to 2 times
        }

        heap[elementsCount] = value; // set value as last element in array
        int currentIndex = elementsCount;
        elementsCount++; // increase count of element in array

        while (currentIndex > 0 && heap[currentIndex] < heap[parent(currentIndex)]) {
            swap(currentIndex, parent(currentIndex));
            currentIndex = parent(currentIndex);
        }
    }

    private void heapifyDown(int i) {
        // Assume the current index is the smallest
        int smallest = i;

        // Get the left child index
        int left = leftChild(i);

        // Get the right child index
        int right = rightChild(i);

        // If the left child is smaller than the current
        // smallest element, update smallest
        if (left < elementsCount && heap[left] < heap[smallest]) {
            smallest = left;
        }

        // If the right child is smaller than the current
        // smallest element, update smallest
        if (right < elementsCount && heap[right] < heap[smallest]) {
            smallest = right;
        }

        // If the smallest element is not the current
        // element, swap and continue heapifying down
        if (smallest != i) {
            swap(i, smallest);
            heapifyDown(smallest);
        }
    }

    public int extractMin() {
        if (elementsCount == 0) {
            throw new RuntimeException("Heap is empty!");
        }

        int min = heap[0];

        // Replace the root with the last element in the heap
        heap[0] = heap[elementsCount - 1];
        elementsCount--;

        heapifyDown(0);
        return min;
    }

    public void printHeap() {
        System.out.println(Arrays.toString(Arrays.copyOf(heap, elementsCount)));
    }
}
