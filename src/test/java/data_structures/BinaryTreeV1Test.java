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
        bt.invertTree();
        System.out.println(bt.traverseInOrder());

        bt.delete(5);
        assertFalse(bt.containsNode(5));
    }

    @Test
    void getDepth() {
        BinaryTreeV1 bt = new BinaryTreeV1();

        bt.add(6);
        bt.add(4);
        bt.add(8);
        bt.add(3);
        bt.add(5);
        bt.add(7);
        bt.add(9);
        assertEquals(3, bt.getDepth());
        bt.add(1);
        assertEquals(4, bt.getDepth());
    }

    @Test
    void pathSum() {
        BinaryTreeV1 bt = new BinaryTreeV1();
        bt.add(6);
        bt.add(4);
        bt.add(8);
        bt.add(3);
        bt.add(5);
        bt.add(7);
        bt.add(9);
        assertTrue(bt.hasPathSum(13));
    }

    @Test
    void deleteNodeWithTwpChildren() {
        BinaryTreeV1 bt = new BinaryTreeV1();

        bt.add(6);
        bt.add(4);
        bt.add(8);
        bt.add(3);
        bt.add(5);
        bt.add(7);
        bt.add(9);

        bt.delete(4);
        bt.delete(8);
        System.out.println(bt.traverseInOrder());
    }

}