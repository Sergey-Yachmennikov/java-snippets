package arrays;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArraysTasksTest {

    @Test
    void findUniqueItem() {
        int[] arr = new int[]{1,1,3,4,4};
        assertEquals(3, ArraysTasks.findUniqueItem(arr));
    }

    @Test
    void containerWithMostWater() {
        assertEquals(49, ArraysTasks.containerWithMostWater(new int[]{1,8,6,2,5,4,8,3,7}));
    }
}