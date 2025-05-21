package miltithreading;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class ParallelSum extends RecursiveTask<BigInteger> {

    private static final int THRESHOLD = 10_000; // Choosing a number to split the computation
    private final List<BigInteger> nums;

    public ParallelSum(List<BigInteger> nums) {
        this.nums = nums;
    }

    @Override
    protected BigInteger compute() {
        var size = nums.size();
        if (size < THRESHOLD) {
            return sequentialSum(nums);
        } else {
            var x = new ParallelSum(nums.subList(0, size / 2));
            var y = new ParallelSum(nums.subList(size / 2, size));

            x.fork();
            y.fork();

            var xResult = x.join();
            var yResult = y.join();

            return yResult.add(xResult);
        }
    }

    public static BigInteger sequentialSum(List<BigInteger> nums) {
        var acc = BigInteger.ZERO;
        for (var value : nums) acc = acc.add(value);
        return acc;
    }

    public static void dummyBenchmark(Runnable runnable) {
        var before = System.currentTimeMillis();
        runnable.run();
        var after = System.currentTimeMillis();
        System.out.println("Executed in: " + (after - before));
        System.out.println("######\n");
    }
}