package data_structures;

import java.util.Arrays;

public class BinaryHeap2<T extends Comparable<T>> {
    private T[] list;
    private int elementsCount; // element count
    private int capacity = 10;

    @SuppressWarnings("unchecked")
    public BinaryHeap2() {
        list = (T[]) new Comparable[capacity + 1];
    }

    @SuppressWarnings("unchecked")
    public BinaryHeap2(int capacity) {
        this.capacity = capacity;
        list = (T[]) new Comparable[capacity + 1];
    }

    public boolean isEmpty() {
        return elementsCount == 0;
    }

    public int size() {
        return elementsCount;
    }

    public void insert(T v) {
        list[++elementsCount] = v;
        swim(elementsCount);
    }

    public T deleteMax() {
        T max = list[1];
        swap(1, elementsCount--);
        list[elementsCount + 1] = null;
        sink(1);

        return max;
    }

    private boolean lessThen(int i, int j) {
        return list[i].compareTo(list[j]) < 0;
    }

    private void swap(int i, int j) {
        T temp = list[i];
        list[i] = list[j];
        list[j] = temp;
    }

    // move up
    private void swim(int k) {
        while (k > 1 && lessThen(k / 2, k)) {
            swap(k / 2, k);
            k = k / 2;
        }
    }

    // move down
    private void sink(int k) {
        while (k * 2 < elementsCount) {
            int j = k * 2;
            if (j < elementsCount && lessThen(j, j + 1)) j++;
            if (!lessThen(k, j)) break;
            swap(k, j);
            k = j;
        }
    }

    @Override
    public String toString() {
        return "BinaryHeap2: " + Arrays.toString(list);
    }
}
