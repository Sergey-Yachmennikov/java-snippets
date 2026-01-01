package tasks;

public class X5Task {

    public static class TestClass extends Thread {

        private static int threadcounter = 0;
        // use AtomicInteger
        // concurrent structure
        // explicit lock

        public void run() {
            ++threadcounter;
            System.out.println(threadcounter);
        }

        public static void main(String[] args) throws Exception {
            for (int i = 0; i < 10; i++) {
                synchronized (TestClass.class) {
                    new TestClass().start();
                }
            }
        }
    }
}
