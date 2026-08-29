package remote_preparation.multithreating;

public class PingPongSimplified {

    // Объект для синхронизации потоков
    private static final Object lock = new Object();
    // Флаг для определения очередности (изначально - ход "ping")
    private static boolean isPingTurn = true;
    // Количество итераций
    private static int N = 5;

    public static void main(String[] args) {

        Thread pingThread = new Thread(() -> {
            for (int i = 0; i < N; i++) {
                synchronized (lock) {
                    while (!isPingTurn) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    System.out.println("ping");
                    isPingTurn = false;
                    lock.notify();

                }
            }
        });

        Thread pongThread = new Thread(() -> {
            for (int i = 0; i < N; i++) {
                synchronized (lock) {
                    while (isPingTurn) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }

                    System.out.println("pong");
                    isPingTurn = true;
                    lock.notify();
                }
            }
        });

        pingThread.start();
        pongThread.start();

        try {
            pingThread.join();
            pongThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
