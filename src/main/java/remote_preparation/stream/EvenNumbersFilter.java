package remote_preparation.stream;

import java.util.List;

public class EvenNumbersFilter {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(3, 6, 9, 12, 15, 18);

        numbers.stream()
                .filter(n -> n % 2 == 0)
                .forEach(System.out::println);
    }
}