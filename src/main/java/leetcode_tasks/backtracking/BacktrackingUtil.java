package leetcode_tasks.backtracking;

import data_structures.leetcode.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class BacktrackingUtil {

    private BacktrackingUtil() {}

    public static List<String> binaryTreePaths(TreeNode root) {
        List<String> paths = new ArrayList<>();
        List<Integer> curPath = new ArrayList<>();
        dfs(root, paths, curPath);
        return paths;
    }

    private static void dfs(TreeNode root, List<String> paths, List<Integer> curPath) {
        if (root == null) return;
        curPath.add(root.val);
        if (root.left == null && root.right == null) paths.add(pathToString(curPath));
        if (root.left != null) dfs(root.left, paths, curPath);
        if (root.right != null) dfs(root.right, paths, curPath);
        curPath.removeLast();
    }

    private static String pathToString(List<Integer> path) {
        StringBuilder sb = new StringBuilder();
        sb.append(path.getFirst());
        for (int i = 1; i < path.size(); i++) sb.append("->").append(path.get(i));
        return sb.toString();
    }

    public static List<String> readBinaryWatch(int turnedOn) {
        var result = new ArrayList<String>();

        for (int i = 0; i < 12; i++)
            for (int j = 0; j < 60; j++)
                if ((Integer.bitCount(i) + Integer.bitCount(j)) == turnedOn)
                    result.add(i + ":" + (j < 10 ? "0" + j : j));

        return result;
    }
}
