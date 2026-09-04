package remote_preparation.algos.array;

import java.util.Arrays;

public class Trap {

    /**
     * ============================================================
     * ПОДХОД 1: ДВА МАССИВА ПРЕФИКСНЫХ МАКСИМУМОВ, O(n) ПАМЯТИ
     * ============================================================
     *
     * Вода, которая удержится НАД столбиком i, ограничена сверху
     * САМЫМ НИЗКИМ из двух "бортиков" по краям — самым высоким
     * столбиком СЛЕВА от i и самым высоким столбиком СПРАВА от i.
     * Вода не может подняться выше более низкого из двух бортиков —
     * она просто перельётся через него.
     *
     *   уровень воды над i = min(leftMax[i], rightMax[i])
     *   вода над i         = max(0, уровень воды над i - height[i])
     *
     * leftMax[i]  — максимум height[0..i] включительно
     * rightMax[i] — максимум height[i..n-1] включительно
     *
     * Оба массива строятся за один проход каждый (слева направо
     * и справа налево соответственно) — O(n) времени, O(n) памяти
     * на сами массивы. Дальше — один проход суммирования — O(n).
     * Итого O(n) времени, O(n) дополнительной памяти.
     */
    static int trapWithPrefixArrays(int[] height) {
        int n = height.length;
        if (n == 0) {
            return 0;
        }

        int[] leftMax = new int[n];
        leftMax[0] = height[0];
        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], height[i]);
        }

        int[] rightMax = new int[n];
        rightMax[n - 1] = height[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            rightMax[i] = Math.max(rightMax[i + 1], height[i]);
        }

        int water = 0;
        for (int i = 0; i < n; i++) {
            water += Math.min(leftMax[i], rightMax[i]) - height[i];
        }
        return water;
    }

    /**
     * ============================================================
     * ПОДХОД 2: ДВА УКАЗАТЕЛЯ, O(1) ПАМЯТИ
     * ============================================================
     *
     * Идея: избавиться от ХРАНЕНИЯ обоих массивов целиком, сохранив
     * только ТЕКУЩИЙ известный максимум с каждой стороны (leftMax,
     * rightMax — теперь просто два числа, а не массивы).
     *
     * left и right сходятся друг к другу с двух концов. На каждом шаге
     * двигаем указатель с МЕНЬШИМ текущим значением height.
     *
     * КЛЮЧЕВОЙ ИНВАРИАНТ, ДЕЛАЮЩИЙ ЭТО КОРРЕКТНЫМ (не очевиден сразу):
     * если height[left] < height[right], то ИСТИННЫЙ rightMax
     * (максимум ПРАВЕЕ left, вплоть до конца массива) ТОЧНО НЕ МЕНЬШЕ
     * height[right] — а значит, не меньше leftMax В ТОЙ ЖЕ ТОЧКЕ,
     * раз мы вообще решили двигать left (см. рассуждение ниже).
     * Поэтому для позиции left уровень воды определяется ИМЕННО
     * leftMax — известным нам точно значением, — а не rightMax,
     * которого мы даже не знаем целиком: он гарантированно НЕ МЕНЬШЕ
     * leftMax, значит min(leftMax, rightMax) = leftMax уже сейчас,
     * без необходимости знать rightMax точно.
     *
     * Более развёрнуто: пусть в данный момент height[left] < height[right].
     * Тогда rightMax (максимум ВСЕХ height[right..n-1], уже накопленный
     * к этому моменту с ПРАВОЙ стороны обхода) >= height[right] > height[left].
     * Также rightMax по построению >= leftMax либо ещё будет обновлён
     * в будущем и станет только больше — то есть ИСТИННЫЙ максимум
     * справа от текущей позиции left ТОЧНО НЕ МЕНЬШЕ leftMax.
     * Значит min(leftTrueMax, rightTrueMax) в точке left равен
     * leftMax — и его можно посчитать ПРЯМО СЕЙЧАС, не дожидаясь
     * полного прохода вправо.
     *
     * Если height[left] >= leftMax — обновляем leftMax (это новый бортик,
     * воды над ним нет). Иначе — height[left] < leftMax, значит вода
     * над этой позицией: leftMax - height[left].
     * Симметрично для правой стороны.
     *
     * O(n) времени (left и right суммарно проходят весь массив один раз),
     * O(1) дополнительной памяти — вот и всё преимущество перед подходом 1.
     */
    static int trapWithTwoPointers(int[] height) {
        int n = height.length;
        if (n == 0) {
            return 0;
        }

        int left = 0;
        int right = n - 1;
        int leftMax = 0;
        int rightMax = 0;
        int water = 0;

        while (left < right) {
            if (height[left] < height[right]) {
                if (height[left] >= leftMax) {
                    leftMax = height[left];
                } else {
                    water += leftMax - height[left];
                }
                left++;
            } else {
                if (height[right] >= rightMax) {
                    rightMax = height[right];
                } else {
                    water += rightMax - height[right];
                }
                right--;
            }
        }

        return water;
    }

    public static void main(String[] args) {
        int[] example = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        checkBoth(example, 6, "пример из условия");

        checkBoth(new int[]{}, 0, "пустой массив");
        checkBoth(new int[]{5}, 0, "один столбик — воде негде удержаться");
        checkBoth(new int[]{3, 3}, 0, "два одинаковых столбика");
        checkBoth(new int[]{1, 2, 3, 4, 5}, 0, "монотонно возрастает — вода стекает");
        checkBoth(new int[]{5, 4, 3, 2, 1}, 0, "монотонно убывает — держать нечем");
        checkBoth(new int[]{4, 2, 0, 3, 2, 5}, 9, "классический пример LeetCode 42");
        checkBoth(new int[]{0, 0, 0}, 0, "все столбики нулевой высоты");
        checkBoth(new int[]{5, 0, 5}, 5, "простая яма между двумя высокими бортиками");

        System.out.println("Все проверки пройдены (оба подхода)");
    }

    private static void checkBoth(int[] height, int expected, String label) {
        int a = trapWithPrefixArrays(Arrays.copyOf(height, height.length));
        int b = trapWithTwoPointers(Arrays.copyOf(height, height.length));
        if (a != expected) {
            throw new AssertionError("[префиксы] " + label + ": ожидали " + expected + ", получили " + a);
        }
        if (b != expected) {
            throw new AssertionError("[два указателя] " + label + ": ожидали " + expected + ", получили " + b);
        }
        System.out.println("OK  " + label + " -> " + expected);
    }
}
