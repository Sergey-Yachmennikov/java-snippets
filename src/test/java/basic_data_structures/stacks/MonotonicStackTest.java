package basic_data_structures.stacks;

import org.junit.jupiter.api.Test;

import java.util.List;

class MonotonicStackTest {

    @Test
    void monotonicIncreasing() {
        int[] nums = {3, 1, 4, 1, 5, 9, 2, 6};
        int[] result = MonotonicStack.monotonicIncreasing(nums);
        System.out.print("Monotonic increasing stack: [");
        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i]);
            if (i != result.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }

    @Test
    void monotonicDecreasing() {
        int[] nums = {3, 1, 4, 1, 5, 9, 2, 6};
        List<Integer> result = MonotonicStack.monotonicDecreasing(nums);
        System.out.println("Monotonic decreasing stack: " + result);
    }
}