package remote_preparation.multithreating;

public class PingPongWaitNotify {

    private final Object lock = new Object();
    private boolean pingTurn = true;   // ping ходит первым
    private final int iterations;

    public PingPongWaitNotify(int iterations) {
        this.iterations = iterations;
    }

    public void start() throws InterruptedException {
        Thread ping = new Thread(() -> play("ping", true), "ping-thread");
        Thread pong = new Thread(() -> play("pong", false), "pong-thread");

        ping.start();
        pong.start();

        ping.join();
        pong.join();
    }

    public void play(String message, boolean isPing) {
        for (int i = 0; i < iterations; i++) {
            synchronized (lock) {
                while (pingTurn != isPing) {       // while, не if!
                    try {
                        System.out.println(message);
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }

                pingTurn = !pingTurn;
                System.out.println(message);
                lock.notifyAll();
            }
        }
    }
}
