package remote_preparation.multithreating;

import java.util.concurrent.SynchronousQueue;

public class PingPongQueue {

    public void start(int iterations) throws InterruptedException {
        SynchronousQueue<String> queue = new SynchronousQueue<>();

        Thread ping = new Thread(() -> {
            try {
                for (int i = 0; i < iterations; i++) {
                    System.out.println("ping");
                    queue.put("token");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread pong = new Thread(() -> {
            try {
                for (int i = 0; i < iterations; i++) {
                    queue.take();
                    System.out.println("pong");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        ping.start();
        pong.start();
        ping.join();
        pong.join();
    }
}