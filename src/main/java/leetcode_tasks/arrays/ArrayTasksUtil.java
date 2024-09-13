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

    public static int[][] insertInterval(int[][] intervals, int[] newInterval) {
        int till = -1, from = -1;
        int n = intervals.length;
        int[][] result;

        // case if length == 0
        if (n == 0) {
            result = new int[1][2];
            result[0] = newInterval;
            return result;
        }

        // case if new interval less than first element of intervals arr
        if (newInterval[1] < intervals[0][0]) {
            result = new int[n + 1][2];
            result[0] = newInterval;
            System.arraycopy(intervals, 0, result, 1, n);
            return result;
        }

        for (int i = 0; i < n; i++) {
            if (till == -1 && intervals[i][1] >= newInterval[0]) till = i;

            if (newInterval[1] < intervals[i][0]) {
                from = i - 1;
                break;
            }

            if (newInterval[1] == intervals[i][0]) {
                from = i;
                break;
            }
        }

        if (till == -1) {
            result = new int[n + 1][2];
            System.arraycopy(intervals, 0, result, 0, n);
            result[n] = newInterval;
            return result;
        }

        from = from == -1 ? n - 1 : from;
        newInterval[0] = Math.min(newInterval[0], intervals[till][0]);
        newInterval[1] = Math.max(newInterval[1], intervals[from][1]);

        result = new int[till + n - from][2];
        result[till] = newInterval;
        System.arraycopy(intervals, 0, result, 0, till);
        System.arraycopy(intervals, from + 1, result, till + 1, n - from - 1);

        return result;
    }
}
