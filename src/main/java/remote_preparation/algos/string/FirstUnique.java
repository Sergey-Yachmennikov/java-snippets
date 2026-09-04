package remote_preparation.algos.string;

import java.util.HashMap;
import java.util.Map;

public class FirstUnique {

    static int firstUnique(String s) {
        int[] codePoints = s.codePoints().toArray();

        Map<Integer, Integer> counts = new HashMap<>();
        for (int cp : codePoints) {
            counts.merge(cp, 1, Integer::sum);
        }

        for (int i = 0; i < codePoints.length; i++) {
            if (counts.get(codePoints[i]) == 1) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        check(firstUnique("leetcode"), 0, "leetcode");
        check(firstUnique("aabb"), -1, "aabb: все символы повторяются");
        check(firstUnique(""), -1, "пустая строка");
        check(firstUnique("a"), 0, "один символ");
        check(firstUnique("loveleetcode"), 2, "loveleetcode");
        check(firstUnique("aabbcddc"), -1, "все символы парные");
        check(firstUnique("swiss"), 1, "swiss: 'w' единственный неповторяющийся");

        System.out.println("Все проверки пройдены");
    }

    private static void check(int actual, int expected, String label) {
        if (actual != expected) {
            throw new AssertionError(label + ": ожидали " + expected + ", получили " + actual);
        }
        System.out.println("OK  " + label);
    }
}
