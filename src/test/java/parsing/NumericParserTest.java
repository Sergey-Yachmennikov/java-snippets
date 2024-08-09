package parsing;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NumericParserTest {
    private static final String template = "22 + 3 - 2 * (2 * 5 + 2) * 4";

    @Test
    void lexAnalyzeTest() {
        assertEquals(-71, NumericParser.calculate(template));
    }
}