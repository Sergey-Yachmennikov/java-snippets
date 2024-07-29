package math;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class FibonacciStarter {

    static Map<Integer, Integer> memo = new HashMap<>(Map.of(0, 0, 1, 1 ));
    private static int last = 0, next = 1;

    public static int fibonacciRecursive(int n) {
        if (!memo.containsKey(n)) {
            memo.put(n, fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2));
        }

        return memo.get(n);
    }

    public static int fibonacciLinear(int n) {
        int last = 0, next = 1;
        for (int i = 0; i < n; i++) {
            int oldLast = last;
            last = next;
            next = oldLast + next;
        }
        return last;
    }

    public static int fibonacciStream(int n) {
        return IntStream.generate(() -> {
            int oldLast = last;
            last = next;
            next = oldLast + next;
            return last;
        })
                .limit(n)
                .reduce(0, (a, b) -> b);
    }
}
