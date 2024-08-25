package arrays;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArraysTasksTest {

    @Test
    void findUniqueItem() {
        int[] arr = new int[]{1,1,3,4,4};
        assertEquals(3, ArraysTasks.findUniqueItem(arr));
    }
}