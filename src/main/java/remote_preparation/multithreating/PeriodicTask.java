package remote_preparation.multithreating;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
// @priority
public class PeriodicTask {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    public static void main(String[] args) throws InterruptedException {

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(task -> {
            Thread t = new Thread(task, "scheduler");
            t.setDaemon(true);
            return t;
        });

        Runnable task = () -> {
            try {
                System.out.println("Текущее время: " + LocalDateTime.now().format(FORMATTER));
            } catch (Exception e) {
                // ВАЖНО: любое непойманное исключение НАВСЕГДА отменяет периодическую задачу,
                // причём молча — никакого стектрейса в консоли не будет.
                System.err.println("Ошибка в задаче: " + e.getMessage());
            }
        };

        scheduler.scheduleAtFixedRate(task, 0, 2, TimeUnit.SECONDS);

        System.out.println("Нажмите Enter для остановки...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        // Остановка планировщика
        scheduler.shutdown();
        // Даём текущей итерации спокойно доработать
        if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
            scheduler.shutdownNow();
        }
        System.out.println("Планировщик остановлен");

    }

}
