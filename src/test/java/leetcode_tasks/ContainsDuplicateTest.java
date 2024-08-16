package leetcode_tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContainsDuplicateTest {

    @Test
    void isContainsDublicate() {
        ContainsDuplicate containsDuplicate = new ContainsDuplicate();
        assertFalse(containsDuplicate.isContainsDuplicate(new int[]{ 1, 2, 3, 4, 5, 6 }));
        assertTrue(containsDuplicate.isContainsDuplicate(new int[]{ 1, 2, 3, 3, 4, 5, 6 }));
    }
}