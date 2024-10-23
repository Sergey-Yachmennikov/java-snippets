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

    @Test
    void subsetXORSum() {
        assertEquals(6, BacktrackingUtil.subsetXORSum(new int[] {1,3}));
        assertEquals(28, BacktrackingUtil.subsetXORSum(new int[] {5,1,6}));
        assertEquals(480, BacktrackingUtil.subsetXORSum(new int[] {3,4,5,6,7,8}));

    }

    @Test
    void subsetXORSum2() {
        assertEquals(28, BacktrackingUtil.subsetXORSum2(new int[] {2,5,6}));
    }

    @Test
    void printSubsets() {
        assertEquals(16, BacktrackingUtil.printSubsets(new char[] {'a', 'b', 'c', 'd'}));
        assertEquals(32, BacktrackingUtil.printSubsets(new char[] {'a', 'b', 'c', 'd', 'e'}));
    }

    @Test
    void letterCombinations() {
        List<String> expected1 = new ArrayList<>(List.of("ad","ae","af","bd","be","bf","cd","ce","cf"));
        List<String> result1 = BacktrackingUtil.letterCombinations("23");
        for (int i = 0; i < expected1.size(); i++) assertEquals(expected1.get(i), result1.get(i));
        assertEquals(0, BacktrackingUtil.letterCombinations("").size());
        List<String> expected3 = new ArrayList<>(List.of("a","b","c"));
        List<String> result3 = BacktrackingUtil.letterCombinations("2");
        for (int i = 0; i < expected3.size(); i++) assertEquals(expected3.get(i), result3.get(i));
    }
}