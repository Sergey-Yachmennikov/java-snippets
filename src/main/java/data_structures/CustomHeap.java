package data_structures;

import java.util.Arrays;

class CustomHeap {
    int[] heap;
    int size;
    int minMaxFlag;

    public CustomHeap(int max) {
        this(max, 0);
    }

    public CustomHeap(int max, int minMaxFlag) {
        heap = new int[max];
        size = 0;
        this.minMaxFlag = minMaxFlag;
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

    public int parentIndex(int index) {
        return (index - 1) / 2;
    }

    public int leftChildIndex(int index) {
        return (2 * index) + 1;
    }

    public int rightChildIndex(int index) {
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

    // max/min heap based on flag
    public void percolateUp(int index) {
        int temp = heap[index];
        int parent = parentIndex(index);
        if (this.minMaxFlag == 0) {
            while (index > 0 && heap[parent] < temp) {
                heap[index] = heap[parent];
                index = parent;
                parent = parentIndex(index);

            }
        } else {
            while (index > 0 && heap[parent] > temp) {
                heap[index] = heap[parent];
                index = parent;
                parent = parentIndex(index);

            }
        }

        heap[index] = temp;
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
        int smallChilIndex;
        if (minMaxFlag == 0) {
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
        } else {
            while (index < (size / 2)) {
                lcIndex = leftChildIndex(index);
                rcIndex = rightChildIndex(index);
                if (rcIndex < size && heap[lcIndex] > heap[rcIndex]) {
                    smallChilIndex = rcIndex;
                } else {
                    smallChilIndex = lcIndex;
                }
                if (heap[smallChilIndex] >= temp)
                    break;
                heap[index] = heap[smallChilIndex];
                index = smallChilIndex;
            }
        }
        heap[index] = temp;
    }

    public void printAscOrder(int n) {

        for (int i = 0; i < n; i++) {
            System.out.print(remove() + "\t");
        }
        System.out.println("\n\n");
    }

    public void printDscOrder(int n) {

        for (int i = 0; i < n; i++) {
            System.out.print(remove() + "\t");
        }
    }

    public void displayHeap() {
        System.out.print("Inserted elements are: ");
        for (int m = 0; m < size; m++)
            if (heap[m] != Integer.MAX_VALUE)
                System.out.print(heap[m] + " ");
            else
                System.out.print("-- ");
        System.out.println();
        int nBlanks = 32;
        int itemsPerRow = 1;
        int column = 0;
        int j = 0; // current item
        String delimeter = "---------------------------------------------";
        System.out.println(delimeter);
        while (size > 0) {
            if (column == 0)
                for (int k = 0; k < nBlanks; k++)
                    System.out.print(' ');
            System.out.print(heap[j]);
            if (++j == size) // done?
                break;
            if (++column == itemsPerRow) {
                nBlanks /= 2;
                itemsPerRow *= 2;
                column = 0;
                System.out.println();
            } else
                for (int k = 0; k < nBlanks * 2 - 2; k++)
                    System.out.print(' ');
        }
        System.out.println("\n" + delimeter);
    }

    @Override
    public String toString() {
        return "CustomHeap: " + Arrays.toString(heap);
    }
}
