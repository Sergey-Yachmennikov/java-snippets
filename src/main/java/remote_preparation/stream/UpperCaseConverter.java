package remote_preparation.stream;

import java.util.Arrays;

// У вас есть массив строк: {"java", "stream", "api"}. Используйте Stream API,
// чтобы преобразовать каждую строку в верхний регистр, а затем выведите их.
public class UpperCaseConverter {
    public static void main(String[] args) {
        String[] words = {"java", "stream", "api"};

        Arrays.stream(words)
                .map(String::toUpperCase)
                .forEach(System.out::println);
    }
}