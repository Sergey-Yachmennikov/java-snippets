package leetcode_tasks.graphs;

import java.util.*;

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

    public static boolean validPath(int n, int[][] edges, int source, int destination) {
        // Create adjacency list representation of the graph
        List<List<Integer>> adjacencyList = new ArrayList<>();

        for (int i = 0; i < n; i++) adjacencyList.add(new ArrayList<>());

        for (int[] edge : edges) {
            adjacencyList.get(edge[0]).add(edge[1]);
            adjacencyList.get(edge[1]).add(edge[0]);
        }

        // Perform BFS traversal to find if there's a valid path from source to destination
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[n]; // n represents count of vertices
        queue.offer(source);
        visited[source] = true;

        while (!queue.isEmpty()) {
            int current = queue.poll();
            if (current == destination) return true; // Found a path

            for (int neighbor : adjacencyList.get(current)) {
                if (!visited[neighbor]) {
                    queue.offer(neighbor);
                    visited[neighbor] = true;
                }
            }
        }

        return false;
    }
}
