package sorting;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomSortingUtilTest {
    private final List<Integer> list = Arrays.asList(10, 5, 2, 3);

    @Test
    void quickSortStream() {
        System.out.println(CustomSortingUtil.quicksort(list));
    }

}