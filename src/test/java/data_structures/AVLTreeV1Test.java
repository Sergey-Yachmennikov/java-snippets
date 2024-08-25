package data_structures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AVLTreeV1Test {

    @Test
    void insertNodes() {
        AVLTreeV1 treeV1 = new AVLTreeV1();
        treeV1.insert(5);
        treeV1.insert(4);
        treeV1.insert(3);
        treeV1.insert(2);
        treeV1.insert(1);
        System.out.println(treeV1.traverseInOrder());
        treeV1.delete(3);
        System.out.println(treeV1.traverseInOrder());
    }

}