package remote_preparation.multithreating;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class WorkersTaskExecutor {

    public static void main(String[] args) throws InterruptedException {
        int workerCount = 5;
        ExecutorService pool = Executors.newFixedThreadPool(workerCount);

        List<Callable<String>> tasks = new ArrayList<>();
        for (int i = 1; i <= workerCount; i++) {
            int id = i;
            tasks.add(() -> doWork(id));
        }

        System.out.println("Запуск рабочих потоков...");
        try {
            // Блокирующий вызов: вернётся, только когда все задачи завершены
            // (успешно, с исключением или отменены).
            List<Future<String>> futures = pool.invokeAll(tasks);
            System.out.println("Все работы завершены");

            System.out.println("\n--- Отчёт ---");
            for (int i = 0; i < futures.size(); i++) {
                try {
                    // get() уже не блокирует: задача точно завершена
                    System.out.printf("  [OK]     Работник %d -> %s%n", i + 1, futures.get(i).get());
                } catch (ExecutionException e) {
                    // Реальная причина лежит в getCause()
                    System.out.printf("  [ОШИБКА] Работник %d -> %s%n", i + 1, e.getCause().getMessage());
                }
            }
        } finally {
            pool.shutdown();
        }
    }

    private static String doWork(int id) throws InterruptedException {
        long seconds = ThreadLocalRandom.current().nextInt(1, 6);
        System.out.printf("Работник %d начал работу (%d сек)%n", id, seconds);

        Thread.sleep(seconds * 1000);

        if (ThreadLocalRandom.current().nextInt(10) < 3) {
            throw new IllegalStateException("не удалось обработать задачу #" + id);
        }

        System.out.printf("Работник %d закончил работу%n", id);
        return "результат-" + id;
    }
}



