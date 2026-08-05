package remote_preparation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayQueueTest {

    @Test
    void all() {
        ArrayQueue<Integer> queue = new ArrayQueue<>(3);

        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        System.out.println("Size: " + queue.size()); // 3

        queue.enqueue(4); // вызовет resize, так как начальная capacity = 3
        System.out.println("Size after resize: " + queue.size()); // 4

        System.out.println("Dequeue: " + queue.dequeue()); // 1
        System.out.println("Dequeue: " + queue.dequeue()); // 2
        System.out.println("IsEmpty: " + queue.isEmpty()); // false

        queue.dequeue();
        queue.dequeue();
        System.out.println("IsEmpty: " + queue.isEmpty()); // true
    }

}