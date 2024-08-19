package leetcode_tasks;

import java.util.List;

public class ReversedLinkedList {

    private ReversedLinkedList() {}

    public static <T> void reverseByLoop(List<T> list) {
        for (int i = 0; i < list.size() / 2; i++) {
            swap(list, i);
        }
    }

    private static <T> void swap(List<T> list, int i) {
        T temp = list.get(i);
        int lastElementIndex = list.size() - 1;
        list.set(i, list.get(lastElementIndex - i));
        list.set(lastElementIndex - i, temp);
    }
}
