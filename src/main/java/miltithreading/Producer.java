package miltithreading;

public class Producer implements Runnable {

    private final Store store;

    public Producer(Store store) {
        this.store = store;
    }

    @Override
    public void run() {
        for (int i = 0; i < 6; i++) {
            try {
                store.put();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
