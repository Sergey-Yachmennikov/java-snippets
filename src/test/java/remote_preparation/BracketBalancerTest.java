package remote_preparation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class BracketBalancerTest {

    @ParameterizedTest
    @CsvSource({
            "'()', true",
            "'{}', true",
            "'[]', true",
            "'([{}])', true",
            "'()[]{}', true",
            "'(]', false",
            "'([)]', false",
            "'{[}]', false",
            "'(((', false",
            "')))', false",
            "'', true"
    })
    void areBalanced_variousInputs(String input, boolean expected) {
        assertEquals(expected, BracketBalancer.areBalanced(input));
    }

    @Test
    void bracketsWithOtherCharacters_isBalanced() {
        // скобки могут быть частью произвольной строки, не только "чистые" скобки
        assertTrue(BracketBalancer.areBalanced("foo(bar[baz]{qux})"));
    }
}