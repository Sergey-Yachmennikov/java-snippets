package remote_preparation.multithreating;

import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ProducerConsumerTask {

    static final Integer POISON_PILL = Integer.MIN_VALUE;

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(10);
        // Продюсеры разной скорости: быстрый, средний, медленный.
        // Каждый производит своё число элементов — суммарно 30.
        List<Producer> producers = List.of(
                new Producer(queue, "P-fast",   10, 50,  150),
                new Producer(queue, "P-medium", 10, 200, 400),
                new Producer(queue, "P-slow",   10, 500, 800)
        );

        List<Consumer> consumers = List.of(
                new Consumer(queue, "C-fast", 100, 250),
                new Consumer(queue, "C-slow", 600, 900)
        );

        CountDownLatch producersFinished = new CountDownLatch(producers.size());

        List<Thread> producerThreads = producers.stream()
                .map(p -> new Thread(() -> {
                        try {
                            p.run();
                        } finally {
                            producersFinished.countDown();
                        }
                    }, p.name)
                )
                .toList();

        List<Thread> consumerThreads = consumers.stream()
                .map(c -> new Thread(c, c.name))
                .toList();

        producerThreads.forEach(Thread::start);
        consumerThreads.forEach(Thread::start);

        Thread dispatcher = createDispatcher(producersFinished, consumers, queue);

        for (Thread t : producerThreads) t.join();

        dispatcher.start();
        dispatcher.join();

        for (Thread t : consumerThreads) t.join();

        // console result
        int produced = producers.stream().mapToInt(p -> p.produced.get()).sum();
        int consumed = consumers.stream().mapToInt(c -> c.consumed.get()).sum();

        System.out.println();
        System.out.println("Итого произведено: " + produced);
        System.out.println("Итого потреблено:  " + consumed);
        System.out.println(produced == consumed ? "OK: ничего не потеряно" : "ОШИБКА: числа расходятся");

    }

    @NonNull
    private static Thread createDispatcher(CountDownLatch producersFinished, List<Consumer> consumers, BlockingQueue<Integer> queue) {
        return new Thread(() -> {
            try {
                producersFinished.await();
                System.out.println("[dispatcher] все производители закончили, "
                        + "рассылаю " + consumers.size() + " сигналов остановки");
                for (int i = 0; i < consumers.size(); i++) {
                    queue.put(POISON_PILL);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "dispatcher");
    }

    public static final class Producer implements Runnable {
        private final BlockingQueue<Integer> queue;
        private final String name;
        private final int itemsToProduce;
        private final int minDelayMs;
        private final int maxDelayMs;
        private final Random random = new Random();
        final AtomicInteger produced = new AtomicInteger();

        Producer(BlockingQueue<Integer> queue, String name, int itemsToProduce, int minDelayMs, int maxDelayMs) {
            this.queue = queue;
            this.name = name;
            this.itemsToProduce = itemsToProduce;
            this.minDelayMs = minDelayMs;
            this.maxDelayMs = maxDelayMs;
        }

        @Override
        public void run() {
            for (int i = 0; i < itemsToProduce; i++) {
                // Диапазон 0..999 не пересекается с POISON_PILL (Integer.MIN_VALUE)
                int value = random.nextInt(1000);
                try {
                    queue.put(value);                 // блокируется, если очередь полна
                    produced.incrementAndGet();

                    Thread.sleep(random.nextInt(maxDelayMs - minDelayMs));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            System.out.println("[" + name + "] закончил, произведено: " + produced.get());
        }
    }

    public static final class Consumer implements Runnable {
        private final BlockingQueue<Integer> queue;
        private final String name;
        private final int minDelayMs;
        private final int maxDelayMs;
        private final Random random = new Random();
        final AtomicInteger consumed = new AtomicInteger();

        Consumer(BlockingQueue<Integer> queue, String name, int minDelayMs, int maxDelayMs) {
            this.queue = queue;
            this.name = name;
            this.minDelayMs = minDelayMs;
            this.maxDelayMs = maxDelayMs;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    int value = queue.take();          // блокируется, если очередь пуста

                    if (value == POISON_PILL) {
                        System.out.println("[" + name + "] получил сигнал остановки, "
                                + "потреблено: " + consumed.get());
                        return;
                    }

                    consumed.incrementAndGet();

                    Thread.sleep(random.nextInt(maxDelayMs - minDelayMs));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }
}



