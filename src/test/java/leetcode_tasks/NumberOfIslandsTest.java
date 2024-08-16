package leetcode_tasks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NumberOfIslandsTest {
    private static final char[][] grid = {
            {'1', '1', '0', '0', '0'},
            {'1', '1', '0', '0', '0'},
            {'0', '0', '1', '0', '0'},
            {'0', '0', '0', '1', '1'}
    };

    @Test
    void numberOfIslandTest() {
        var numberOfIslands = new NumberOfIslands();
         assertEquals(3, numberOfIslands.calcNumberOfIsland(grid));
    }

}