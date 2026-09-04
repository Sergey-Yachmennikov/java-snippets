package remote_preparation.algos.string;

import java.util.HashMap;
import java.util.Map;

public class WordCount {

    public static void main(String[] args) {
        String[] tests = {
                "Hello world hello",
                "Go go GO gopher",
                "one two three two one one",
                "",
        };

        for (String test : tests) {
            Map<String, Integer> result = wordCount(test);

            System.out.println("\"" + test + "\" -> " + result);
        }
    }

    public static Map<String, Integer> wordCount(String s) {
        Map<String, Integer> counts = new HashMap<>();

        if (s == null || s.isEmpty()) {
            return counts;
        }

        String[] words = s.split("\\s+");

        for (String word : words) {
            if (word.isEmpty()) {
                continue;
            }

            String lower = word.toLowerCase();

            counts.put(lower, counts.getOrDefault(lower, 0) + 1);
        }

        return counts;
    }
}
