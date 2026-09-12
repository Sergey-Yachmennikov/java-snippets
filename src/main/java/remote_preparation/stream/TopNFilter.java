package remote_preparation.stream;

import java.util.List;

//Есть поток чисел: `[7, 3, 5, 7, 9, 3, 1, 8, 9, 2]`. Используя Stream API, выполните:
//
//        1. Удаление дубликатов.
//        2. Выбор первых трёх значений, которые больше **4**.
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