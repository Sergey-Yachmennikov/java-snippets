package miltithreading;

import java.util.concurrent.RecursiveTask;

public class FibonacciTask extends RecursiveTask<Long> {

    private final long n;

    FibonacciTask(long n) { this.n = n; }

    @Override
    protected Long compute() {
        if (n <= 10) { // Базовый случай
            return sequentialFibonacci(n);
        }
        FibonacciTask f1 = new FibonacciTask(n - 1);
        f1.fork(); // Асинхронный запуск подзадачи

        FibonacciTask f2 = new FibonacciTask(n - 2);
        return f2.compute() + f1.join(); // Объединение результатов
    }

    private long sequentialFibonacci(long n) {
        if (n <= 1) return n;
        return sequentialFibonacci(n - 1) + sequentialFibonacci(n - 2);
    }
}
