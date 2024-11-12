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
            if (in[i] == n - 1 && out[i] == 0) return i;
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

        // Perform union operations
        for (int i = 0; i < equations.size(); i++) {
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

        double[] answers = new double[queries.size()];

        // Evaluate each query
        for (int i = 0; i < queries.size(); i++) {
            String varC = queries.get(i).get(0);
            String varD = queries.get(i).get(1);
            if (!parent.containsKey(varC) || !parent.containsKey(varD) ||
                !Objects.equals(find(varC, parent, weight), find(varD, parent, weight))
            ) {
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

    public static int findCircleNum(int[][] connections) {
        int count = 0;
        boolean[] visited = new boolean[connections.length];

        for (int i = 0; i < connections.length; i++) {
            if (!visited[i]) {
                count++;
                circleDfs(i, visited, connections);
            }
        }

        return count;
    }

    private static void circleDfs(int row, boolean[] visited, int[][] connections) {
        visited[row] = true;

        for (int j = 0; j < connections[row].length; j++) {
            if (connections[row][j] == 1 && !visited[j]) circleDfs(j, visited, connections);
        }
    }

    public static int countComponents(int n, int[][] edges) {
        boolean[] visited = new boolean[n];
        List<List<Integer>> adjList = new ArrayList<>(n);

        for (int i = 0; i < n; i++) adjList.add(new ArrayList<>());

        for (int[] edge: edges) {
            adjList.get(edge[0]).add(edge[1]);
            adjList.get(edge[1]).add(edge[0]);
        }

        int componentsCount = 0;

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                componentsCount++;
                countComponentsDfs(visited, adjList, i);
            }
        }

        return componentsCount;
    }

    private static void countComponentsDfs(boolean[] visited, List<List<Integer>> adjList, int node) {
        visited[node] = true;
        List<Integer> adjacentNodes = adjList.get(node);

        for (int n: adjacentNodes) {
            if (!visited[n]) countComponentsDfs(visited, adjList, n);
        }
    }

    public static int countComponentsUF(int n, int[][] edges) {
        int[] roots = new int[n];
        for (int i = 0; i < n; i++) roots[i] = i;
        int count = n;

        for (int[] edge : edges) {
            int root1 = findRoot(edge[0], roots);
            int root2 = findRoot(edge[1], roots);

            if (root1 != root2) {
                roots[root1] = root2;
                count--;
            }
        }

        return count;
    }

    public static int findRoot(int node, int[] roots) {
        while (node != roots[node]) {
            roots[node] = roots[roots[node]];
            node = roots[node];
        }

        return node;
    }

    public static int[] findRedundantConnection(int[][] edges) {
        DisjointSet ds = new DisjointSet(edges.length + 1);

        for (int[] a : edges) {
            if(!ds.unionBySize(a[0], a[1])) return a;
        }

        return new int[0];
    }

    private static class DisjointSet {
        private final int[] parent;
        private final int[] size;

        public DisjointSet(int n) {
            parent = new int[n + 1];
            size = new int[n + 1];

            for (int i = 0; i <= n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        public int findUPar(int node) {
            if (node == parent[node]) return node;
            int ulp = findUPar(parent[node]);
            parent[node] = ulp;
            return parent[node];
        }

        public boolean unionBySize(int u,int v){
            int ulp_u = findUPar(u);
            int ulp_v = findUPar(v);
            if(ulp_v == ulp_u) return false;
            if (size[ulp_u] < size[ulp_v]) {
                parent[ulp_u] = ulp_v;
                size[ulp_v] = size[ulp_u] + size[ulp_v];
            } else {
                parent[ulp_v] = ulp_u;
                size[ulp_u] = size[ulp_u] + size[ulp_v];
            }

            return true;
        }
    }
}
