package remote_preparation.multithreating;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class PeriodicTaskDynamic {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    public static void main(String[] args) throws InterruptedException {

        try(ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "scheduler");
            t.setDaemon(true);
            return t;
        })) {
            Runnable task = () -> {
                try {
                    System.out.println("Текущее время: " + LocalDateTime.now().format(FORMATTER));
                } catch (Exception e) {
                    System.err.println("Ошибка в задаче: " + e.getMessage());
                }
            };

            long periodSeconds = 2;
            ScheduledFuture<?> handle = scheduler.scheduleAtFixedRate(task, 0, periodSeconds, TimeUnit.SECONDS);

            System.out.println("Период: " + periodSeconds + " сек.");
            System.out.println("Введите число — новый период в секундах. Пустая строка — выход.");

            Scanner scanner = new Scanner(System.in);
            while (true) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    break;
                }

                long newPeriod;
                try {
                    newPeriod = Long.parseLong(line);
                } catch (NumberFormatException e) {
                    System.out.println("Не число. Введите период в секундах или Enter для выхода.");
                    continue;
                }
                if (newPeriod <= 0) {
                    System.out.println("Период должен быть больше нуля.");
                    continue;
                }

                // false = не прерывать текущую итерацию, дать ей доработать
                handle.cancel(false);
                handle = scheduler.scheduleAtFixedRate(task, 0, newPeriod, TimeUnit.SECONDS);
                System.out.println("Новый период: " + newPeriod + " сек.");


                scheduler.shutdown();
                if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                    scheduler.shutdownNow();
                }
                System.out.println("Планировщик остановлен");

            }

            scheduler.shutdown();
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
            System.out.println("Планировщик остановлен");
        }
    }
}