package remote_preparation.stream;

import java.util.List;

public class DuplicateCounter {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(5, 3, 5, 2, 8, 2, 5, 8);

        long uniqueCount = numbers.stream()
                .distinct()
                .count();

        System.out.println(uniqueCount);
    }
}