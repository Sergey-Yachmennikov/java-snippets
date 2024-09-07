package leetcode_tasks.linked_list;

import data_structures.leetcode.ListNode;

public class LinkedListUtil {

    private LinkedListUtil() {}

    public boolean hasCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
            if(slow == fast) return true;
        }

        return false;
    }

    public static ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode current = head;

        while(current != null) {
            ListNode next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }

        return prev;
    }

    public static ListNode reverseBetween(ListNode head, int left, int right) {
        if (left == right) return head;

        ListNode prev = null;
        ListNode curr = head;

        int counter = 0;
        // traverse till the left - 1 node, so that we have to link it toh the new reverse node which will come after it
        while(curr != null && counter < left - 1){
            prev = curr;
            curr = curr.next;
            counter++;
        }

        // new pointers for travers to right node
        ListNode last = prev;
        ListNode newEnd = curr;

        int i = 0;
        int n = (right - left) + 1;
        ListNode next = curr.next;

        while(curr != null && i < n) {
            curr.next = prev;
            prev = curr;
            curr = next;
            if (next != null) next = next.next;
            i++;
        }

        if(last != null) {
            last.next = prev;
        } else {
            head = prev;
        }

        newEnd.next = curr;

        return head;
    }

    public static ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode second = head.next;
        ListNode third = second.next;
        second.next = head;
        head.next = swapPairs(third);
        return second;

    }
}
