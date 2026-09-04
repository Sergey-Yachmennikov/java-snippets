package remote_preparation.algos.other;

public class MergeTwoLists {

    static class Node {
        int val;
        Node next;

        Node(int val) {
            this.val = val;
        }
    }

    // -------------------------------------------------------------------
    // Итеративное решение
    // -------------------------------------------------------------------

    /**
     * Dummy-узел (фиктивная голова) — стандартный приём для построения
     * связного списка "с нуля", когда заранее неизвестно, какой узел
     * окажется настоящей головой результата (l1 или l2 — узнаём только
     * в процессе сравнения). Без dummy пришлось бы отдельной веткой кода
     * выбирать и присваивать первый узел результата ДО входа в основной
     * цикл — dummy убирает эту развилку, тем же приёмом, что sentinel-
     * узел prefix[0]=0 убирал развилку в задачах на префиксные суммы.
     *
     * ВАЖНО: узлы НЕ копируются и не создаются заново — алгоритм
     * ПЕРЕСВЯЗЫВАЕТ существующие узлы l1 и l2, просто выбирая, какой
     * из двух текущих узлов присоединить следующим. Отсюда O(1)
     * дополнительной памяти (не считая dummy — одного узла).
     *
     * Сложность: O(n + m) по времени — каждый узел обоих списков
     * посещается и присоединяется ровно один раз. O(1) доп. памяти.
     */
    static Node mergeTwoListsIterative(Node l1, Node l2) {
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

        // Один из списков закончился раньше — оставшийся хвост
        // целиком (уже отсортированный) присоединяется одним присваиванием,
        // без обхода узел за узлом
        tail.next = (l1 != null) ? l1 : l2;

        return dummy.next;
    }

    // -------------------------------------------------------------------
    // Рекурсивное решение
    // -------------------------------------------------------------------

    /**
     * Та же логика "выбрать меньший из двух текущих узлов", но выражена
     * через рекурсию: голова результата — это узел с меньшим значением,
     * а его next — результат СЛИЯНИЯ ОСТАВШЕЙСЯ ЧАСТИ. База рекурсии —
     * когда один из списков закончился: тогда результат — это просто
     * оставшийся список целиком (отсортированный список плюс null
     * в конце всё ещё отсортированный список).
     *
     * Читается почти как формальное определение задачи, а не как
     * алгоритм её решения — в этом основное преимущество перед
     * итеративной версией.
     *
     * ЦЕНА: глубина рекурсии равна n + m (суммарная длина списков).
     * Каждый рекурсивный вызов добавляет фрейм в стек вызовов —
     * O(n + m) памяти на стеке, а не O(1), как в итеративной версии.
     * На больших списках (десятки-сотни тысяч узлов) это реально
     * StackOverflowError, а не только "менее эффективно". Именно
     * поэтому в проде для длинных списков предпочитают итеративный
     * вариант, а рекурсию — для коротких списков или языков
     * с гарантированной оптимизацией хвостовой рекурсии (в Java
     * такой оптимизации НЕТ, JVM не разворачивает хвостовые вызовы
     * в цикл автоматически).
     */
    static Node mergeTwoListsRecursive(Node l1, Node l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }

        if (l1.val <= l2.val) {
            l1.next = mergeTwoListsRecursive(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoListsRecursive(l1, l2.next);
            return l2;
        }
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
        // Каждый случай прогоняется через ОБЕ реализации —
        // они обязаны давать одинаковый результат
        runBoth(new int[]{}, new int[]{}, "", "оба списка пусты");
        runBoth(new int[]{}, new int[]{1, 2, 3}, "1,2,3", "первый список пуст");
        runBoth(new int[]{1, 2, 3}, new int[]{}, "1,2,3", "второй список пуст");
        runBoth(new int[]{1, 3, 5}, new int[]{2, 4, 6}, "1,2,3,4,5,6", "чередование поровну");
        runBoth(new int[]{1, 2, 3}, new int[]{4, 5, 6}, "1,2,3,4,5,6", "второй список целиком после первого");
        runBoth(new int[]{4, 5, 6}, new int[]{1, 2, 3}, "1,2,3,4,5,6", "первый список целиком после второго");
        runBoth(new int[]{1, 1, 2}, new int[]{1, 1, 3}, "1,1,1,1,2,3", "с повторяющимися значениями");
        runBoth(new int[]{5}, new int[]{1, 2, 3, 4}, "1,2,3,4,5", "разная длина списков");

        System.out.println("Все проверки пройдены (обе реализации)");
    }

    private static void runBoth(int[] a, int[] b, String expected, String label) {
        Node iterResult = mergeTwoListsIterative(buildFromArray(a), buildFromArray(b));
        check(toValueString(iterResult), expected, "[итеративно] " + label);

        Node recResult = mergeTwoListsRecursive(buildFromArray(a), buildFromArray(b));
        check(toValueString(recResult), expected, "[рекурсивно] " + label);
    }

    private static void check(String actual, String expected, String label) {
        if (!actual.equals(expected)) {
            throw new AssertionError(label + ": ожидали \"" + expected + "\", получили \"" + actual + "\"");
        }
        System.out.println("OK  " + label);
    }
}
