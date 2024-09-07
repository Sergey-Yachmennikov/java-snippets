package basic_data_structures.stacks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MonotonicIncreasingStackTest {

    @Test
    void monotonicIncreasing() {
        int[] nums = {3, 1, 4, 1, 5, 9, 2, 6};
        int[] result = MonotonicIncreasingStack.monotonicIncreasing(nums);
        System.out.print("Monotonic increasing stack: [");
        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i]);
            if (i != result.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
}