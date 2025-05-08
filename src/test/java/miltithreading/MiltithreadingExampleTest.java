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

    @Test
    void livelockBaseTest() {
        class Person {
            private final String name;
            private boolean sideLeft = true;

            public Person(String name) {
                this.name = name;
            }

            public boolean isSideLeft() {
                return sideLeft;
            }

            public void switchSide() {
                sideLeft = !sideLeft;
            }
        }

        final Person person1 = new Person("Alice");
        final Person person2 = new Person("Bob");

        Thread t1 = new Thread(() -> {
            while (person1.isSideLeft() == person2.isSideLeft()) {
                System.out.println("Alice sees Bob on same side, switching...");
                person1.switchSide();
                try { Thread.sleep(100); } catch (InterruptedException ignored) {}
            }
            System.out.println("Alice passed.");
        });

        Thread t2 = new Thread(() -> {
            while (person1.isSideLeft() == person2.isSideLeft()) {
                System.out.println("Bob sees Alice on same side, switching...");
                person2.switchSide();
                try { Thread.sleep(100); } catch (InterruptedException ignored) {}
            }
            System.out.println("Bob passed.");
        });

        t1.start();
        t2.start();
    }

    @Test
    void livelockReentrantLockTest() {
         class SharedResource {
            private final ReentrantLock lock = new ReentrantLock();

            public boolean tryLock(String name) {
                boolean success = lock.tryLock();
                if (success) {
                    System.out.println(name + " acquired lock.");
                }
                return success;
            }

            public void unlock(String name) {
                lock.unlock();
                System.out.println(name + " released lock.");
            }
        }

        SharedResource resource1 = new SharedResource();
        SharedResource resource2 = new SharedResource();

        Thread t1 = new Thread(() -> {
            while (true) {
                if (resource1.tryLock("Thread-1")) {
                    try {
                        try { Thread.sleep(50); } catch (InterruptedException ignored) {}
                        if (resource2.tryLock("Thread-1")) {
                            try {
                                System.out.println("Thread-1: Acquired both locks, working...");
                                break;
                            } finally {
                                resource2.unlock("Thread-1");
                            }
                        }
                    } finally {
                        resource1.unlock("Thread-1");
                    }
                }
                // Уступаем, чтобы другой поток смог сделать попытку
                try { Thread.sleep(50); } catch (InterruptedException ignored) {}
            }
        });


        Thread t2 = new Thread(() -> {
            while (true) {
                if (resource2.tryLock("Thread-2")) {
                    try {
                        try { Thread.sleep(50); } catch (InterruptedException ignored) {}
                        if (resource1.tryLock("Thread-2")) {
                            try {
                                System.out.println("Thread-2: Acquired both locks, working...");
                                break;
                            } finally {
                                resource1.unlock("Thread-2");
                            }
                        }
                    } finally {
                        resource2.unlock("Thread-2");
                    }
                }
                try { Thread.sleep(50); } catch (InterruptedException ignored) {}
            }
        });

        t1.start();
        t2.start();
    }
}
