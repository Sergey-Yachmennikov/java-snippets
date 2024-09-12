package leetcode_tasks.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayTasksUtil {

    private ArrayTasksUtil() {}

    // Intervals tasks
    public static int[][] merge(int[][] arr) {
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
}
