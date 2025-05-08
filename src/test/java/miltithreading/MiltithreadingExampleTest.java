package miltithreading;

import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.ReentrantLock;

class MiltithreadingExampleTest {

    @Test
    void storeTest() {
        Store store = new Store();
        Producer producer = new Producer(store);
        Consumer consumer = new Consumer(store);

        new Thread(producer).start();
        new Thread(consumer).start();
    }

    @Test
    void lockTest() {
        CommonResource commonResource = new CommonResource();
        ReentrantLock locker = new ReentrantLock();

        for (int i = 0; i < 6; i++) {
            Thread thread = new Thread(new CountThread(commonResource, locker));
            thread.setName("Thread " + i);
            thread.start();
        }
    }
}
