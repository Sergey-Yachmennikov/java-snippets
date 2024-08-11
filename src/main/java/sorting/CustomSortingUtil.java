package sorting;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class CustomSortingUtil {

    private CustomSortingUtil() {}

    public static List<Integer> quicksort(List<Integer> list) {
        if (list.size() < 2) { // base case
            return list;
        } else { // recursive case
            Integer pivot = list.getFirst();

            // sub-array of all the elements less than the pivot
            List<Integer> less = list.stream()
                    .skip(1)
                    .filter(el -> el <= pivot)
                    .toList();

            // sub-array of all the elements greater than the pivot
            List<Integer> greater = list.stream()
                    .skip(1)
                    .filter(el -> el > pivot)
                    .toList();

            return Stream.of(
                            quicksort(less).stream(),
                            Stream.of(pivot),
                            quicksort(greater).stream())
                    .flatMap(Function.identity()).toList();
        }
    }
}
