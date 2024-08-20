package data_structures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BinaryTreeV1Test {

    @Test
    void addElementToTree() {
        BinaryTreeV1 bt = new BinaryTreeV1();

        bt.add(6);
        bt.add(4);
        bt.add(8);
        bt.add(3);
        bt.add(5);
        bt.add(7);
        bt.add(9);

         assertTrue(bt.containsNode(7));
         assertEquals(3, bt.findSmallestValue());

        System.out.println(bt.traverseInOrder());
        System.out.println(bt.traversePreOrder());
        System.out.println(bt.traversePostOrder());
        System.out.println(bt.traverseInOrderReversed());
        System.out.println(bt.traverseLevelOrder());
        System.out.println(bt.traverseInOrderIterative());
        System.out.println(bt.traversePostOrderIterative());

        bt.delete(5);
        assertFalse(bt.containsNode(5));
    }

}