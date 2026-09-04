package remote_preparation.algos.string;

public class ReverseWords {

    static String reverseWords(String s) {
        String trimmed = s.trim();
        if (trimmed.isEmpty()) {
            return "";
        }

        String[] words = trimmed.split("\\s+");

        StringBuilder sb = new StringBuilder(trimmed.length());
        for (int i = words.length - 1; i >= 0; i--) {
            sb.append(words[i]);
            if (i > 0) {
                sb.append(' ');
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        check(reverseWords("the sky is blue"), "blue is sky the", "базовый случай");
        check(reverseWords(""), "", "пустая строка");
        check(reverseWords("   "), "", "строка из одних пробелов");
        check(reverseWords("hello"), "hello", "одно слово");

        System.out.println("Все проверки пройдены");
    }

    private static void check(String actual, String expected, String label) {
        if (!actual.equals(expected)) {
            throw new AssertionError(label + ": ожидали \"" + expected + "\", получили \"" + actual + "\"");
        }
        System.out.println("OK  " + label);
    }
}
