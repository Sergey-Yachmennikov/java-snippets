package miltithreading;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ForkJoinPool;

class ForkJoinExamples {

    private ForkJoinExamples() {}

    @Test
    void fibonacciTaskTest() {
        try (ForkJoinPool pool = new ForkJoinPool()) {
            System.out.println(pool.invoke(new FibonacciTask(20))); // 6765
        }
    }
}
