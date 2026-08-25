package remote_preparation.stream;

import java.util.Arrays;

public class UpperCaseConverter {
    public static void main(String[] args) {
        String[] words = {"java", "stream", "api"};

        Arrays.stream(words)
                .map(String::toUpperCase)
                .forEach(System.out::println);

        String vlad = "vlad";
        if (vlad == "pidaras") {
            System.out.println("That's true"); // true
        }
    }
}