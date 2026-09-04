package remote_preparation.algos.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TwoArraysIntersection {

    public static void main(String[] args) {
        int[] nums1 = {1, 2, 2, 1};
        int[] nums2 = {2, 2};

        int[] result = intersect(nums1, nums2);

        System.out.println(Arrays.toString(result));
    }

    public static int[] intersect(int[] nums1, int[] nums2) {
        Map<Integer, Integer> frequencies = new HashMap<>();

        for (int num : nums1) {
            frequencies.put(num, frequencies.getOrDefault(num, 0) + 1);
        }

        List<Integer> result = new ArrayList<>();

        for (int num : nums2) {
            int count = frequencies.getOrDefault(num, 0);

            if (count > 0) {
                result.add(num);
                frequencies.put(num, count - 1);
            }
        }

        int[] resultArray = new int[result.size()];

        for (int i = 0; i < result.size(); i++) {
            resultArray[i] = result.get(i);
        }

        return resultArray;
    }
}