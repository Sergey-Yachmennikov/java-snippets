package leetcode_tasks.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ArrayTasksUtil {

    private ArrayTasksUtil() {}

    // Intervals tasks
    public static int[][] merge(int[][] arr) {
        if (arr.length == 0) return new int[0][0];
        Arrays.sort(arr, Comparator.comparingInt(a -> a[0]));
        List<List<Integer>> preResult = new ArrayList<>();
        for (int[] set : arr) {
            // if 1 value from set > 2 value from previous set
            if (preResult.isEmpty() || set[0] > preResult.getLast().get(1)) {
                preResult.add(Arrays.asList(set[0], set[1]));
            } else {
                preResult.getLast().set(1, Math.max(preResult.getLast().get(1), set[1]));
            }
        }

        // Convert List<List<Integer>> to int[][]
        int[][] result = new int[preResult.size()][2];
        for (int i = 0; i < preResult.size(); i++) {
            result[i][0] = preResult.get(i).get(0);
            result[i][1] = preResult.get(i).get(1);
        }

        return result;
    }

    public static int[][] mergeV2(int[][] intervals) {
        int n = intervals.length;
        if (n == 0) return new int[0][0];

        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        ArrayList<int[]> outcome = new ArrayList<>();

        int[] currentInterval = intervals[0];
        outcome.add(currentInterval);

        for (int i = 1; i < intervals.length; i++) {
            int[] interval = intervals[i];

            if (currentInterval[1] >= interval[0]) { // Overlapping intervals, merge them
                currentInterval[1] = Math.max(currentInterval[1], interval[1]);
            } else {
                currentInterval = interval;
                outcome.add(currentInterval);
            }
        }

        return outcome.toArray(new int[outcome.size()][]);
    }
}
