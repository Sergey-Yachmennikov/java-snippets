package miltithreading;

import org.junit.jupiter.api.Test;

public class MiltithreadingExampleTest {

    @Test
    void storeTest() {
        Store store = new Store();
        Producer producer = new Producer(store);
        Consumer consumer = new Consumer(store);

        new Thread(producer).start();
        new Thread(consumer).start();
    }
}
