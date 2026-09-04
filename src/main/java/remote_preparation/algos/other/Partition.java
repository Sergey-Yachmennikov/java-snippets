package remote_preparation.algos.other;

public class Partition {

    static class Node {
        int val;
        Node next;

        Node(int val) {
            this.val = val;
        }
    }

    /**
     * Два отдельных списка (dummy-голова для каждого — тот же приём,
     * что в задаче 41 MergeTwoLists), собираемые за один проход,
     * потом соединённые в один.
     *
     * Каждый узел исходного списка ПЕРЕСВЯЗЫВАЕТСЯ, а не копируется:
     * попадает либо в конец "less"-списка (val < x), либо в конец
     * "greater"-списка (val >= x), в зависимости от значения. Порядок
     * внутри каждой из двух групп сохраняется автоматически — узлы
     * добавляются в конец в том же порядке, в котором шли в исходном
     * списке, поэтому решение стабильно (STABLE PARTITION) без
     * дополнительных усилий.
     *
     * ============================================================
     * КЛАССИЧЕСКАЯ ЛОВУШКА ЭТОЙ ЗАДАЧИ: greaterTail.next ОБЯЗАН
     * быть обнулён перед финальной склейкой.
     * ============================================================
     *
     * Почему это не паранойя, а реальный баг без этой строки.
     * Каждый узел при перемещении в свой список получает НОВОЕ значение
     * next только когда он САМ становится tail и к нему присоединяют
     * СЛЕДУЮЩИЙ узел. Но последний добавленный в "greater"-список узел
     * (greaterTail) СВОЙ .next ещё хранит указатель из ИСХОДНОГО списка —
     * этот указатель никто не трогал, ведь после него в исходном списке
     * обход уже закончился (node = null), и до него очередь обновить
     * greaterTail.next просто не дошла.
     *
     * Если исходный .next у greaterTail указывал на узел, который
     * оказался в "less"-списке (для этого исходный следующий элемент
     * должен быть < x — то есть последний узел списка попал в greater,
     * а перед ним в исходном порядке шёл less-элемент, который к моменту
     * обработки greaterTail уже был пересвязан в другой список) —
     * получаем ЦИКЛ при финальной склейке lessTail.next = greaterHead.next.
     *
     * Конкретный пример, где это реально стреляет: [1,4,3,2,5,2], x=3.
     * greater-список собирается из узлов 4,3,5 — реальных ОБЪЕКТОВ
     * из исходного списка. У узла "5" исходный .next указывал на узел
     * "2" (последний элемент исходного списка) — а этот узел "2" уже
     * пересвязан в конец less-списка! Без явного greaterTail.next = null
     * итоговый список превращается в бесконечный цикл через узел "2".
     * Проверено симуляцией отдельно перед написанием этого кода —
     * без этой строки обход зацикливается уже на первом содержательном
     * тесте, а не в каком-то экзотическом крайнем случае.
     *
     * Сложность: O(n) по времени за один проход, O(1) дополнительной
     * памяти (не считая двух dummy-узлов) — узлы пересвязываются,
     * не копируются.
     */
    static Node partition(Node head, int x) {
        Node lessDummy = new Node(0);
        Node greaterDummy = new Node(0);
        Node lessTail = lessDummy;
        Node greaterTail = greaterDummy;

        Node node = head;
        while (node != null) {
            if (node.val < x) {
                lessTail.next = node;
                lessTail = node;
            } else {
                greaterTail.next = node;
                greaterTail = node;
            }
            node = node.next;
        }

        // ОБЯЗАТЕЛЬНАЯ строка — см. подробное объяснение выше
        greaterTail.next = null;
        lessTail.next = greaterDummy.next;

        return lessDummy.next;
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
        // Ограничение на длину вывода — если бы где-то остался цикл,
        // это превратило бы тест в зависание вместо явного провала
        int limit = 1000;
        Node n = head;
        while (n != null && limit-- > 0) {
            if (sb.length() > 0) {
                sb.append(',');
            }
            sb.append(n.val);
            n = n.next;
        }
        if (limit == 0) {
            sb.append(",...ПОХОЖЕ НА ЦИКЛ, ОБХОД ПРЕРВАН");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        check(partition(buildFromArray(new int[]{1, 4, 3, 2, 5, 2}), 3), "1,2,2,4,3,5",
                "пример с реальным риском цикла без greaterTail.next=null");
        check(partition(buildFromArray(new int[]{}), 5), "", "пустой список");
        check(partition(buildFromArray(new int[]{1}), 2), "1", "один узел, меньше x");
        check(partition(buildFromArray(new int[]{2}), 2), "2", "один узел, равен x (идёт в greater)");
        check(partition(buildFromArray(new int[]{1, 2, 3}), 10), "1,2,3", "все меньше x");
        check(partition(buildFromArray(new int[]{1, 2, 3}), -10), "1,2,3", "все больше x");
        check(partition(buildFromArray(new int[]{5, 5, 5}), 5), "5,5,5", "все равны x — все в greater");
        check(partition(buildFromArray(new int[]{3, 1}), 2), "1,3", "порядок внутри групп сохраняется");

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
