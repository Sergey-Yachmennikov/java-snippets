package leetcode_tasks.search.dfs;

import data_structures.leetcode.TreeNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DfsSearchUtilTest {

    @Test
    void pathSum() {
        TreeNode root = new TreeNode(5);

        root.left = new TreeNode(4);
        root.right = new TreeNode(8);

        root.left.left = new TreeNode(11);

        root.left.left.left = new TreeNode(7);
        root.left.left.right = new TreeNode(12);

        root.right.left = new TreeNode(13);
        root.right.right = new TreeNode(4);

        root.right.right.left = new TreeNode(5);
        root.right.right.right = new TreeNode(1);

        int[] expected = new int[]{5,8,4,5};
        List<List<Integer>> result = DfsSearchUtil.pathSum(root, 22);
        for (int i = 0; i < result.size(); i++) assertEquals(expected[i], result.getFirst().get(i));
    }
}