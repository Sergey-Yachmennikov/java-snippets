package leetcode_tasks.linked_list;

import data_structures.leetcode.ListNode;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LinkedListUtilTest {

    @Test
    void reverseList() {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);

        ListNode current = LinkedListUtil.reverseList(head);
        int[] resultSet = new int[] {5,4,3,2,1};
        for (int v : resultSet) {
            assertEquals(v, current.val);
            current = current.next;
        }
    }
}