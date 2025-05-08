package miltithreading;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class MiltithreadingExampleTest {

    @Test
    void storeTest() {
        Store store = new Store();
        Producer producer = new Producer(store);
        Consumer consumer = new Consumer(store);

        new Thread(producer).start();
        new Thread(consumer).start();
    }

    @Test
    void lockTest() {
        CommonResource commonResource = new CommonResource();
        Lock locker = new ReentrantLock();

        for (int i = 0; i < 6; i++) {
            Thread thread = new Thread(new CountThread(commonResource, locker));
            thread.setName("Thread " + i);
            thread.start();
        }
    }

    @Test
    void boundedBufferTest() {
        BoundedBuffer<Integer> buffer = new BoundedBuffer<>(5);

        // Producer
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    buffer.put(i);
                    System.out.println("Produced: " + i);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();

        // Consumer
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    int item = buffer.take();
                    System.out.println("Consumed: " + item);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

    @Test
    void deadlockSynchronizedTest() { // base case of deadlock
         Object lockA = new Object();
         Object lockB = new Object();

        Thread thread1 = new Thread(() -> {
            synchronized (lockA) {
                System.out.println("Поток 1 захватил lockA");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                synchronized (lockB) {
                    System.out.println("Поток 1 захватил lockB");
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (lockB) {
                System.out.println("Поток 2 захватил lockB");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (lockA) {
                    System.out.println("Поток 2 захватил lockA");
                }
            }
        });

        thread1.start();
        thread2.start();
    }

    @Test
    void deadlockReentrantLockTest() {
       Lock lock1 = new ReentrantLock();
       Lock lock2 = new ReentrantLock();

        Thread thread1 = new Thread(() -> {
            lock1.lock();
            System.out.println("Поток 1 удерживает lock1");
            try {
                Thread.sleep(100); // Имитация работы
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Поток 1 пытается захватить lock2...");
            lock2.lock(); // Блокируется, так как lock2 удерживается thread2
            System.out.println("Поток 1 удерживает lock1 и lock2");
            lock2.unlock();
            lock1.unlock();
        });

        Thread thread2 = new Thread(() -> {
            lock2.lock();
            System.out.println("Поток 2 удерживает lock2");
            try {
                Thread.sleep(100); // Имитация работы
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Поток 2 пытается захватить lock1...");
            lock1.lock(); // Блокируется, так как lock1 удерживается thread1
            System.out.println("Поток 2 удерживает lock2 и lock1");
            lock1.unlock();
            lock2.unlock();
        });

        thread1.start();
        thread2.start();
    }

    @Test
    @Disabled
    void joinDeadlockTest() {
        final Thread[] threads = new Thread[2];  // Массив для хранения ссылок на потоки

        threads[0] = new Thread(() -> {
            try {
                threads[1].join();  // Первый поток ждёт завершения второго
                System.out.println("Поток 1 завершил работу");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        threads[1] = new Thread(() -> {
            try {
                threads[0].join();  // Второй поток ждёт завершения первого
                System.out.println("Поток 2 завершил работу");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        threads[0].start();
        threads[1].start();

        try {
            threads[0].join();
            threads[1].join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Главный поток завершился");
    }

    @Test
    void deadlockWithPoolTest() throws ExecutionException, InterruptedException {
        final ExecutorService pool = Executors.newFixedThreadPool(1); // не хватает потоков для обработки

        Future<?> future = pool.submit(() -> {
            System.out.println("Задача 1 запущена");

            // Пытаемся отправить вторую задачу в ТОТ ЖЕ пул
            Future<?> nestedFuture = pool.submit(() -> {
                System.out.println("Задача 2 запущена");
                return "Результат";
            });

            try {
                // Ждём завершения вложенной задачи -> DEADLOCK!
                System.out.println("Задача 1 ждёт результат Задачи 2...");
                nestedFuture.get();
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("Задача 1 завершена");
            return "Готово";
        });

        // Основной поток ждёт завершения первой задачи
        future.get();
        System.out.println("Основной поток завершён");
        pool.shutdown();
    }
}
