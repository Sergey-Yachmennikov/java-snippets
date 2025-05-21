package miltithreading;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.LongStream;

import static miltithreading.ParallelSum.dummyBenchmark;
import static miltithreading.ParallelSum.sequentialSum;

class ParallelSumTest {

    @Test
    void testParallelSum() {
        var nums = LongStream.range(0, 5_000_000L)
                .mapToObj(BigInteger::valueOf)
                .toList();

// 		Run one then comment and run another
        Runnable parallel = () -> {
            var commonPool = ForkJoinPool.commonPool();
            var result = commonPool.invoke(new ParallelSum(nums));

            System.out.println("Parallel Result is: " + result);
        };

        Runnable sequential = () -> {
            var acc = sequentialSum(nums);

            System.out.println("Sequential Result is: " + acc);
        };

        sequential.run();
        parallel.run();


        System.out.println("#### After some JIT \n\n");

        dummyBenchmark(sequential);
        dummyBenchmark(parallel);

        System.out.println("#### After more JIT \n\n");

        dummyBenchmark(sequential);
        dummyBenchmark(parallel);
    }

}