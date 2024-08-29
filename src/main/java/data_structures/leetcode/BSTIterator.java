package data_structures.leetcode;

import java.util.LinkedList;
import java.util.Queue;

class BSTIterator {
    private final Queue<Integer> list = new LinkedList<>();

    public BSTIterator(TreeNode root) {
        traverseBST(root, list);
    }

    public int next() {
        return list.poll();
    }

    public boolean hasNext() {
        return !list.isEmpty();
    }

    private void traverseBST(TreeNode node, Queue<Integer> list) {
        if (node == null) return;

        traverseBST(node.left, list);
        list.add(node.val);
        traverseBST(node.right, list);
    }
}
