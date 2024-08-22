package dynamic_programming;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BackpackTask {

    private BackpackTask() {}

    public static List<Integer> fillTheBackpack(int[] weights, int[] prices, int maxWeight){
        int count = weights.length;
        int[][]A = new int[count + 1][];

        for (int i = 0; i < count + 1; i++) {
            A[i] = new int[maxWeight + 1];
        }

        for (int k = 0; k <= count; k++) {
            for (int s = 0; s <= maxWeight ; s++) {
                if (k == 0 || s == 0) {
                    A[k][s] = 0;
                } else {
                    if (s >= weights[k - 1]) {
                        A[k][s] = Math.max(A[k - 1][s], A[k - 1][s - weights[k - 1]] + prices[k - 1]);
                    } else {
                        A[k][s] = A[k - 1][s];
                    }
                }
            }
        }

        List<Integer> result = new ArrayList<>();
        traceResult(A, weights, count, maxWeight, result);
        Collections.sort(result);

        return result;
    }

    private static void traceResult(int[][] A, int[] weight, int k, int s, List<Integer> result) {
        if (A[k][s] == 0) {
            return;
        }

        if (A[k - 1][s] == A[k][s]) {
            traceResult(A, weight, k - 1, s, result);
        } else {
            traceResult(A, weight, k - 1, s - weight[k - 1], result);
            result.addFirst(k);
        }
    }
}
