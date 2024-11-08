package leetcode_tasks.graphs;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GraphsTaskUtilTest {

    @Test
    void findJudge() {
        assertEquals(2, GraphsTaskUtil.findJudge(2, new int[][] { {1, 2} }));
        assertEquals(3, GraphsTaskUtil.findJudge(3, new int[][] { {1, 3}, {2, 3} } ));
        assertEquals(-1, GraphsTaskUtil.findJudge(3, new int[][] { {1, 3}, {2, 3}, {3, 1} }));
    }

    @Test
    void findCenter() {
        assertEquals(2, GraphsTaskUtil.findCenter(new int[][] { {1,2}, {2, 3}, {4, 2} }));
        assertEquals(1, GraphsTaskUtil.findCenter(new int[][] { {1, 2}, {5, 1}, {1, 3}, {1, 4} }));
        assertEquals(2, GraphsTaskUtil.findCenterConstant(new int[][] { {1,2}, {2, 3}, {4, 2} }));
        assertEquals(1, GraphsTaskUtil.findCenterConstant(new int[][] { {1, 2}, {5, 1}, {1, 3}, {1, 4} }));
    }

    @Test
    void validPath() {
        assertTrue(GraphsTaskUtil.validPath(3, new int[][] { {0, 1}, {1, 2}, {2, 0} }, 0 , 2));
        assertFalse(GraphsTaskUtil.validPath(6, new int[][] { {0, 1}, {0, 2}, {3, 5}, {5, 4}, {4, 3} }, 0 , 5));
    }

    @Test
    void findMinHeightTrees() {
        assertEquals(1, GraphsTaskUtil.findMinHeightTrees(4, new int[][]{ {1, 0}, {1, 2}, {1, 3} }).getFirst());
    }

    @Test
    void calcEquation() {
        List<List<String>> equations = List.of(
                List.of("a", "b"),
                List.of("b", "c")
        );

        double[] values = new double[] { 2.0, 3.0 };

        List<List<String>> queries = List.of(
                List.of("a", "c"),
                List.of("b", "a"),
                List.of("a", "e"),
                List.of("a", "a"),
                List.of("x", "x")
        );

        assertArrayEquals(new double[] { 6.0, 0.5, -1.0, 1.0, -1.0 }, GraphsTaskUtil.calcEquation(equations, values, queries));
    }
}