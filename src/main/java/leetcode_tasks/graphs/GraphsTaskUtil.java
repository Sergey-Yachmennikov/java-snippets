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

    public static GraphNode cloneGraph(GraphNode node) {
        if(node == null) return null;
        HashMap<GraphNode, GraphNode> map = new HashMap<>();
        return cloneGraphNode(node, map);
    }

    private static GraphNode cloneGraphNode(GraphNode node, HashMap<GraphNode, GraphNode> map) {
        GraphNode newNode = new GraphNode(node.val);
        map.put(node, newNode);

        for(GraphNode n : node.neighbors) {
            if(!map.containsKey(n)) {
                newNode.neighbors.add(cloneGraphNode(n, map));
            } else {
                newNode.neighbors.add(map.get(n));
            }
        }

        return newNode;
    }

    public static List<Integer> findMinHeightTrees(int n, int[][] edges) {
        List<Integer> result = new ArrayList<>();

        if (n == 0) return result;

        if (n == 1) {
            result.add(0);
            return result;
        }

        List<List<Integer>> adjList = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adjList.add(new ArrayList<>());

        // degree of element edges: [1, 3, 1, 1]
        int[] degree = new int[n];

        for (int[] edge : edges) {
            adjList.get(edge[0]).add(edge[1]);
            adjList.get(edge[1]).add(edge[0]);
            degree[edge[0]]++;
            degree[edge[1]]++;
        }

        // leaves = [0, 2, 3] - indexes of element with degree 1
        Queue<Integer> leaves = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (degree[i] == 1) leaves.offer(i);
        }

        while (n > 2) {
            int size = leaves.size();
            n -= size;
            for (int i = 0; i < size; i++) {
                int leaf = leaves.poll();
                for (int neighbor : adjList.get(leaf)) {
                    if (--degree[neighbor] == 1) {
                        leaves.offer(neighbor);
                    }
                }
            }
        }
        result.addAll(leaves);

        return result;
    }

    public static double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        // Initialize the parent and weight maps for union-find structure
        Map<String, String> parent = new HashMap<>();
        Map<String, Double> weight = new HashMap<>();

        // Create a union-find structure to represent the equations
        for (List<String> equation : equations) {
            parent.put(equation.get(0), equation.get(0));
            parent.put(equation.get(1), equation.get(1));
            weight.put(equation.get(0), 1.0);
            weight.put(equation.get(1), 1.0);
        }

        int equationCount = equations.size();

        // Perform union operations
        for (int i = 0; i < equationCount; ++i) {
            List<String> equation = equations.get(i);
            String varA = equation.get(0);
            String varB = equation.get(1);
            String parentA = find(varA, parent, weight);
            String parentB = find(varB, parent, weight);

            if (!Objects.equals(parentA, parentB)) {
                parent.put(parentA, parentB);
                weight.put(parentA, weight.get(varB) * values[i] / weight.get(varA));
            }
        }

        int queryCount = queries.size();
        double[] answers = new double[queryCount];

        // Evaluate each query
        for (int i = 0; i < queryCount; ++i) {
            String varC = queries.get(i).get(0);
            String varD = queries.get(i).get(1);
            if (!parent.containsKey(varC) || !parent.containsKey(varD) ||
                    !Objects.equals(find(varC, parent, weight), find(varD, parent, weight))) {
                // If the variables do not belong to the same set, or at least one of the
                // variables is not part of any equation, the answer is -1
                answers[i] = -1.0;
            } else {
                answers[i] = weight.get(varC) / weight.get(varD);
            }
        }

        return answers;
    }

    // The find function for the union-find structure
    private static String find(String x, Map<String, String> parent, Map<String, Double> weight) {
        if (!Objects.equals(parent.get(x), x)) {
            String originalParent = parent.get(x);
            parent.put(x, find(parent.get(x), parent, weight)); // Path compression
            weight.put(x, weight.get(x) * weight.get(originalParent));
        }
        return parent.get(x);
    }
}
