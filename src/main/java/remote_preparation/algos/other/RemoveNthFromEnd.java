package remote_preparation.algos.other;

public class RemoveNthFromEnd {

    public static void main(String[] args) {
        ListNode head = buildList(new int[]{1, 2, 3, 4, 5});
        int n = 2;

        ListNode result = removeNthFromEnd(head, n);

        printList(result);
    }

    public static ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null || n <= 0) {
            return head;
        }

        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode fast = dummy;
        ListNode slow = dummy;

        // Двигаем fast на n+1 шагов вперёд
        for (int i = 0; i <= n; i++) {
            fast = fast.next;
        }

        // Двигаем оба указателя, пока fast не достигнет конца
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }

        // Удаляем n-й узел с конца
        slow.next = slow.next.next;

        return dummy.next;
    }

    // Вспомогательные методы для тестирования

    private static ListNode buildList(int[] values) {
        if (values == null || values.length == 0) {
            return null;
        }

        ListNode head = new ListNode(values[0]);
        ListNode current = head;

        for (int i = 1; i < values.length; i++) {
            current.next = new ListNode(values[i]);
            current = current.next;
        }

        return head;
    }

    private static void printList(ListNode head) {
        ListNode current = head;

        while (current != null) {
            System.out.print(current.val);

            if (current.next != null) {
                System.out.print(" -> ");
            }

            current = current.next;
        }

        System.out.println();
    }

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }
}
