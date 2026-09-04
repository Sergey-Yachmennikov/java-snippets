package remote_preparation.algos.string;

import java.util.HashMap;
import java.util.Map;

public class IsAnagram {

    /**
     * Считаем частоты кодовых точек первой строки, вычитаем при обходе
     * второй. Если в конце все счётчики нулевые (карта пуста после
     * remove-при-нуле) — это анаграмма.
     *
     * Быстрый отсев по длине ДО построения карты: если число символов
     * не совпадает, анаграммой быть не может, и нет смысла тратить
     * время на построение map[rune]int.
     *
     * codePoints(), а не char[] — по тем же причинам, что в задачах 10-13:
     * символ вне базовой плоскости (эмодзи) — это суррогатная пара
     * из двух char, и обход по char посчитал бы его за два разных символа.
     */
    static boolean isAnagram(String s, String t) {
        int[] sCodePoints = s.codePoints().toArray();
        int[] tCodePoints = t.codePoints().toArray();

        if (sCodePoints.length != tCodePoints.length) {
            return false;
        }

        Map<Integer, Integer> counts = new HashMap<>();
        for (int cp : sCodePoints) {
            counts.merge(cp, 1, Integer::sum);
        }

        for (int cp : tCodePoints) {
            Integer current = counts.get(cp);
            if (current == null) {
                return false; // символ из t отсутствует в s
            }
            if (current == 1) {
                counts.remove(cp);
            } else {
                counts.put(cp, current - 1);
            }
        }

        // Длины равны и все счётчики обнулены -> карта обязана быть пустой
        return counts.isEmpty();
    }

    public static void main(String[] args) {
        check(isAnagram("anagram", "nagaram"), true, "anagram/nagaram");
        check(isAnagram("rat", "car"), false, "rat/car");
        check(isAnagram("", ""), true, "обе пустые");
        check(isAnagram("a", ""), false, "разная длина");
        check(isAnagram("aabb", "abab"), true, "с повторами, другой порядок");
        check(isAnagram("aabbcc", "abcabc"), true, "три пары");
        check(isAnagram("привет", "тевирп"), true, "кириллица: анаграмма");
        check(isAnagram("привет", "приветт"), false, "кириллица: разная длина");

        System.out.println("Все проверки пройдены");
    }

    private static void check(boolean actual, boolean expected, String label) {
        if (actual != expected) {
            throw new AssertionError(label + ": ожидали " + expected + ", получили " + actual);
        }
        System.out.println("OK  " + label);
    }
}
