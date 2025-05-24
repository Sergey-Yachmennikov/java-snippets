package miltithreading.syncronizers;

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierExamples {

    public static void cyclicBarrierExample() {
        CyclicBarrier barrier = new CyclicBarrier(2, () -> System.out.println("Барьер снят!"));

        new Thread(() -> {
            System.out.println("Поток 1 начал");
            try {
                barrier.await(); // Ждет второй поток
            } catch (Exception e) {
                try {
                    throw new Exception(e);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
            System.out.println("Поток 1 продолжил");
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Поток 2 начал");
            try {
                barrier.await(); // Снимает барьер
            } catch (Exception e) {
                try {
                    throw new Exception(e);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
            System.out.println("Поток 2 продолжил");
        }).start();
    }
}
