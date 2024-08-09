package parsing;

import java.util.ArrayList;
import java.util.List;

public class NumericParser {

    private enum LexemeType {
        LEFT_BRACKET,
        RIGHT_BRACKET,
        OP_PLUS,
        OP_MINUS,
        OP_MUL,
        OP_DIV,
        NUMBER,
        EOF
    }

    private static class Lexeme {
        LexemeType type;
        String value;

        public Lexeme(LexemeType type, String value) {
            this.type = type;
            this.value = value;
        }

        public Lexeme(LexemeType type, Character value) {
            this.type = type;
            this.value = value.toString();
        }

        @Override
        public String toString() {
            return "Lexeme {" +
                    "type=" + type +
                    ", value='" + value + '\'' +
                    '}';
        }
    }

    private static class LexemeBuffer {
        private int pos;
        public List<Lexeme> lexemes;

        public LexemeBuffer(List<Lexeme> lexemes) {
            this.lexemes = lexemes;
        }

        public Lexeme next() {
            return lexemes.get(pos++);
        }

        public void back() {
            pos--;
        }

        public int getPosition() {
            return pos;
        }
    }

    private static List<Lexeme> lexicalAnalysis(String expText) {
        List<Lexeme> lexemes = new ArrayList<>();
        int pos = 0;
        while (pos < expText.length()) {
            char c = expText.charAt(pos);
            switch (c) {
                case '(':
                    lexemes.add(new Lexeme(LexemeType.LEFT_BRACKET, c));
                    pos++;
                    break;
                case ')':
                    lexemes.add(new Lexeme(LexemeType.RIGHT_BRACKET, c));
                    pos++;
                    break;
                case '+':
                    lexemes.add(new Lexeme(LexemeType.OP_PLUS, c));
                    pos++;
                    break;
                case '-':
                    lexemes.add(new Lexeme(LexemeType.OP_MINUS, c));
                    pos++;
                    break;
                case '*':
                    lexemes.add(new Lexeme(LexemeType.OP_MUL, c));
                    pos++;
                    break;
                case '/':
                    lexemes.add(new Lexeme(LexemeType.OP_DIV, c));
                    pos++;
                    break;
                default:
                    if (c < '9' && c > '0') {
                        StringBuilder builder = new StringBuilder();
                        do {
                            builder.append(c);
                            pos++;
                            if (pos >= expText.length()) {
                                break;
                            }
                            c = expText.charAt(pos);
                        } while (c < '9' && c > '0');
                        lexemes.add(new Lexeme(LexemeType.NUMBER, builder.toString()));
                    } else {
                        if (c != ' ') {
                            throw new RuntimeException("Unexpected character: " + c);
                        }

                        pos++;
                    }
            }
        }

        lexemes.add(new Lexeme(LexemeType.EOF, ""));

        return lexemes;
    }

    public static int calculate(String expText) {
        List<Lexeme> lexemes = lexicalAnalysis(expText);
        LexemeBuffer buffer = new LexemeBuffer(lexemes);
        return expr(buffer);
    }

    private static int expr(LexemeBuffer lexemes) {
        Lexeme lexeme = lexemes.next();
        if (lexeme.type == LexemeType.EOF) {
            return 0;
        } else {
            lexemes.back();
            return plusminus(lexemes);
        }
    }

    private static int plusminus(LexemeBuffer lexemes) {
        int value = miltdiv(lexemes);
        while (true) {
            Lexeme lexeme = lexemes.next();
            switch (lexeme.type) {
                case OP_PLUS -> value += miltdiv(lexemes);
                case OP_MINUS -> value -= miltdiv(lexemes);
                default -> {
                    lexemes.back();
                    return value;
                }
            }
        }
    }

    private static int miltdiv(LexemeBuffer lexemes) {
        int value = factor(lexemes);
        while (true) {
            Lexeme lexeme = lexemes.next();
            switch (lexeme.type) {
                case OP_MUL -> value *= factor(lexemes);
                case OP_DIV -> value /= factor(lexemes);
                default -> {
                    lexemes.back();
                    return value;
                }
            }
        }
    }

    private static int factor(LexemeBuffer lexemes) {
        Lexeme lexeme = lexemes.next();
        switch (lexeme.type) {
            case NUMBER -> {
                return Integer.parseInt(lexeme.value);
            }
            case LEFT_BRACKET -> {
                int value = expr(lexemes);
                lexeme = lexemes.next();
                if (lexeme.type != LexemeType.RIGHT_BRACKET) {
                    throw new RuntimeException("Unexpected token: " + lexeme.value + " at position " + lexemes.getPosition());
                }

                return value;
            }
            default -> throw new RuntimeException("Unexpected token: " + lexeme.value + " at position " + lexemes.getPosition());
        }
    }
}
