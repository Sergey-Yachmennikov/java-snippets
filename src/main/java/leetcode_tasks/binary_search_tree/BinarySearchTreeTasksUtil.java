package leetcode_tasks.binary_search_tree;

import data_structures.leetcode.TreeNode;

import java.util.*;

public class BinarySearchTreeTasksUtil {

    private BinarySearchTreeTasksUtil() {}

    public static boolean findSumOfTwo(TreeNode root, int k) {
        Set<Integer> set = new HashSet<>();
        return checkSum(root, set, k);
    }

    private static boolean checkSum(TreeNode node, Set<Integer> set, int target) {
        if (node == null) return false;

        if (set.contains(node.val)) return true;
        set.add(target - node.val);

        return checkSum(node.left, set, target) || checkSum(node.right, set, target);
    }

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

    public static boolean isValidBST(TreeNode root) {
        boolean[] is = {true};
        int[] prev = {Integer.MIN_VALUE};
        inorder(root, is, prev);
        return is[0];
    }

    private static void inorder(TreeNode node, boolean[] is, int[] prev) {
        if (node == null) return;
        inorder(node.left, is, prev);
        if (node.val <= prev[0]) is[0] = false;
        prev[0] = node.val;
        inorder(node.right, is, prev);
    }
}
