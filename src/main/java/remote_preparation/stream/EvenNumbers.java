package remote_preparation.stream;

import java.util.stream.IntStream;


// Используйте бесконечный поток для создания последовательности чётных чисел (начиная с 2).
//
//        1. Отберите первые 10 чисел.
//        2. Найдите их сумму.
public class EvenNumbers {

    public static void main(String[] args) {
        int outcome = sumOfFirstTenEvens();
        System.out.println(outcome);
    }

    public static int sumOfFirstTenEvens() {
        return IntStream.iterate(2, n -> n + 2)
                .limit(10)
                .sum();
    }
}
