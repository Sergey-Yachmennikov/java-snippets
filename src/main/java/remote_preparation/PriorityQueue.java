package remote_preparation;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class PriorityQueue {

    // Внутренний класс для хранения пары "приоритет + значение"
    private static class Node {
        int priority;
        String value;

        Node(int priority, String value) {
            this.priority = priority;
            this.value = value;
        }
    }

    private final List<Node> heap = new ArrayList<>();

    /**
     * Добавляет элемент с указанным приоритетом.
     * Чем меньше число — тем выше приоритет.
     */
    public void enqueue(int priority, String value) {
        Node node = new Node(priority, value);
        heap.add(node);                    // добавляем в конец
        siftUp(heap.size() - 1);           // "поднимаем" на нужное место
    }

    /**
     * Удаляет и возвращает значение с наивысшим приоритетом (наименьшее число).
     */
    public String dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Очередь пуста");
        }

        Node top = heap.get(0);
        Node last = heap.remove(heap.size() - 1);

        if (!heap.isEmpty()) {
            heap.set(0, last);   // перемещаем последний элемент в корень
            siftDown(0);         // и "опускаем" на нужное место
        }

        return top.value;
    }

    /**
     * Возвращает значение с наивысшим приоритетом без удаления.
     */
    public String peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Очередь пуста");
        }
        return heap.get(0).value;
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public int size() {
        return heap.size();
    }

    // ==================== Внутренняя логика кучи ====================

    /**
     * "Просеивание вверх" — восстанавливает свойство кучи после добавления
     * элемента в конец: сравнивает с родителем и меняет местами, если нарушен порядок.
     */
    private void siftUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (heap.get(index).priority < heap.get(parentIndex).priority) {
                swap(index, parentIndex);
                index = parentIndex;
            } else {
                break; // порядок восстановлен, дальше подниматься не нужно
            }
        }
    }

    /**
     * "Просеивание вниз" — восстанавливает свойство кучи после удаления корня:
     * сравнивает элемент с детьми и меняет местами с меньшим из них.
     */
    private void siftDown(int index) {
        int size = heap.size();

        while (true) {
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;
            int smallest = index;

            if (leftChild < size && heap.get(leftChild).priority < heap.get(smallest).priority) {
                smallest = leftChild;
            }
            if (rightChild < size && heap.get(rightChild).priority < heap.get(smallest).priority) {
                smallest = rightChild;
            }

            if (smallest == index) {
                break; // порядок восстановлен
            }

            swap(index, smallest);
            index = smallest;
        }
    }

    private void swap(int i, int j) {
        Node temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
}
