package remote_preparation.multithreating;

public class VolatileQuestion {

    private static int nonVolatileResult = 0;
    private static volatile boolean done = false;

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            nonVolatileResult = 13;
            done = true;
        });
        thread.start();
        while(!done) {
            Thread.onSpinWait();
        }
        System.out.println(nonVolatileResult);
    }
}
