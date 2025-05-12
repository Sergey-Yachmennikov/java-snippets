package stream;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

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

        System.out.println(digits); // charts values in int
    }

    @Test
    void flatMapAndNumber2Usage() {
        List<Integer> numbers = Arrays.asList(123, 45, 678);

        List<Integer> digits = numbers.stream()
                .map(String::valueOf)        // Число -> строка "123"
                .flatMap(s -> s.chars().mapToObj(v -> v - '0')) // transform char to int
                .toList();

        System.out.println(digits);
    }

    @Test
    void distinctUsage() {
        List<Integer> nums = Arrays.asList(1, 2, 2, 3);
        nums.stream().distinct().forEach(System.out::println); // 1, 2, 3
    }

    @Test
    void sortedUsage() {
        List<String> names = Arrays.asList("Bob", "Alice");
        names.stream().sorted().forEach(System.out::println); // Alice, Bob
    }

    @Test
    void reduceUsage() {
        List<Integer> nums = Arrays.asList(1, 2, 3);
        Optional<Integer> sum = nums.stream().reduce(Integer::sum);
        System.out.println(sum.get()); // 6
    }

    @Test
    void anyMatchUsage() {
        assertTrue(Stream.of(1, 2, 3)
                .anyMatch(n -> n % 2 == 0));
    }

    @Test
    void allMatchUsage() {
        assertTrue(Stream.of(2, 4, 6).allMatch(n -> n % 2 == 0));
    }

    @Test
    void noneMatchUsage() {
        assertTrue(Stream.of(1, 2, 3).noneMatch(n -> n < 0));
    }

    @Test
    void findAnyUsage() {
        Stream.of(1, 2, 3).findAny().ifPresent(System.out::println);
    }

    @Test
    void multiMapUsage() {
        List<Integer> numbers = List.of(1, 2, 3);

        // С помощью flatMap
        numbers.stream()
                .flatMap(n -> Stream.of(n, n * 2, n * 3))
                .forEach(System.out::println); // 1, 2, 3, 2, 4, 6, 3, 6, 9

        // С помощью mapMulti (более эффективно)
        numbers.stream()
                .mapMulti((n, consumer) -> {
                    consumer.accept(n);
                    consumer.accept(n * 2);
                    consumer.accept(n * 3);
                })
                .forEach(System.out::println);

        List<String> fruits = List.of("Apple", "Banana");

        fruits.stream()
                .mapMulti((fruit, consumer) -> {
                    consumer.accept(fruit);
                    consumer.accept(fruit.toUpperCase());
                })
                .forEach(System.out::println);
        // Apple, APPLE, Banana, BANANA
    }

    @Test
    void spliteratorTest() {
        Spliterator<String> spliterator = Arrays.asList("A", "B", "C", "D", "E").spliterator();

        Spliterator<String> firstHalf = spliterator.trySplit();

        System.out.println(firstHalf.characteristics());

        System.out.println("first half");
        firstHalf.forEachRemaining(System.out::println); // обрабатываем первую часть
        System.out.println("----");
        System.out.println("second half");
        spliterator.forEachRemaining(System.out::println); // обрабатываем вторую часть
    }

    @Test
    void customSpliteratorTest() {
        Date date = new Date();
        Messages messages = new Messages();

        List.of(
            new MessageRecord(1, date, "Hello"),
            new MessageRecord(2, date, "World"),
            new MessageRecord(3, date, "Java The Best"),
            new MessageRecord(4, date, "Cat"),
            new MessageRecord(5, date, "Hello John"),
            new MessageRecord(6, date, "Java The Best"),
            new MessageRecord(7, date, "Buy Katty"),
            new MessageRecord(8, date, "I choose Rust")
        ).forEach(messages::addMessageRecord);

        Spliterator<MessageRecord> spliterator = messages.getSpliterator();

        for (;spliterator.tryAdvance(System.out::println);) {
            System.out.println(spliterator.estimateSize());
        }
    }
}
