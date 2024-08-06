package trees;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TreeTest {

    @Test
    void treeSumTest() {
        Tree root = new Tree(20,
                new Tree(7,
                        new Tree(4, null, new Tree(6)),
                        new Tree(9)
                ),
                new Tree(35,
                        new Tree(31, new Tree(28), null),
                        new Tree(40, new Tree(38),new Tree(52))
                )
        );

        assertEquals(270, root.sumRecursive());
        assertEquals(270, root.sumDeep());
        assertEquals(270, root.sumWide());
    }

}