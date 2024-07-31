package base;

import org.junit.jupiter.api.Test;
import java.util.StringJoiner;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JavaNativeApiTest {

    @Test
    void whenAddingElements_thenJoinedElements() {
        var prefix = new StringBuilder("["); // String case
        var suffix = new StringBuilder().append(']'); // char case

        var joiner = new StringJoiner(", ", prefix, suffix);
        joiner
                .add("Red")
                .add("Green")
                .add("Blue");

        assertEquals("[Red, Green, Blue]", joiner.toString());
    }
}