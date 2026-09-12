package remote_preparation.multithreating;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
// @priority
public class WorkersTask {

    public static void main(String[] args) throws InterruptedException {
        final int workerCount = 5;
        CountDownLatch latch = new CountDownLatch(workerCount);

        for (int i = 1; i <= workerCount; i++) {
            Thread thread = new Thread(new Worker(i, latch), "worker-" + i);
            thread.start();
        }

        // Ожидание выполнения всех потоков
        latch.await();
        System.out.println("Все работы завершены");

    }

    public static class Worker implements Runnable {

        private final int id;
        private final CountDownLatch latch;

        Worker(int id, CountDownLatch latch) {
            this.id = id;
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                long seconds = ThreadLocalRandom.current().nextInt(1, 6);
                System.out.printf("Работник %d начал работу (%d сек)%n", id, seconds);
                Thread.sleep(seconds * 1000);
                System.out.printf("Работник %d закончил работу%n", id);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                latch.countDown();
            }
        }
    }
}
