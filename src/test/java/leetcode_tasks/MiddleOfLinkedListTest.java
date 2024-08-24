package leetcode_tasks;

import data_structures.LinkedListV1;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MiddleOfLinkedListTest {

    @Test
    void getLinkedListMiddle() {
        LinkedListV1<Integer> linkedList = new LinkedListV1<>();
        linkedList.add(1);
        linkedList.add(2);
        linkedList.add(3);
        linkedList.add(4);
        linkedList.add(5);

        System.out.println(MiddleOfLinkedList.getMiddle(linkedList));
    }

}