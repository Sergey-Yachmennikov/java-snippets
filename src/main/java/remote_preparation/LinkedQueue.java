package remote_preparation;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class LinkedQueue<T>  {

    private final LinkedList<T> elements;

    public LinkedQueue() {
        this.elements = new LinkedList<>();
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public int size() {
        return elements.size();
    }

    public void enqueue(T t) {
        elements.addLast(t);
    }

    public T dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return elements.removeFirst();
    }
}
