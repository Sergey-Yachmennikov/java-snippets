package data_structures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BinarySearchTreeTest {

    @Test
    void binarySearchTest() {
        BinarySearchTree<Integer, String> tree = new BinarySearchTree<>();
        tree.put(5, "item 5");
        tree.put(2, "item 2");
        tree.put(1, "item 1");
        tree.print();

         assertEquals(3, tree);
    }

}