package leetcode_tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class ReversedLinkedListTest {
    LinkedList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);
    }

    @Test
    void reverseByLoop() {
        ReversedLinkedList.reverseByLoop(list);
        assertEquals(7, list.getFirst());
        assertEquals(1, list.getLast());
        System.out.println(list);
    }
}