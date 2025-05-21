package miltithreading.syncronizers;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

public class ControlledInitialization {

    static class Resource1 {}
    static class Resource2 {}
    static class Resource3 {}

    private Resource1 resource1;
    private Resource2 resource2;
    private Resource3 resource3;
    private final CountDownLatch latch = new CountDownLatch(3);

    public ControlledInitialization() {
        initialize();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private final Runnable initResource1 = () -> {
        try {
            // simulate wait
            Thread.sleep(4000);
            resource1 = new Resource1();
            latch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    };

    private final Runnable initResource2 = () -> {
        try {
            // simulate wait
            Thread.sleep(4000);
            resource2 = new Resource2();
            latch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    };

    private final Runnable initResource3 = () -> {
        try {
            // simulate wait
            Thread.sleep(4000);
            resource3 = new Resource3();
            latch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    };

    public void doTask() {
        System.out.println("=== Resources Initialized ===");
        System.out.println("Resource 1 instance " + resource1);
        System.out.println("Resource 2 instance " + resource2);
        System.out.println("Resource 3 instance " + resource3);
    }

    private void initialize() {
        System.out.println("=== Initializing Resources ===");
        try (var executor = Executors.newFixedThreadPool(3)) {
            executor.execute(initResource1);
            executor.execute(initResource2);
            executor.execute(initResource3);
            executor.shutdown();
        }
    }
}
