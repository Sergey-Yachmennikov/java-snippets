package guru;

import ch.qos.logback.core.encoder.JsonEscapeUtil;

import java.util.Comparator;
import java.util.stream.Stream;

public class StreamState {

    public static void main(String[] args) {
        Stream.of("Yes", "No", "querty", "solid")
                .sorted(Comparator.comparingInt(String::length))
                .peek(s -> System.out.println("Before filtering: " +  s))
                .filter(s -> s.length() > 2)
                .forEach(s -> System.out.println("After filtering: " +  s));
    }
}
