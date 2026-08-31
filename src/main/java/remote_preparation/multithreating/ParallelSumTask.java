package remote_preparation.multithreating;

import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSumTask {

    private static final int THRESHOLD = 100_000;

    public static void main(String[] args) throws Exception {
        // Создание массива
        int[] array = new int[10_000_000];
        Random random = new Random(42L);
        // фиксированный seed — все варианты считают один и тот же массив
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(100);
        }

        // Измерение времени для однопоточного решения
        long start = System.currentTimeMillis();
        long singleThreadSum = singleThreadSum(array);
        long singleThreadTime = System.currentTimeMillis() - start;
        System.out.println("Single thread sum: " + singleThreadSum);
        System.out.println("Single thread time: " + singleThreadTime + " ms");


        // Доп. усложнение: ForkJoinPool для сравнения
        start = System.currentTimeMillis();
        long parallelTime = System.currentTimeMillis() - start;
        long forkJoinSum = forkJoinSum(array);
        long forkJoinTime = System.currentTimeMillis() - start;
        System.out.println("\nForkJoinPool sum: " + forkJoinSum);
        System.out.println("ForkJoinPool time: " + forkJoinTime + " ms");
        System.out.println("Correct: " + (forkJoinSum == singleThreadSum));

        System.out.printf(Locale.ROOT, "%nSpeedup ExecutorService: %.2fx%n",
                (double) singleThreadTime / Math.max(parallelTime, 1));
        System.out.printf(Locale.ROOT, "Speedup ForkJoinPool:    %.2fx%n",
                (double) singleThreadTime / Math.max(forkJoinTime, 1));

    }


    private static long singleThreadSum(int[] array) {
        long sum = 0;
        for (int value : array) {
            sum += value;
        }
        return sum;
    }

    private static final class SumTask extends RecursiveTask<Long> {

        private final int[] array;
        private final int from;
        private final int to;

        public SumTask(int[] array, int from, int to) {
            this.array = array;
            this.from = from;
            this.to = to;
        }

        @Override
        protected Long compute() {
            int length = to - from;

            if (length <= THRESHOLD) {
                long sum = 0;
                for (int i = from; i < to; i++) {
                    sum += array[i];
                }
                return sum;
            }

            int mid = from + length / 2;
            SumTask left = new SumTask(array, from, mid);
            SumTask right = new SumTask(array, mid, to);

            left.fork();
            long rightResult = right.compute();
            long leftResult = left.join();
            return leftResult + rightResult;
        }
    }

    private static long forkJoinSum(int[] array) {
        try (ForkJoinPool pool = new ForkJoinPool()) {
            return pool.invoke(new SumTask(array, 0, array.length));
        }
    }

}
