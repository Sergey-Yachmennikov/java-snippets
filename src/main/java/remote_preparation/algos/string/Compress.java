package remote_preparation.algos.string;

public class Compress {

    public static void main(String[] args) {
        String[] tests = {
                "aabcccdddd",
                "abcd",
                "aaabbbccc",
                "a",
                "",
        };

        for (String test : tests) {
            System.out.println(
                    "\"" + test + "\" -> \"" + compress(test) + "\""
            );
        }
    }

    public static String compress(String s) {
        if (s == null || s.length() <= 2) {
            return s;
        }

        StringBuilder compressed = new StringBuilder();

        int count = 1;

        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == s.charAt(i - 1)) {
                count++;
            } else {
                compressed.append(s.charAt(i - 1));

                if (count > 1) {
                    compressed.append(count);
                }

                count = 1;
            }
        }

        // Добавляем последний символ и его счётчик
        compressed.append(s.charAt(s.length() - 1));

        if (count > 1) {
            compressed.append(count);
        }

        // Если сжатая строка не короче, возвращаем исходную
        if (compressed.length() >= s.length()) {
            return s;
        }

        return compressed.toString();
    }
}
