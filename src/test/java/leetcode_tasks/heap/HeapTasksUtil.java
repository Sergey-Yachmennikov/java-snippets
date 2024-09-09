package leetcode_tasks.heap;

import java.util.*;

public class HeapTasksUtil {

    private HeapTasksUtil() {}

    public static int findKthLargest(int[] nums, int k) {
        Queue<Integer> pq = new PriorityQueue<>();

        for(int i = 0; i < k; i++){
            pq.add(nums[i]);
        }

        for(int i = k; i < nums.length; i++) {
            if (nums[i] > pq.peek()) {
                pq.remove();
                pq.add(nums[i]);
            }
        }

        return pq.peek();
    }

    public static int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> frequencyMap = new HashMap<>();

        for (int n : nums) {
            frequencyMap.put(n, frequencyMap.getOrDefault(n, 0) + 1);
        }

        List<Map.Entry<Integer, Integer>> entryList = new ArrayList<>(frequencyMap.entrySet());
        entryList.sort((a, b) -> b.getValue() - a.getValue());

        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            result[i] = entryList.get(i).getKey();
        }

        return result;
    }
}
