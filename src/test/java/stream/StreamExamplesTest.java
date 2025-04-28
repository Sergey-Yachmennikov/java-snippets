package stream;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class StreamExamplesTest {

    @Test
    void flatMapUsage() {
        List<String> phrases = Arrays.asList("Hello world", "Java Stream API", "flatMap example");

        List<String> words = phrases.stream()
                .map(phrase -> phrase.split(" "))
                .flatMap(Arrays::stream)  // "Разворачиваем" массивы в один стрим
                .toList();

        System.out.println(words);
    }

    @Test
    void flatMapAndOptionalUsage() {
        List<Optional<String>> options = Arrays.asList(
                Optional.of("Value1"),
                Optional.empty(),
                Optional.of("Value2")
        );

        List<String> values = options.stream()
                .flatMap(Optional::stream)  // Разворачиваем Optional в Stream (авто фильр на empty)
                .toList();

        System.out.println(values);
    }

    @Test
    void flatMapAndNumberUsage() {
        List<Integer> numbers = Arrays.asList(123, 45, 678);

        List<Integer> digits = numbers.stream()
                .map(String::valueOf)        // Число -> строка "123"
                .flatMapToInt(String::chars)
                .boxed() // преоразует IntStream в Stream<Integer> (объект обертка)
                .toList();

        System.out.println(digits);
    }
}
