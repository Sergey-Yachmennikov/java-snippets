package remote_preparation.algos.other;

import java.util.ArrayList;
import java.util.List;

public class MergeKLists {

    static class Node {
        int val;
        Node next;

        Node(int val) {
            this.val = val;
        }
    }

    /**
     * mergeTwoLists — итеративная версия из задачи 41 (не рекурсивная:
     * здесь списки могут быть длинными, и рекурсия рискует переполнить
     * стек — то же соображение, что обсуждалось там).
     */
    private static Node mergeTwoLists(Node l1, Node l2) {
        Node dummy = new Node(0);
        Node tail = dummy;

        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                tail.next = l1;
                l1 = l1.next;
            } else {
                tail.next = l2;
                l2 = l2.next;
            }
            tail = tail.next;
        }
        tail.next = (l1 != null) ? l1 : l2;

        return dummy.next;
    }

    /**
     * ПОПАРНОЕ (турнирное) слияние вместо ПОСЛЕДОВАТЕЛЬНОГО.
     *
     * Последовательный подход — merge(list[0], list[1]), затем
     * merge(результат, list[2]), затем merge(результат, list[3])... —
     * работает, но на каждом шаге результат становится ВСЁ БОЛЬШЕ,
     * а его снова целиком приходится сравнивать со следующим списком.
     * Уже слитые в начале элементы проходятся через merge ПОВТОРНО
     * на каждом следующем шаге.
     *
     * Попарное слияние организовано турнирной сеткой: сливаем списки
     * ПОПАРНО за один раунд (0-й с 1-м, 2-й с 3-м, ...), получаем
     * вдвое меньше списков, повторяем на новом наборе, и так далее,
     * пока не останется один список. Каждый раунд — это ⌈k/2⌉ слияний,
     * количество раундов — log2(k).
     *
     * НЕЧЁТНОЕ ЧИСЛО СПИСКОВ В РАУНДЕ: последний список без пары
     * переносится в следующий раунд БЕЗ ИЗМЕНЕНИЙ (не сливается
     * сам с собой) — иначе легко получить NPE или лишнюю работу
     * на пустом слиянии.
     *
     * ============================================================
     * СЛОЖНОСТЬ — ПОЧЕМУ ПОПАРНОЕ СУЩЕСТВЕННО ЛУЧШЕ ПОСЛЕДОВАТЕЛЬНОГО
     * ============================================================
     * Пусть всего N элементов суммарно во всех k списках (N = k * n
     * при равной длине n каждого).
     *
     * ПОСЛЕДОВАТЕЛЬНОЕ слияние: 1-е слияние стоит O(2n) (два списка
     * длины n), 2-е — O(3n) (накопленный результат 2n + новый список n),
     * 3-е — O(4n), ..., k-е — O(kn). Сумма арифметической прогрессии:
     * O(n * (2+3+...+k)) = O(n * k^2) = O(N * k) (так как N = kn).
     * Каждый элемент из ПЕРВЫХ списков проходит через merge МНОГО РАЗ —
     * элемент из list[0] участвует во ВСЕХ k-1 слияниях подряд.
     *
     * ПОПАРНОЕ слияние: на КАЖДОМ раунде суммарная работа по ВСЕМ
     * слияниям этого раунда — O(N), потому что раунд просто проходит
     * по всем оставшимся N элементам ровно один раз, распределённым
     * по разным парам. Раундов — log2(k), потому что число списков
     * вдвое уменьшается на каждом раунде. Итого: O(N log k).
     *
     * Каждый элемент участвует не более чем в log2(k) слияниях за ВСЮ
     * работу алгоритма (по одному на каждый раунд), а не в k-1,
     * как при последовательном подходе — в этом всё отличие.
     *
     * Для k=1000 списков: log2(1000) ≈ 10 против k-1=999 — разница
     * в ДВА порядка на суммарную работу по каждому элементу.
     *
     * Память: O(1) дополнительной (не считая списка указателей
     * на промежуточные результаты раунда) — как и mergeTwoLists,
     * попарное слияние ПЕРЕСВЯЗЫВАЕТ существующие узлы, не копирует.
     */
    static Node mergeKLists(Node[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }

        List<Node> current = new ArrayList<>();
        for (Node l : lists) {
            current.add(l);
        }

        while (current.size() > 1) {
            List<Node> next = new ArrayList<>();
            for (int i = 0; i < current.size(); i += 2) {
                if (i + 1 < current.size()) {
                    next.add(mergeTwoLists(current.get(i), current.get(i + 1)));
                } else {
                    // нечётный хвост — без пары, переносится как есть
                    next.add(current.get(i));
                }
            }
            current = next;
        }

        return current.get(0);
    }

    // --- Вспомогательные функции для тестов ---

    private static Node buildFromArray(int[] values) {
        Node dummy = new Node(0);
        Node tail = dummy;
        for (int v : values) {
            tail.next = new Node(v);
            tail = tail.next;
        }
        return dummy.next;
    }

    private static String toValueString(Node head) {
        StringBuilder sb = new StringBuilder();
        for (Node n = head; n != null; n = n.next) {
            if (sb.length() > 0) {
                sb.append(',');
            }
            sb.append(n.val);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        check(mergeKLists(new Node[]{
                buildFromArray(new int[]{1, 4, 5}),
                buildFromArray(new int[]{1, 3, 4}),
                buildFromArray(new int[]{2, 6})
        }), "1,1,2,3,4,4,5,6", "пример из условия: три списка");

        check(mergeKLists(new Node[]{}), "", "пустой массив списков");
        check(mergeKLists(new Node[]{null}), "", "один пустой список");
        check(mergeKLists(new Node[]{null, null}), "", "два пустых списка");
        check(mergeKLists(new Node[]{buildFromArray(new int[]{1})}), "1", "один список из одного элемента");
        check(mergeKLists(new Node[]{buildFromArray(new int[]{1, 2, 3})}), "1,2,3", "единственный список целиком");

        check(mergeKLists(new Node[]{
                buildFromArray(new int[]{1}),
                buildFromArray(new int[]{2}),
                buildFromArray(new int[]{3}),
                buildFromArray(new int[]{4}),
                buildFromArray(new int[]{5})
        }), "1,2,3,4,5", "нечётное число списков (5), все длины 1");

        check(mergeKLists(new Node[]{
                null,
                buildFromArray(new int[]{1, 2}),
                null
        }), "1,2", "пустые списки вперемешку с непустыми");

        System.out.println("Все проверки пройдены");
    }

    private static void check(Node result, String expected, String label) {
        String actual = toValueString(result);
        if (!actual.equals(expected)) {
            throw new AssertionError(label + ": ожидали \"" + expected + "\", получили \"" + actual + "\"");
        }
        System.out.println("OK  " + label);
    }
}
