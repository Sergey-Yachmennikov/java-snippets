package remote_preparation.algos.string;

import java.util.HashMap;
import java.util.Map;

public class MinWindow {

    /**
     * Скользящее окно ПЕРЕМЕННОГО размера (как в задачах 29 и 31),
     * но условие валидности окна сложнее одного числа — это ЧАСТОТНОЕ
     * СООТВЕТСТВИЕ, тот же аппарат need/window/formed, что и в задаче 30
     * (FindAnagrams), только там окно было ФИКСИРОВАННОГО размера,
     * а здесь оно растёт и сжимается свободно в поисках МИНИМУМА.
     *
     * need    — частота каждого символа, требуемая строкой t (map[rune]int)
     * window  — частота каждого символа В ТЕКУЩЕМ окне (map[rune]int)
     * required — число РАЗЛИЧНЫХ символов в need (не сумма частот!)
     * formed  — сколько из этих различных символов УЖЕ достигли
     *           нужной частоты в window (0 <= formed <= required)
     *
     * Окно валидно (содержит все символы t с нужной кратностью)
     * ровно когда formed == required.
     *
     * right расширяет окно вправо, обновляя window и, если частота
     * какого-то нужного символа ДОСТИГЛА требуемой (не превысила,
     * а именно СТАЛА РАВНА — переход через эту границу считается
     * один раз), увеличивает formed.
     *
     * Пока formed == required, окно ВАЛИДНО — пытаемся сжать его
     * слева максимально, фиксируя каждую валидную ширину как кандидата
     * на минимум (та же схема "сжимать, пока валидно", что в задаче 31
     * MinSubarrayLen, а не "сжимать, пока НЕ валидно", как в задаче 32
     * LongestOnes — там искали максимум, здесь ищем минимум,
     * отсюда разница в направлении цикла).
     *
     * ПОРЯДОК ОПЕРАЦИЙ ПРИ СЖАТИИ КРИТИЧЕН: сначала фиксируем текущую
     * ширину как кандидата (окно ЕЩЁ валидно), потом уменьшаем window
     * для уходящего символа, и только ПОСЛЕ уменьшения проверяем,
     * не УПАЛА ЛИ частота НИЖЕ требуемой — если да, formed уменьшается
     * и цикл сжатия останавливается на следующей итерации while.
     * Перепутать порядок (например, сначала уменьшить window, потом
     * проверить условие для фиксации кандидата) даст либо пропущенный
     * валидный кандидат, либо фиксацию уже невалидного окна.
     *
     * Сложность: O(|s| + |t|) — right проходит |s| раз, left суммарно
     * тоже не больше |s| раз (только растёт), построение need — O(|t|).
     * Память: O(|t| + k), где k — число различных символов в окне
     * (не более размера алфавита).
     */
    static String minWindow(String s, String t) {
        if (s.isEmpty() || t.isEmpty()) {
            return "";
        }

        Map<Character, Integer> need = new HashMap<>();
        for (char c : t.toCharArray()) {
            need.merge(c, 1, Integer::sum);
        }
        int required = need.size(); // РАЗЛИЧНЫХ символов, не сумма частот

        Map<Character, Integer> window = new HashMap<>();
        int formed = 0;

        int left = 0;
        int bestLen = Integer.MAX_VALUE;
        int bestLeft = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            window.merge(c, 1, Integer::sum);

            if (need.containsKey(c) && window.get(c).intValue() == need.get(c).intValue()) {
                formed++;
            }

            while (formed == required) {
                if (right - left + 1 < bestLen) {
                    bestLen = right - left + 1;
                    bestLeft = left;
                }

                char leftChar = s.charAt(left);
                window.put(leftChar, window.get(leftChar) - 1);
                if (need.containsKey(leftChar) && window.get(leftChar) < need.get(leftChar)) {
                    formed--;
                }
                left++;
            }
        }

        return (bestLen == Integer.MAX_VALUE) ? "" : s.substring(bestLeft, bestLeft + bestLen);
    }

    public static void main(String[] args) {
        check(minWindow("ADOBECODEBANC", "ABC"), "BANC", "пример из условия");
        check(minWindow("a", "a"), "a", "минимальный случай: полное совпадение");
        check(minWindow("a", "aa"), "", "t требует больше повторов, чем есть в s");
        check(minWindow("", "A"), "", "s пустая");
        check(minWindow("A", ""), "", "t пустая");
        check(minWindow("ab", "b"), "b", "нужен только один символ из двух");
        check(minWindow("aa", "aa"), "aa", "t с повторами, s ровно им соответствует");
        check(minWindow("aaflslflsldkalskaaa", "aaa"), "aaa", "t с тройным повтором символа");
        check(minWindow("bba", "ab"), "ba", "порядок символов в t не имеет значения");

        System.out.println("Все проверки пройдены");
    }

    private static void check(String actual, String expected, String label) {
        if (!actual.equals(expected)) {
            throw new AssertionError(label + ": ожидали \"" + expected + "\", получили \"" + actual + "\"");
        }
        System.out.println("OK  " + label);
    }
}
