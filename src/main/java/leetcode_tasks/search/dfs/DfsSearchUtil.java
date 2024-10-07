package leetcode_tasks.search.dfs;

import data_structures.leetcode.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class DfsSearchUtil {

    private DfsSearchUtil() {}

    public static List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> list = new ArrayList<>();
        List<Integer> sublist = new ArrayList<>();
        searchSum(list, sublist, root, targetSum);
        return list;
    }

    private static void searchSum(List<List<Integer>> list, List<Integer> sublist, TreeNode node, int sum){
        if(node == null) return;

        sum -= node.val;
        sublist.add(node.val);

        if(sum == 0 && node.left == null && node.right == null) list.add(new ArrayList<>(sublist));

        searchSum(list, sublist, node.left, sum);
        searchSum(list, sublist, node.right, sum);

        sublist.removeLast();
    }
}
