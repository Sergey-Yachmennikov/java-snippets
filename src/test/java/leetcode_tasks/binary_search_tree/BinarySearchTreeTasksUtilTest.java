package leetcode_tasks.binary_search_tree;

import data_structures.leetcode.ListNode;
import data_structures.leetcode.TreeNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BinarySearchTreeTasksUtilTest {

    @Test
    void findSumOfTwo() {
        TreeNode root = new TreeNode(5);

        root.left = new TreeNode(3);
        root.right = new TreeNode(6);

        root.left.left = new TreeNode(2);
        root.left.right = new TreeNode(4);

        root.right.right = new TreeNode(7);

        assertTrue(BinarySearchTreeTasksUtil.findSumOfTwo(root, 9));
    }



    @Test
    void isValidBST() {
        TreeNode root = new TreeNode(2);

        root.left = new TreeNode(1);
        root.right = new TreeNode(3);
        assertTrue(BinarySearchTreeTasksUtil.isValidBST(root));
    }

    @Test
    void recoverTree() {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(2);
        root.right = new TreeNode(1);

        BinarySearchTreeTasksUtil.recoverTree(root);
        BinarySearchTreeTasksUtil.showOrder(root);
    }

    @Test
    void transformLinkedListToBST() {
        ListNode head = new ListNode(-10);
        head.next = new ListNode(-3);
        head.next.next = new ListNode(0);
        head.next.next.next = new ListNode(5);
        head.next.next.next.next = new ListNode(9);

        TreeNode root = BinarySearchTreeTasksUtil.transformLinkedListToBST(head);
        BinarySearchTreeTasksUtil.showOrder(root);
    }

    @Test
    void serialization() {
        TreeNode root = new TreeNode(2);
        root.left = new TreeNode(1);
        root.right = new TreeNode(3);

        String serialized = BinarySearchTreeTasksUtil.serialize(root);
        assertEquals("2,1,3", serialized);
    }

    @Test
    void bfsFromPreorderArray() {
        TreeNode tree = BinarySearchTreeTasksUtil.bstFromPreorder(new int[]{8, 5, 1, 7, 10, 12});
        assertEquals(8, tree.val);
        assertEquals(5, tree.left.val);
        assertEquals(1, tree.left.left.val);
        assertEquals(7, tree.left.right.val);
        assertEquals(10, tree.right.val);
        assertEquals(12, tree.right.right.val);
    }
}