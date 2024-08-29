package leetcode_tasks.binary_search_tree;

import data_structures.leetcode.TreeNode;

import java.util.*;

public class BinarySearchTreeTasksUtil {

    private BinarySearchTreeTasksUtil() {}

    // check sum of two element in tree task
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

    // check valid order of element in tree
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
