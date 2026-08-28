package remote_preparation.multithreating;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.*;

public class PingPongLockCondition {

    private final Lock lock = new ReentrantLock();
    private final Condition pingTurn = lock.newCondition();
    private final Condition pongTurn = lock.newCondition();
    private boolean isPingTurn = true;
    private final int iterations;

    private final List<String> log = Collections.synchronizedList(new ArrayList<>());

    public PingPongLockCondition(int iterations) {
        this.iterations = iterations;
    }

    public List<String> start() throws InterruptedException {
        Thread ping = new Thread(() -> play("ping", true), "ping-thread");
        Thread pong = new Thread(() -> play("pong", false), "pong-thread");

        ping.start();
        pong.start();
        ping.join();
        pong.join();

        return List.copyOf(log);
    }

    private void play(String message, boolean isPing) {
        Condition myTurn = isPing ? pingTurn : pongTurn;
        Condition otherTurn = isPing ? pongTurn : pingTurn;

        for (int i = 0; i < iterations; i++) {
            lock.lock();
            try {
                while (isPingTurn != isPing) {
                    myTurn.await();
                }
                log.add(message);              // вместо System.out.println
                isPingTurn = !isPing;
                otherTurn.signal();
                // end
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            } finally {
                lock.unlock();
            }
        }
    }
}