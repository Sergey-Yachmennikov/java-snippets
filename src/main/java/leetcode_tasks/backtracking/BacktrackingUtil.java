package leetcode_tasks.backtracking;

import data_structures.leetcode.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class BacktrackingUtil {

    private BacktrackingUtil() {}

    public static List<String> binaryTreePaths(TreeNode root) {
        List<String> paths = new ArrayList<>();
        List<Integer> curPath = new ArrayList<>();
        dfs(root, paths, curPath);
        return paths;
    }

    private static void dfs(TreeNode root, List<String> paths, List<Integer> curPath) {
        if (root == null) return;
        curPath.add(root.val);
        if (root.left == null && root.right == null) paths.add(pathToString(curPath));
        if (root.left != null) dfs(root.left, paths, curPath);
        if (root.right != null) dfs(root.right, paths, curPath);
        curPath.removeLast();
    }

    private static String pathToString(List<Integer> path) {
        StringBuilder sb = new StringBuilder();
        sb.append(path.getFirst());
        for (int i = 1; i < path.size(); i++) sb.append("->").append(path.get(i));
        return sb.toString();
    }

    public static List<String> readBinaryWatch(int turnedOn) {
        var result = new ArrayList<String>();

        for (int i = 0; i < 12; i++)
            for (int j = 0; j < 60; j++)
                if ((Integer.bitCount(i) + Integer.bitCount(j)) == turnedOn)
                    result.add(i + ":" + (j < 10 ? "0" + j : j));

        return result;
    }

    public static int subsetXORSum(int[] nums) {
        int[] ans = new int[]{0};
        List<Integer> all = new ArrayList<>();
        findAns(0, nums, all, ans);
        return ans[0];
    }

    private static void findAns(int index, int[] nums, List<Integer> all, int[] ans) {
        if (index == nums.length) { // iterated all nums
            int result = 0;
            for (int num : all) result ^= num;
            ans[0] += result;
        } else {
            all.add(nums[index]);
            findAns(index + 1, nums, all, ans);
            all.removeLast();
            findAns(index + 1, nums, all, ans);
        }
    }

    public static int subsetXORSum2(int[] nums) {
        int[] totalXorSum = new int[]{0};
        dfsXor(nums, 0, 0, totalXorSum);
        return totalXorSum[0];
    }

    private static void dfsXor(int[] nums, int index, int currentXor, int[] totalXorSum) {
        totalXorSum[0] += currentXor;
        for (int i = index; i < nums.length; i++) {
            currentXor ^= nums[i];
            dfsXor(nums, i + 1, currentXor, totalXorSum);
            currentXor ^= nums[i];
        }
    }

    public static int printSubsets(char[] set) {
        int n = 1 << set.length;
        for (int i = 0; i < n; i++) {
            System.out.print("{ ");

            // Print current subset
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) > 0) System.out.print(set[j] + " ");
            }

            System.out.println("}");
        }

        return n;
    }

    public static List<String> letterCombinations(String digits) {
        List<String> ans = new ArrayList<>();
        if (digits == null || digits.isEmpty()) return ans;
        String[] mapping = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        backtraceLetters(digits, "", ans, mapping, 0);
        return ans;
    }

    private static void backtraceLetters(String digits, String current, List<String> ans, String[] mapping, int index) {
        if (index == digits.length()) {
            ans.add(current);
            return;
        }

        String letters = mapping[digits.charAt(index) - '0'];
        for (int i = 0; i < letters.length(); i++) backtraceLetters(digits, current + letters.charAt(i), ans, mapping, index + 1);
    }

    public static List<List<Integer>> calculateSubsets(int[] arr) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> backtrackSet = new ArrayList<>();
        dfsSubset(arr, 0, backtrackSet, result);
        return result;
    }

    private static void dfsSubset(int[] nums, int index, List<Integer> backtrackSet, List<List<Integer>> subsets) {
        if (index == nums.length) {
            subsets.add(new ArrayList<>(backtrackSet));
        } else {
            backtrackSet.add(nums[index]);
            dfsSubset(nums, index + 1, backtrackSet, subsets);
            backtrackSet.removeLast();
            dfsSubset(nums, index + 1, backtrackSet, subsets);
        }
    }

    public static List<List<Integer>> calculateSubsetsIterative(int[] arr) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> backtrackSet = new ArrayList<>();
        dfsSubsetIterative(arr, 0, backtrackSet, result);
        return result;
    }

    private static void dfsSubsetIterative(int[] nums, int index, List<Integer> backtrackSet, List<List<Integer>> subsets) {
        subsets.add(new ArrayList<>(backtrackSet));
        for (int i = index; i < nums.length; i++) {
            backtrackSet.add(nums[i]);
            dfsSubsetIterative(nums, i + 1, backtrackSet, subsets);
            backtrackSet.removeLast();
        }
    }
}
