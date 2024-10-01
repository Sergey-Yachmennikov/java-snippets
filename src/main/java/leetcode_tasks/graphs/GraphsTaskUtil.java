package leetcode_tasks.graphs;

public class GraphsTaskUtil {

    private GraphsTaskUtil() {}

    public static int findJudge(int n, int[][] trust) {
        int[] in = new int[n + 1];
        int[] out = new int[n + 1];

        for (int[] ints : trust) {
            out[ints[0]] += 1;
            in[ints[1]] += 1;
        }

        for (int i = 1; i <= n; i++){
            if (in[i] == n - 1 && out[i] == 0)
                return i;
        }

        return -1;
    }
}
