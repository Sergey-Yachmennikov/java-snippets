package miltithreading;

public class Consumer implements Runnable {

    private final Store store;

    public Consumer(Store store) {
        this.store = store;
    }

    @Override
    public void run() {
        for (int i = 0; i < 6; i++) {
            try {
                store.get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
