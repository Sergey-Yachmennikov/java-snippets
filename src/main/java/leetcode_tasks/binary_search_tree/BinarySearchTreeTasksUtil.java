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
        validateInorder(root, is, prev);
        return is[0];
    }

    private static void validateInorder(TreeNode node, boolean[] is, int[] prev) {
        if (node == null) return;
        validateInorder(node.left, is, prev);
        if (node.val <= prev[0]) is[0] = false;
        prev[0] = node.val;
        validateInorder(node.right, is, prev);
    }

    // recover tree items order
    public static void recoverTree(TreeNode node) {
        if (node == null) return;
        recoverTree(node.left);

        if (node.left != null && node.val < node.left.val) {
            swap(node, node.left);
        }

        if (node.right != null && node.val > node.right.val) {
            swap(node, node.right);
        }

        recoverTree(node.right);
    }

    private static void swap(TreeNode node1, TreeNode node2) {
        int temp = node1.val;
        node1.val = node2.val;
        node2.val = temp;
    }

    // common util method
    public static void showOrder(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        showOrder(root, list);
        System.out.println(list);
    }

    private static void showOrder(TreeNode node, List<Integer> list) {
        if (node == null) return;
        showOrder(node.left, list);
        list.add(node.val);
        showOrder(node.right, list);
    }
}
