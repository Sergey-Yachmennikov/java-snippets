package remote_preparation.algos.string;

import java.util.HashMap;
import java.util.Map;

public class CheckInclusion {

    static boolean checkInclusion(String s1, String s2) {
        int k = s1.length();
        if (k == 0 || s2.length() < k) {
            return false;
        }

        Map<Character, Integer> need = new HashMap<>();
        for (char c : s1.toCharArray()) {
            need.merge(c, 1, Integer::sum);
        }

        Map<Character, Integer> window = new HashMap<>();
        int matches = 0;

        for (int right = 0; right < s2.length(); right++) {
            char incoming = s2.charAt(right);
            int newCount = window.merge(incoming, 1, Integer::sum);
            if (need.containsKey(incoming) && newCount == need.get(incoming)) {
                matches++;
            } else if (need.containsKey(incoming) && newCount == need.get(incoming) + 1) {
                matches--;
            }

            int left = right - k + 1;
            if (left > 0) {
                char outgoing = s2.charAt(left - 1);
                int oldCount = window.get(outgoing);
                if (oldCount == 1) {
                    window.remove(outgoing);
                } else {
                    window.put(outgoing, oldCount - 1);
                }
                if (need.containsKey(outgoing)) {
                    int updated = oldCount - 1;
                    if (updated == need.get(outgoing)) {
                        matches++;
                    } else if (updated == need.get(outgoing) - 1) {
                        matches--;
                    }
                }
            }

            if (left >= 0 && matches == need.size()) {
                return true; // ранний выход — единственное отличие от FindAnagrams
            }
        }

        return false;
    }

    public static void main(String[] args) {
        check(checkInclusion("ab", "eidbaooo"), true, "пример из условия: 'ba' — перестановка 'ab'");
        check(checkInclusion("ab", "eidboaoo"), false, "перестановки 'ab' нет ни в одном окне");
        check(checkInclusion("adc", "dcda"), true, "перестановка не только 'dca', но и 'cda' на второй позиции");
        check(checkInclusion("hello", "ooolleoooleh"), false, "похожие буквы, но не совпадающие частоты");
        check(checkInclusion("a", "ab"), true, "s1 длины 1 — простейший нетривиальный случай");
        check(checkInclusion("", "abc"), false, "s1 пустая");
        check(checkInclusion("abc", ""), false, "s2 пустая");
        check(checkInclusion("abc", "abc"), true, "s1 и s2 совпадают целиком");
        check(checkInclusion("ab", "a"), false, "s1 длиннее s2 — перестановка физически не влезает");

        System.out.println("Все проверки пройдены");
    } // алекс сюй - подготовка с сложному интервью

    private static void check(boolean actual, boolean expected, String label) {
        if (actual != expected) {
            throw new AssertionError(label + ": ожидали " + expected + ", получили " + actual);
        }
        System.out.println("OK  " + label);
    }
}