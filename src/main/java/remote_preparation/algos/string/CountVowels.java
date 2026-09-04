package remote_preparation.algos.string;

import java.util.Set;

public class CountVowels {

    private static final Set<Character> VOWELS = Set.of(
            'a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U');


    static int countVowels(String s) {
        int count = 0;
        for (char codePoint : s.toCharArray()) {
            if (VOWELS.contains(codePoint)) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        check(countVowels("hello world"), 3, "hello world");
        check(countVowels(""), 0, "пустая строка");
        check(countVowels("xyz"), 0, "без гласных");
        check(countVowels("AEIOU"), 5, "только заглавные гласные");
        check(countVowels("aeiouAEIOU"), 10, "все гласные обоих регистров");

        System.out.println("Все проверки пройдены");
    }

    private static void check(int actual, int expected, String label) {
        if (actual != expected) {
            throw new AssertionError(label + ": ожидали " + expected + ", получили " + actual);
        }
        System.out.println("OK  " + label);
    }
}
