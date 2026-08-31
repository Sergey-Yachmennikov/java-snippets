package remote_preparation.multithreating;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;


class CounterDemoTest {

    private static final int THREADS = 16;
    private static final int ITERATIONS = 100_000;

    private ExecutorService executor;

    @BeforeEach
    void setUp() {
        executor = Executors.newFixedThreadPool(THREADS);
    }

    @AfterEach
    void tearDown() throws InterruptedException {
        if (!executor.isTerminated()) {
            executor.shutdownNow();
            executor.awaitTermination(5, TimeUnit.SECONDS);
        }
    }

    @Test
    void tenThreadsThousandIncrementsEachYieldsTenThousand() throws InterruptedException {
        CounterDemo.Counter counter = new CounterDemo.AtomicLongCounter();
        CountDownLatch start = new CountDownLatch(1);

        for (int i = 0; i < THREADS; i++) {
            executor.submit(() -> {
                try {
                    start.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
                for (int j = 0; j < ITERATIONS; j++) {
                    counter.increment();
                }
            });
        }

        start.countDown();
        executor.shutdown();

        // Ждём завершения всех задач
        boolean awaited = executor.awaitTermination(1, TimeUnit.MINUTES);
        System.out.println(counter.get());
        assertTrue(awaited);
        assertEquals((long) THREADS * ITERATIONS, counter.get());
    }
}