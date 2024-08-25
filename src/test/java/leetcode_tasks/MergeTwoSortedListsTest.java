package leetcode_tasks;

import data_structures.LinkedListV1;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MergeTwoSortedListsTest {

    @Test
    void merge() {
        LinkedListV1<Integer> list1 = new LinkedListV1<>();
        LinkedListV1<Integer> list2 = new LinkedListV1<>();

        list1.add(1).add(2).add(4);
        list2.add(1).add(3).add(4);

       int[] values = {1,1,2,3,4,4};
       int valueIndex = 0;

        LinkedListV1.Node<Integer> merged = MergeTwoSortedLists.merge(list1, list2);
        while (merged != null) {
            assertEquals(values[valueIndex], merged.getValue());
            merged = merged.next;
            valueIndex++;
        }
    }
}