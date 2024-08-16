package leetcode_tasks;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PascalTriangleTest {

    @Test
    void generate() {
        PascalTriangle pascalTriangle = new PascalTriangle();
        List<List<Integer>> list = pascalTriangle.generate(5);
        list.forEach(System.out::println);
    }
}