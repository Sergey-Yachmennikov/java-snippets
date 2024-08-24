package data_structures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListV1Test {

    @Test
    void addElementsToLinkedList() {
        LinkedListV1<Integer> linkedList = new LinkedListV1<>();
        linkedList.add(1);
        linkedList.add(2);
        linkedList.add(3);
        linkedList.add(4);
        linkedList.add(5);

        assertEquals(5, linkedList.size());
        assertEquals(1, linkedList.removeHead());
        assertEquals(4, linkedList.size());
        assertEquals(5, linkedList.removeTail());
        assertEquals(3, linkedList.size());
    }

}