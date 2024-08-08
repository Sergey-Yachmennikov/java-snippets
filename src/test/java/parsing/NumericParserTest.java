package parsing;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NumericParserTest {
    private static final String template = "22 + 3 - 2 * (2 * 5 + 2) * 4";

    @Test
    void lexAnalyzeTest() {
        List<NumericParser.Lexeme> lexemes = NumericParser.lexAnalyze(template);
        NumericParser.LexemeBuffer buffer = new NumericParser.LexemeBuffer(lexemes);
        assertEquals(-71, NumericParser.expr(buffer));
    }
}