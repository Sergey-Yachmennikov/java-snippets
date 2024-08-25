package leetcode_tasks;

import data_structures.LinkedListV1;


public class MergeTwoSortedLists {

    private MergeTwoSortedLists() {}

    public static LinkedListV1.Node<Integer> merge(LinkedListV1<Integer> list1, LinkedListV1<Integer> list2) {

        var l1 = list1.getHead(); // link to head of first LinkedList
        var l2 = list2.getHead(); // lint to head of second LinkedList
        if (l1 == null || l2 == null) return null;

        LinkedListV1.Node<Integer> tempNode = new LinkedListV1.Node<>(0); // temporal link to the beginning of LikedList
        LinkedListV1.Node<Integer> currentNode = tempNode; // link to current node during iteration

        while (l1 != null && l2 != null) {

            if (l1.getValue() < l2.getValue()) {
                currentNode.next = l1;
                l1 = l1.next;
            } else {
                currentNode.next = l2;
                l2 = l2.next;
            }

            currentNode = currentNode.next;
        }

        return tempNode.next;
    }
}
