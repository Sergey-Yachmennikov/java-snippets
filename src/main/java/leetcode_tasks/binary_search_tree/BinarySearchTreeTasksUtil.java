package leetcode_tasks.binary_search_tree;

import data_structures.leetcode.ListNode;
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

    public static TreeNode transformLinkedListToBST(ListNode head) {
        if (head == null) return null;
        if (head.next == null) return new TreeNode(head.val);

        // two pointers
        var slow = head;
        var fast = head.next.next;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        TreeNode root = new TreeNode(slow.next.val);
        ListNode rightPartStartNode = slow.next.next;
        slow.next = null;

        root.left = transformLinkedListToBST(head);
        root.right = transformLinkedListToBST(rightPartStartNode);

        return root;
    }

    public static String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        int largest = largestItem(root).val;
        preorder(root, sb, largest);
        return sb.toString();
    }

    private static void preorder(TreeNode node, StringBuilder sb, int largest) {
        if (node == null) return;

        sb.append(node.val);
        sb.append(node.val != largest ? "," : "");
        preorder(node.left, sb, largest);
        preorder(node.right, sb, largest);
    }

    private static TreeNode largestItem(TreeNode node) {
        return node.right == null ? node : largestItem(node.right);
    }

    public static TreeNode bstFromPreorder(int[] values) {
        TreeNode root = new TreeNode(values[0]);
        for (int i = 1; i < values.length; i++) {
            bstFromPreorder(root, values[i]);
        }

        return root;
    }

    private static TreeNode bstFromPreorder(TreeNode node, int value) {
        if (node == null) return new TreeNode(value);

        if (node.val > value) {
            node.left = bstFromPreorder(node.left, value);
        } else {
            node.right = bstFromPreorder(node.right, value);
        }

        return node;
    }

    // merge 2 trees to 1 list
    public static List<Integer> getAllElements(TreeNode root1, TreeNode root2) {
        List<Integer> l1 = new ArrayList<>();
        List<Integer> l2 = new ArrayList<>();

        inorderToList(root1, l1);
        inorderToList(root2, l2);

        List<Integer> result = new ArrayList<>();
        int i = 0, j = 0;

        while(i < l1.size() && j < l2.size()) {
            if (l1.get(i) > l2.get(j)) {
                result.add(l2.get(j++));
            } else {
                result.add(l1.get(i++));
            }
        }

        while(i < l1.size()) result.add(l1.get(i++));
        while(j < l2.size()) result.add(l2.get(j++));

        return result;
    }

    private static void inorderToList(TreeNode root, List<Integer> list){
        if(root == null) return;
        inorderToList(root.left, list);
        list.add(root.val);
        inorderToList(root.right, list);
    }
}
