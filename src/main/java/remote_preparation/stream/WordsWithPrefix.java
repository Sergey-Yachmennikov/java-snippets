package remote_preparation.stream;

import java.util.Arrays;
import java.util.List;

public class WordsWithPrefix {

    public static void main(String[] args) {
        String[] words = {"java", "stream", "example", "simple", "javascript"};

        List<String> result = Arrays.stream(words)
                .filter(s -> s.startsWith("java"))
                .toList();

        System.out.println(result);
    }
}
