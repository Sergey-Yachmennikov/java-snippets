package remote_preparation;

import org.junit.jupiter.api.Test;

class PriorityQueueTest {

    @Test
    void all() {
        PriorityQueue pq = new PriorityQueue();

        pq.enqueue(3, "Помыть посуду");
        pq.enqueue(1, "Пожар!");
        pq.enqueue(2, "Купить хлеб");
        pq.enqueue(1, "Вызвать скорую");

        System.out.println("Размер: " + pq.size()); // 4

        while (!pq.isEmpty()) {
            System.out.println(pq.dequeue());
        }
    }

}