package data_structures;

public class MinHeap {
    private int[] heap;
    private int index;
    private int size;

    public MinHeap(int size) {
        this.size = size;
        this.index = 0;
        heap = new int[size];
    }

    public int[] getHeap() {
        return heap;
    }

    private int parent(int i) {
        return (i - 1) / 2;
    }

    private int leftChild(int i) {
        return (i * 2) + 1;
    }

    private int rightChild(int i) {
        return (i * 2) + 2;
    }

    private boolean isLeaf(int i) {
        return rightChild(i) >= size || leftChild(i) >= size;
    }

    public void insert(int element) {
        if (index >= size) {
            return;
        }
        heap[index] = element;
        int current = index;

        while (heap[current] < heap[parent(current)]) {
            swap(current, parent(current));
            current = parent(current);
        }

        index++;
    }

    public int remove() {
        // since its a min heap, so root = minimum
        int popped = heap[0];
        heap[0] = heap[--index];
        minHeapify(0);
        return popped;
    }

    private void minHeapify(int i) {
        // If the node is a non-leaf node and any of its child is smaller
        if (!isLeaf(i)) {
            if (heap[i] > heap[leftChild(i)] ||
                    heap[i] > heap[rightChild(i)]) {
                if (heap[leftChild(i)] < heap[rightChild(i)]) {
                    swap(i, leftChild(i));
                    minHeapify(leftChild(i));
                } else {
                    swap(i, rightChild(i));
                    minHeapify(rightChild(i));
                }
            }
        }
    }

    public void minHeap() {
        for (int i = (index - 1 / 2); i >= 1; i--) {
            minHeapify(i);
        }
    }

    // Function to print the contents of the heap
    public void printHeap() {
        for (int i = 0; i < (index / 2); i++) {
            System.out.print("Parent : " + heap[i]);
            if (leftChild(i) < index)
                System.out.print(" Left : " + heap[leftChild(i)]);
            if (rightChild(i) < index)
                System.out.print(" Right :" + heap[rightChild(i)]);
            System.out.println();
        }
    }

    private void swap(int x, int y) {
        int tmp;
        tmp = heap[x];
        heap[x] = heap[y];
        heap[y] = tmp;
    }
}
