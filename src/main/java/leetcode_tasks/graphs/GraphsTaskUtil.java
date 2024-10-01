package leetcode_tasks.graphs;

import java.util.HashMap;
import java.util.Map;

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

    public static int findCenter(int[][] edges) {
        Map<Integer, Integer> degree = new HashMap<>();

        for (int[] edge : edges) {
            degree.put(edge[0], degree.getOrDefault(edge[0], 0) + 1);
            degree.put(edge[1], degree.getOrDefault(edge[1], 0) + 1);
        }

        for (int node : degree.keySet()) {
            if (degree.get(node) == edges.length) return node;
        }

        return -1;
    }

    public static int findCenterConstant(int[][] edges) {
        int[] firstEdge = edges[0];
        int[] secondEdge = edges[1];

        return (firstEdge[0] == secondEdge[0] || firstEdge[0] == secondEdge[1])
                ? firstEdge[0]
                : firstEdge[1];
    }
}
