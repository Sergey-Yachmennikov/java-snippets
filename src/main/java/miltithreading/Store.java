package miltithreading;

public class Store {

    private int items;

    public synchronized void put() throws InterruptedException {
        while (items >= 3) {
            wait();
        }
        items++;
        System.out.println("Producer added 1 item. Total items: " + items);
        notify();
    }

    public synchronized void get() throws InterruptedException {
        while (items < 1) {
            wait();
        }
        items--;
        System.out.println("Consumer get 1 item. Total items: " + items);
        notify();
    }
}
