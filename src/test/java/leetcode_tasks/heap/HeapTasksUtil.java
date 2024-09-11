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

    private static class Triple {
        int value;
        int i;
        int index;

        public Triple(int value,int i,int index){
            this.value = value;
            this.i = i;
            this.index = index;
        }

        @Override
        public String toString() {
            return "Triple{" +
                    "value=" + value +
                    ", i=" + i +
                    ", index=" + index +
                    '}';
        }
    }

    public static List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        PriorityQueue<Triple> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.value));

        for(int i = 0; i < Math.min(k, nums1.length); i++) {
            pq.add(new Triple(nums1[i] + nums2[0], i,0));
        }

        List<List<Integer>> list = new ArrayList<>();
        while(k != list.size()) {
            List<Integer> list1 = new ArrayList<>();
            Triple triple = pq.remove();

            list1.add(nums1[triple.i]);
            list1.add(nums2[triple.index]);
            list.add(list1);
            if(triple.index != nums2.length - 1){
                triple.index ++;
                pq.add(new Triple(nums1[triple.i] + nums2[triple.index ], triple.i, triple.index ));
            }
        }

        return list;
    }
}
