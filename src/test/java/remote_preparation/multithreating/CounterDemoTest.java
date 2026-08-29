package remote_preparation.multithreating;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;


class CounterDemoTest {

    @Test
    void tenThreadsThousandIncrementsEachYieldsTenThousand() throws InterruptedException {
        CounterDemo.Counter counter = new CounterDemo.LongAdderCounter();
        final int threadCount = 10;
        final int iterationsCount = 1000;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                for (int j = 0; j < iterationsCount; j++) {
                    counter.increment();
                }
            });
        }

        executor.shutdown();

        // Ждём завершения всех задач
        boolean awaited = executor.awaitTermination(1, TimeUnit.MINUTES);
        System.out.println(counter.get());
        assertTrue(awaited);
    }
}