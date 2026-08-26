package remote_preparation.array;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayTasksTest {

    @Test
    void getMaximumSubarrayCadane() {
        int[] arr1 = new int[] { -2,1,-3,4,-1,2,1,-5,4 };
        int[] arr2 = new int[] { 1 };
        int[] arr3 = new int[] { 5,4,-1,7,8};
        assertEquals(6, ArrayTasks.getMaximumSubarrayCadane(arr1));
        assertEquals(1, ArrayTasks.getMaximumSubarrayCadane(arr2));
        assertEquals(23, ArrayTasks.getMaximumSubarrayCadane(arr3));
    }
}