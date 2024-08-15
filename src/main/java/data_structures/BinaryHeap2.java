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

    public boolean isEmpty() {
        return elementsCount == 0;
    }

    public int size() {
        return elementsCount;
    }

    public void insert(T v) {
        if (capacity == elementsCount) resize();
        list[++elementsCount] = v;
        swim(elementsCount);
    }

    private void resize() {
        capacity *= 2;
        list = Arrays.copyOf(list, capacity + 1);
    }

    public T deleteMax() {
        T max = list[1];
        swap(1, elementsCount--);
        list[elementsCount + 1] = null;
        sink();

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
    private void swim(int index) {
        while (index > 1 && lessThen(index / 2, index)) {
            swap(index / 2, index);
            index = index / 2;
        }
    }

    // move down
    private void sink() {
        int k = 1;
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
