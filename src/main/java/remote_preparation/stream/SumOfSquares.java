package remote_preparation.stream;

import java.util.Arrays;

// Есть массив чисел: [3, 7, 10, 15, 20, 25, 30]. Отфильтруйте числа,
// оставив только делящиеся на 5, возведите их в квадрат, затем найдите сумму квадратов.
public class SumOfSquares {
    public static void main(String[] args) {
        int[] numbers = {3, 7, 10, 15, 20, 25, 30};

        int sumOfSquares = Arrays.stream(numbers)
                .filter(n -> n % 5 == 0)
                .map(n -> n * n)
                .sum();

        System.out.println(sumOfSquares);
    }
}