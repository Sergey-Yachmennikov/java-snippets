package remote_preparation.algos.string;

public class IsPalindrome {

    static boolean isPalindrome(String s) {
        int[] codePoints = s.codePoints().toArray();

        int left = 0;
        int right = codePoints.length - 1;

        while (left < right) {
            if (codePoints[left] != codePoints[right]) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    public static void main(String[] args) {
        check(isPalindrome("racecar"), true, "racecar");
        check(isPalindrome("hello"), false, "hello");
        check(isPalindrome(""), true, "пустая строка");
        check(isPalindrome("a"), true, "один символ");
        check(isPalindrome("aa"), true, "два одинаковых");

        System.out.println("Все проверки пройдены");
    }

    private static void check(boolean actual, boolean expected, String label) {
        if (actual != expected) {
            throw new AssertionError(label + ": ожидали " + expected + ", получили " + actual);
        }
        System.out.println("OK  " + label);
    }
}
