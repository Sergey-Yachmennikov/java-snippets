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

    @Test
    void calculateSubsets() {
        List<List<Integer>> result = BacktrackingUtil.calculateSubsets(new int[]{1,2,3});
        System.out.println(result);

        List<List<Integer>> result2 = BacktrackingUtil.calculateSubsetsIterative(new int[]{1,2,3});
        System.out.println(result2);
    }

    @Test
    void generateParenthesis() {
        List<String> result1 = BacktrackingUtil.generateParenthesis(3);
        List<String> expected1 = List.of("((()))","(()())","(())()","()(())","()()()");
        for (int i = 0; i < result1.size(); i++) assertEquals(result1.get(i), expected1.get(i));
        List<String> result2 = BacktrackingUtil.generateParenthesis(1);
        assertEquals(1, result2.size());
        assertEquals("()", result2.getFirst());
    }

    @Test
    void permute() {
        String expectedStr = "[[1, 2, 3], [1, 3, 2], [2, 1, 3], [2, 3, 1], [3, 1, 2], [3, 2, 1]]";
        List<List<Integer>> permuted = BacktrackingUtil.permute(new int[]{1, 2, 3});
        assertEquals(expectedStr, permuted.toString());
        List<List<Integer>> permuteUnique = BacktrackingUtil.permuteUnique(new int[]{1, 2, 3});
        assertEquals(expectedStr, permuteUnique.toString());
        System.out.println("Permutation result: " + permuted);
    }

    @Test
    void combinationSum() {
        String outputString1 = "[[2, 2, 3], [7]]";
        assertEquals(outputString1, BacktrackingUtil.combinationSum(new int[]{2,3,6,7}, 7).toString());

        String outputString2 = "[[2, 2, 2, 2], [2, 3, 3], [3, 5]]";
        assertEquals(outputString2, BacktrackingUtil.combinationSum(new int[]{2,3,5}, 8).toString());

        List<List<Integer>> lists = BacktrackingUtil.combinationSum(new int[]{10, 1, 2, 7, 6, 1, 5}, 8);
        System.out.println(lists);
    }

    @Test
    void combinationSumUnique() {
        String resultStr = "[[1, 1, 6], [1, 2, 5], [1, 7], [2, 6]]";
        assertEquals(resultStr, BacktrackingUtil.combinationSumUnique(new int[]{10,1,2,7,6,1,5}, 8).toString());
    }

    @Test
    void getNCombination() {
        String resultStr = "[[1, 2], [1, 3], [1, 4], [2, 3], [2, 4], [3, 4]]";
        assertEquals(resultStr, BacktrackingUtil.getNCombination(4, 2).toString());
    }

    @Test
    void wordSearchIn2DMatrix() {
        char[][] chars = {
                {'A', 'B', 'C', 'E'},
                {'S', 'F', 'C', 'S'},
                {'A', 'D', 'E', 'E'}
        };

        assertTrue(BacktrackingUtil.wordSearchIn2DMatrix(chars, "ABCCED"));
        assertTrue(BacktrackingUtil.wordSearchIn2DMatrix(chars, "SEE"));
        assertFalse(BacktrackingUtil.wordSearchIn2DMatrix(chars, "ABCB"));
    }

    @Test
    void restoreIpAddresses() {
        List<String> ipAddresses = BacktrackingUtil.restoreIpAddresses("25525511135");
        assertEquals("255.255.11.135", ipAddresses.getFirst());
        assertEquals("255.255.111.35", ipAddresses.getLast());
    }

    @Test
    void generateTrees() {
        List<TreeNode> treeNodes = BacktrackingUtil.generateTrees(3);
        System.out.println(treeNodes);
    }

    @Test
    void pathSum() {
        TreeNode root = new TreeNode(5);

        root.left = new TreeNode(4);
        root.left.left = new TreeNode(11);
        root.left.left.left = new TreeNode(7);
        root.left.left.right = new TreeNode(2);

        root.right = new TreeNode(8);
        root.right.left = new TreeNode(13);
        root.right.right = new TreeNode(4);
        root.right.right.left = new TreeNode(5);
        root.right.right.right = new TreeNode(1);

        List<List<Integer>> lists = BacktrackingUtil.pathSum(root, 22);

        assertEquals("[5, 4, 11, 2]", lists.getFirst().toString());
        assertEquals("[5, 8, 4, 5]", lists.getLast().toString());

        TreeNode root2 = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);

        List<List<Integer>> lists2 = BacktrackingUtil.pathSum(root2, 5);
        assertEquals("[1,2,3]", lists2.getFirst().toString());
    }

    @Test
    void partition() {
        List<List<String>> palindromeCombinations = BacktrackingUtil.partition("aab");
        assertEquals("[a, a, b]", palindromeCombinations.getFirst().toString());
        assertEquals("[aa, b]", palindromeCombinations.getLast().toString());
    }

    @Test
    void combinationSum3() {
        assertEquals("[[1, 2, 4]]", BacktrackingUtil.combinationSum3(3, 7).toString());
        assertEquals("[[1, 2, 6], [1, 3, 5], [2, 3, 4]]", BacktrackingUtil.combinationSum3(3, 9).toString());
    }

    @Test
    void makeSquare() {
        assertTrue(BacktrackingUtil.makeSquare(new int[] {1,1,2,2,2}));
        assertFalse(BacktrackingUtil.makeSquare(new int[] {3,3,3,3,4}));
    }

    @Test
    void makeSquare2() {
        assertTrue(BacktrackingUtil.makeSquare2(new int[] {1,1,2,2,2}));
        assertFalse(BacktrackingUtil.makeSquare2(new int[] {3,3,3,3,4}));
    }
}