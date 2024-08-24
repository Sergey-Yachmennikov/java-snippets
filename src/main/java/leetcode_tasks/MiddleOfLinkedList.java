package leetcode_tasks;

import data_structures.LinkedListV1;

/**
 * @topic List
 */
public class MiddleOfLinkedList {

    private MiddleOfLinkedList() {}

    public static int getMiddle(LinkedListV1<Integer> list) {
        LinkedListV1.Node<Integer> pointer1 = list.getHead();
        LinkedListV1.Node<Integer> pointer2 = list.getHead();

        while (pointer2 != null && pointer2.next != null) {
            pointer1 = pointer1.next;
            pointer2 = pointer2.next.next;
        }

        return pointer1.getValue();
    }
}
