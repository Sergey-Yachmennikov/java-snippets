package remote_preparation.stream;

import java.util.List;

//У вас есть список целых чисел: [3, 6, 9, 12, 15, 18].
// Фильтруйте, оставив только чётные числа, и выведите их.
public class EvenNumbersFilter {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(3, 6, 9, 12, 15, 18);

        numbers.stream()
                .filter(n -> n % 2 == 0)
                .forEach(System.out::println);
    }
}