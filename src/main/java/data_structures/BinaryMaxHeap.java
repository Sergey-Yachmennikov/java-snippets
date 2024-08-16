package data_structures;

import java.util.Arrays;

public class BinaryMaxHeap {
    private int[] heap;
    private int size;

    public BinaryMaxHeap(int max) {
        heap = new int[max];
        size = 0;
    }

    public int getSize() {
        return size;
    }

    public int[] getHeap() {
        return heap;
    }

    int getTop() {
        int max = Integer.MAX_VALUE;

        if (size >= 0) {
            max = heap[0];
        }

        return max;
    }

    private int parentIndex(int index) {
        return (index - 1) / 2;
    }

    private int leftChildIndex(int index) {
        return (2 * index) + 1;
    }

    private int rightChildIndex(int index) {
        return (2 * index) + 2;
    }

    public void swap(int index1, int index2) {
        heap[index1] = heap[index1] ^ heap[index2];
        heap[index2] = heap[index1] ^ heap[index2];
        heap[index1] = heap[index1] ^ heap[index2];
    }

    public void insert(int element) {
        if (size == 0) {
            heap[size++] = element;
        } else {
            heap[size] = element;
            percolateUp(size++);
        }
    }

    public void percolateUp(int currentIndex) {
        int currentValue = heap[currentIndex];
        int parentInx = parentIndex(currentIndex);

        while (currentIndex > 0 && heap[parentInx] < currentValue) {
            heap[currentIndex] = heap[parentInx];
            currentIndex = parentInx;
            parentInx = parentIndex(currentIndex); // move to next parent
        }

        heap[currentIndex] = currentValue;
    }

    public int remove() {
        int temp = heap[0];
        heap[0] = heap[--size];
        heap[size] = 0;
        percolateDown(0);
        return temp;
    }

    public void percolateDown(int index) {
        int lcIndex;
        int rcIndex;
        int temp = heap[index];
        int largeChildIndex;

        while (index < (size / 2)) {
            lcIndex = leftChildIndex(index);
            rcIndex = rightChildIndex(index);
            if (rcIndex < size && heap[lcIndex] < heap[rcIndex]) {
                largeChildIndex = rcIndex;
            } else {
                largeChildIndex = lcIndex;
            }
            if (heap[largeChildIndex] <= temp)
                break;
            heap[index] = heap[largeChildIndex];
            index = largeChildIndex;
        }

        heap[index] = temp;
    }

    @Override
    public String toString() {
        return "BinaryMaxHeap: " + Arrays.toString(heap);
    }
}
