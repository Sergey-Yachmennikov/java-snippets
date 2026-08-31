package remote_preparation.multithreating;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PingPongLockSimplified {

    private static final ReentrantLock lock = new ReentrantLock();
    private static final Condition turnChanged = lock.newCondition();

    private static boolean isPingTurn = true;
    private static int N = 5;

    public static void main(String[] args) {

        Thread pingThread = new Thread(() -> {
            for (int i = 0; i < N; i++) {
                lock.lock();                       // было: synchronized (lock)
                try {
                    while (!isPingTurn) {
                        try {
                            turnChanged.await();    // было: lock.wait()
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    System.out.println("ping");
                    isPingTurn = false;
                    turnChanged.signalAll();        // было: lock.notifyAll()
                } finally {
                    lock.unlock();                  // критично: обязателен finally
                }
            }
        });

        Thread pongThread = new Thread(() -> {
            for (int i = 0; i < N; i++) {
                lock.lock();
                try {
                    while (isPingTurn) {
                        try {
                            turnChanged.await();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    System.out.println("pong");
                    isPingTurn = true;
                    turnChanged.signalAll();
                } finally {
                    lock.unlock();
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