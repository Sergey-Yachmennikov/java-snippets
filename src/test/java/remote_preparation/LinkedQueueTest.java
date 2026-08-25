package remote_preparation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkedQueueTest {

    @Test
    void all() {
        LinkedQueue<Integer> queue = new LinkedQueue<>();

        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        System.out.println("Size: " + queue.size()); // 3

        System.out.println("Dequeue: " + queue.dequeue()); // 1
        System.out.println("Dequeue: " + queue.dequeue()); // 2
        System.out.println("IsEmpty: " + queue.isEmpty()); // false

        queue.dequeue();
        System.out.println("IsEmpty: " + queue.isEmpty()); // true
    }

}