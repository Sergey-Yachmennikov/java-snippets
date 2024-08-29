package leetcode_tasks.binary_search_tree;

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
}