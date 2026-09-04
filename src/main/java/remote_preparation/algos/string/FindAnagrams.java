package remote_preparation.algos.string;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindAnagrams {

    static List<Integer> findAnagrams(String s, String p) {
        List<Integer> result = new ArrayList<>();

        int[] sCp = s.codePoints().toArray();
        int[] pCp = p.codePoints().toArray();
        int k = pCp.length;

        if (k == 0 || sCp.length < k) {
            return result;
        }

        Map<Integer, Integer> need = new HashMap<>();
        for (int cp : pCp) {
            need.merge(cp, 1, Integer::sum);
        }

        Map<Integer, Integer> window = new HashMap<>();
        int matches = 0; // сколько различных символов сейчас совпадают по частоте с need

        for (int right = 0; right < sCp.length; right++) {
            int incoming = sCp[right];
            int newCount = window.merge(incoming, 1, Integer::sum);
            if (need.containsKey(incoming) && newCount == need.get(incoming)) {
                matches++;
            } else if (need.containsKey(incoming) && newCount == need.get(incoming) + 1) {
                // символ стал ИЗБЫТОЧНЫМ: только что потеряли совпадение по нему
                matches--;
            }

            int left = right - k + 1;
            if (left > 0) {
                // Элемент слева, покидающий окно (right сместился на позицию left-1 + k)
                int outgoing = sCp[left - 1];
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
                result.add(left);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        check(findAnagrams("cbaebabacd", "abc"), List.of(0, 6), "cbaebabacd/abc");
        check(findAnagrams("abab", "ab"), List.of(0, 1, 2), "abab/ab: пересекающиеся вхождения");
        check(findAnagrams("af", "be"), List.of(), "нет совпадений");
        check(findAnagrams("a", "a"), List.of(0), "одинаковые односимвольные строки");
        check(findAnagrams("ab", "abc"), List.of(), "p длиннее s");
        check(findAnagrams("", "a"), List.of(), "s пустая");
        check(findAnagrams("aaaaaaaaaa", "aaa"), List.of(0, 1, 2, 3, 4, 5, 6, 7),
                "все символы одинаковые");
        check(findAnagrams("абвгд", "гвб"), List.of(1), "кириллица");

        System.out.println("Все проверки пройдены");
    }

    private static void check(List<Integer> actual, List<Integer> expected, String label) {
        if (!actual.equals(expected)) {
            throw new AssertionError(label + ": ожидали " + expected + ", получили " + actual);
        }
        System.out.println("OK  " + label);
    }
}
