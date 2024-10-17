package leetcode_tasks.backtracking;

import data_structures.leetcode.TreeNode;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BacktrackingUtilTest {

    @Test
    void binaryTreePaths() {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.left.left = new TreeNode(5);
        root.right = new TreeNode(3);
        List<String> expected = new ArrayList<>(List.of("1->2->5","1->3"));
        List<String> result = BacktrackingUtil.binaryTreePaths(root);

        for (int i = 0; i < result.size(); i++) {
            assertEquals(expected.get(i), result.get(i));
        }
    }
}