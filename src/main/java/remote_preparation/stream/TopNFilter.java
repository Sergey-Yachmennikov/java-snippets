package remote_preparation.stream;

import java.util.List;

public class TopNFilter {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(7, 3, 5, 7, 9, 3, 1, 8, 9, 2);

        numbers.stream()
                .distinct()
                .filter(n -> n > 4)
                .limit(3)
                .sorted()
                .forEach(System.out::println);
    }
}