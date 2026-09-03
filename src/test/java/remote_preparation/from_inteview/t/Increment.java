package remote_preparation.from_inteview.t;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Increment {
    private static int counter1;
    private static int counter2;

    public static synchronized void incrementBoth() {
        counter1++;
        counter2++;
    }

    public static void main(String[] args) throws InterruptedException {
        int taskCount = 100_000;
        CountDownLatch latch = new CountDownLatch(taskCount);
        ExecutorService executor = Executors.newFixedThreadPool(100);

        for (int i = 0; i < taskCount; i++) {
            executor.submit(() -> {
                incrementBoth();
                latch.countDown();
            });
        }

        latch.await();

        System.out.println(counter1);
        System.out.println(counter2);
        System.exit(0);
    }
}
