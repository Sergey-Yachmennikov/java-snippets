package data_structures.leetcode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BSTIteratorTest {

    @Test
    void BSTIteratorTestWorkTest() {
        TreeNode root = new TreeNode(0);
        root.left = new TreeNode(-3);
        root.left.left = new TreeNode(-10);
        root.right = new TreeNode(5);
        root.right.right = new TreeNode(9);
        BSTIterator iterator = new BSTIterator(root);
        assertEquals(-10, iterator.next());
        assertEquals(-3, iterator.next());
        assertEquals(0, iterator.next());
        assertEquals(5, iterator.next());
        assertEquals(9, iterator.next());
        assertFalse(iterator.hasNext());
    }

}