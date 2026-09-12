package remote_preparation.stream;

import java.util.Arrays;
import java.util.List;

//Дан список строк, где каждая строка — список чисел, разделённых запятой:
//
//        `["1,2,3", "4,5,6", "7,8,9"]`.
//
//Необходимо:
//
//        1. Преобразовать каждую строку в массив чисел.
//        2. Объединить все массивы в один поток чисел.
//        3. Найти общую сумму всех чисел.
public class NestedDataProcessor {
    public static void main(String[] args) {
        List<String> data = List.of("1,2,3", "4,5,6", "7,8,9");

        int sum = data.stream()
                .flatMapToInt(line -> Arrays.stream(line.split(","))
                        .mapToInt(Integer::parseInt))
                .sum();
    }
}