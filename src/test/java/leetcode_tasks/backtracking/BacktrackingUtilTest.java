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
        List<String> expected = new ArrayList<>(List.of("1->2->5", "1->3"));
        List<String> result = BacktrackingUtil.binaryTreePaths(root);

        for (int i = 0; i < result.size(); i++) {
            assertEquals(expected.get(i), result.get(i));
        }
    }

    @Test
    void readBinaryWatch() {
        List<String> result1 = BacktrackingUtil.readBinaryWatch(1);
        List<String> result2 = BacktrackingUtil.readBinaryWatch(9);
        List<String> expected = new ArrayList<>(List.of("0:01", "0:02", "0:04", "0:08", "0:16", "0:32", "1:00", "2:00", "4:00", "8:00"));

        for (int i = 0; i < result1.size(); i++) assertEquals(expected.get(i), result1.get(i));
        assertEquals(0, result2.size());
    }
}