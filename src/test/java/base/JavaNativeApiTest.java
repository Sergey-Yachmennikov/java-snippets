package base;

import org.junit.jupiter.api.Test;
import java.util.StringJoiner;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class JavaNativeApiTest {

    private final CharSequence PREFIX = new StringBuilder("["); // String case
    private final CharSequence SUFFIX = new StringBuilder().append(']'); // char case

    // StringJoiner
    @Test
    void whenAddingElements_thenJoinedElements() {
        var joiner = new StringJoiner(", ", PREFIX, SUFFIX);
        joiner
                .add("Red")
                .add("Green")
                .add("Blue");

        assertEquals("[Red, Green, Blue]", joiner.toString());
    }

    @Test
    void whenEmptyJoinerWithEmptyValue_thenDefaultValue() {
        StringJoiner joiner = new StringJoiner(",");
        joiner.setEmptyValue("default");

        assertEquals(joiner.toString(), "default");
    }

    @Test
    void whenMergingJoiners_thenReturnMerged() {

        StringJoiner rgbJoiner = new StringJoiner(
                ",", PREFIX, SUFFIX);
        StringJoiner cmybJoiner = new StringJoiner(
                "-", PREFIX, SUFFIX);

        rgbJoiner.add("Red")
                .add("Green")
                .add("Blue");

        cmybJoiner.add("Cyan")
                .add("Magenta")
                .add("Yellow")
                .add("Black");

        rgbJoiner.merge(cmybJoiner);

        assertEquals("[Red,Green,Blue,Cyan-Magenta-Yellow-Black]", rgbJoiner.toString());
    }

    // CharSequence
    @Test
    void givenCharSequenceAsString_whenConvertingUsingCasting_thenCorrect() {
        String expected = "baeldung";
        CharSequence charSequence = "baeldung";
        String explicitCastedString = (String) charSequence; // explicit type casting

        assertEquals(expected, charSequence);
        assertEquals(expected, explicitCastedString);
    }

    @Test
    void givenCharSequence_whenConvertingUsingToString_thenCorrect() {
        String expected = "baeldung";
        CharSequence charSequence1 = "baeldung";
        CharSequence charSequence2 = new StringBuilder("baeldung");

        assertEquals(expected, charSequence1.toString());
        assertEquals(expected, charSequence2.toString());
    }

    @Test
    void givenCharSequence_whenConvertingUsingValueOf_thenCorrect() {
        String expected = "baeldung";
        CharSequence charSequence1 = "baeldung";
        CharSequence charSequence2 = new StringBuilder("baeldung");
        CharSequence charSequence3 = null;

        assertEquals(expected, String.valueOf(charSequence1));
        assertEquals(expected, String.valueOf(charSequence2));
        assertEquals("null", String.valueOf(charSequence3)); // null cast to String
    }

    /// test error handling
    @Test()
    void givenCharSequenceAsStringBuiler_whenConvertingUsingCasting_thenThrowException() {
        assertThrows(ClassCastException.class, this::doException);
    }

    @Test()
    void givenCharSequenceAsStringBuiler_whenConvertingUsingCasting_thenThrowException2() {
        assertThatThrownBy(this::doException).isInstanceOf(ClassCastException.class);
    }

    void doException() {
        CharSequence charSequence = new StringBuilder("baeldung");
        String castedString = (String) charSequence;
    }

    @Test
    void whenExceptionThrown_thenAssertionSucceeds() {
        Exception exception = assertThrows(NumberFormatException.class, () -> {
            Integer.parseInt("1a");
        });

        String expectedMessage = "For input string";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void givenABlock_whenExecutes_thenEnsureNoExceptionThrown() {
        assertDoesNotThrow(() -> {
            Integer.parseInt("100");
        });
    }
}