package leetcode_tasks.dynamic_programming;

import data_structures.leetcode.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class BinarySearchTreeTasksWithDPUtil {

    private BinarySearchTreeTasksWithDPUtil() {}

    /**
     * @topic Dynamic Programming, Backtracking, Binary Tree, Binary Search Tree
     */
    @SuppressWarnings("unchecked")
    public static List<TreeNode> generateTrees(int n) {
        List<TreeNode>[][] dp = new List[n + 1][n + 1];
        return addBst(1, n, dp);
    }

    private static List<TreeNode> addBst(int low, int high, List<TreeNode>[][] dp) {
        if (low > high) {
            List<TreeNode> list = new ArrayList<>();
            list.add(null);
            return list;
        }

        if (dp[low][high] != null) {
            return dp[low][high];
        }

        List<TreeNode> list = new ArrayList<>();
        for (int i = low; i <= high; i++) {
            List<TreeNode> leftSubtree = addBst(low, i - 1, dp);
            List<TreeNode> rightSubtree = addBst(i + 1, high, dp);

            for (TreeNode left : leftSubtree) {
                for (TreeNode right : rightSubtree) {
                    list.add(new TreeNode(i, left, right));
                }
            }
        }
        dp[low][high] = list;


        return dp[low][high];
    }
}
