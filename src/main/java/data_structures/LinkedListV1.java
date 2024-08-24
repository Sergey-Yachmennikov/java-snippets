package data_structures;

import java.util.ArrayList;
import java.util.List;

public class LinkedListV1<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public static class Node<T> {
        private final T value;
        public Node<T> next;
        public Node<T> prev;

        public Node(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }
    }

    public Node<T> getHead() {
        return head;
    }

    public Node<T> getTail() {
        return tail;
    }

    public void add(T value) {
        if (head == null) {
            head = new Node<>(value);
        } else {
            if (tail == null) {
                tail = new Node<>(value);
                head.next = tail;
                tail.prev = head;
            } else {
                tail.next = new Node<>(value);
                tail.next.prev = tail;
                tail = tail.next;
            }
        }

        size++;
    }

    public int size() {
        return size;
    }

    public T removeHead() {
        T value = head.value;
        head = head.next;
        head.prev = null;
        size--;
        return value;
    }

    public T removeTail() {
        T value = tail.value;
        tail = tail.prev;
        tail.next = null;
        size--;
        return value;
    }

    @Override
    public String toString() {
        List<T> list = new ArrayList<>();
        Node<T> current = head;

        while (current != null) {
            list.add(current.value);
            current = current.next;
        }

        return list.toString();
    }
}
