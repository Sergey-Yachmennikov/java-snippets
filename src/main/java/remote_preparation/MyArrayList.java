package remote_preparation;


public class MyArrayList<T> {

    private Object[] elements;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    public MyArrayList() {
        elements = new Object[DEFAULT_CAPACITY];
    }

    public MyArrayList(int capacity) {
        elements = new Object[capacity];
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        checkIndex(index);
        return (T) elements[index];
    }

    public void add(T value) {
        ensureCapacity();
        elements[size] = value;
        size++;
    }

    public void remove(int index) {
        checkIndex(index);
        int movedElementCount = size - index - 1;
        if (movedElementCount > 0) {
            System.arraycopy(elements, index + 1, elements, index, movedElementCount);
        } else {
            elements[index] = null;
        }
        size--;
    }

    public int size() {
        return size;
    }

    public boolean contains(T value) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(value)) {
                return true;
            }
        }
        return false;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) throw new ArrayIndexOutOfBoundsException();
    }

    private void ensureCapacity() {
        if (size == elements.length) {
            int newCapacity = elements.length * 2;
            Object[] newElements = new Object[newCapacity];
            System.arraycopy(elements, 0, newElements, 0, size);
            elements = newElements;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(elements[i]);
            if (i < size - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}
