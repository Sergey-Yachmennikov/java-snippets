package miltithreading;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBuffer<T> {
    private final Queue<T> queue = new LinkedList<>();
    private final int capacity;

    private final Lock lock = new ReentrantLock();
    private final Condition isNotFull = lock.newCondition();
    private final Condition isNotEmpty = lock.newCondition();

    public BoundedBuffer(int capacity) {
        this.capacity = capacity;
    }

    public void put(T item) throws InterruptedException {
        lock.lock();
        try {
            while (queue.size() == capacity) {
                isNotFull.await(); // до тех пор пока очередь полностью заполнена, мы не можем положить элемент
            }
            queue.add(item);
            isNotEmpty.signal(); // сигнал для Consumer
        } finally {
            lock.unlock();
        }
    }

    public T take() throws InterruptedException {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                isNotEmpty.await();
            }

            T item = queue.remove();
            isNotFull.signal(); // сигнал продюсеру, что появилось место
            return item;
        } finally {
            lock.unlock();
        }
    }
}
