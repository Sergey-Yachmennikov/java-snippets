package remote_preparation.algos.other;

public class GetIntersectionNode {

    static class Node {
        int val;
        Node next;

        Node(int val) {
            this.val = val;
        }
    }

    /**
     * Два указателя, каждый обходит ОБА списка по очереди: сначала свой,
     * потом чужой. Если списки пересекаются, указатели гарантированно
     * встретятся ровно в узле пересечения. Если нет — оба одновременно
     * придут к null, и цикл корректно завершится без результата.
     *
     * МАТЕМАТИКА, ПОЧЕМУ ЭТО РАБОТАЕТ.
     *
     * Пусть список A состоит из "своего" хвоста длины a (узлы ДО точки
     * пересечения, уникальные для A) и общего хвоста длины c (общий
     * с B, начиная с точки пересечения). Аналогично B = b + c,
     * где b — уникальная часть B.
     *
     *   A: [--- a ---][--- c ---]
     *   B: [--- b ---][--- c ---]
     *
     * pA идёт по A (a + c шагов до конца), затем переключается на head B
     * и проходит по B ещё b шагов до точки пересечения.
     * Итого путь pA до точки пересечения: a + c + b.
     *
     * pB идёт по B (b + c шагов до конца), затем переключается на head A
     * и проходит по A ещё a шагов до точки пересечения.
     * Итого путь pB до точки пересечения: b + c + a.
     *
     * a + c + b  ==  b + c + a  — ОДИНАКОВОЕ количество шагов.
     * Оба указателя проходят РОВНО ОДИНАКОВОЕ суммарное расстояние
     * (a + b + c) к моменту достижения точки пересечения, а значит
     * попадают туда ОДНОВРЕМЕННО, за одинаковое число итераций цикла.
     * Именно переключение на ЧУЖОЙ список компенсирует разницу
     * в длине "уникальных" частей a и b — оба указателя суммарно
     * проходят одну и ту же комбинированную дистанцию a+b+c.
     *
     * ЕСЛИ ПЕРЕСЕЧЕНИЯ НЕТ (c = 0, списки физически разные объекты
     * в хвосте): pA проходит lenA + lenB шагов, pB проходит
     * lenB + lenA шагов — тоже поровну, и оба одновременно
     * приходят к null. Цикл "while pA != pB" корректно завершается
     * на null == null, возвращая null — ложного совпадения не будет,
     * потому что мы сравниваем ССЫЛКИ, а null == null истинно
     * только когда оба указателя РЕАЛЬНО прошли весь путь и никакого
     * общего узла по дороге не нашли.
     *
     * Сложность: O(lenA + lenB) по времени — каждый указатель проходит
     * не более двух полных обходов суммарной длины, O(1) по памяти.
     * Альтернатива с HashSet посещённых узлов первого списка тоже
     * O(n), но требует O(n) памяти — то же соотношение, что между
     * Флойдом и HashSet-версией в задаче про цикл.
     */
    static Node getIntersectionNode(Node headA, Node headB) {
        if (headA == null || headB == null) {
            return null;
        }

        Node pA = headA;
        Node pB = headB;

        // Сравнение ССЫЛОК: узел пересечения — это ОДИН И ТОТ ЖЕ объект
        // в памяти, а не узел с совпадающим val
        while (pA != pB) {
            pA = (pA == null) ? headB : pA.next;
            pB = (pB == null) ? headA : pB.next;
        }

        return pA; // либо узел пересечения, либо null — оба случая корректны
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

    /** Присоединяет общий хвост tail к концу списка head (для построения пересечения). */
    private static void attachTail(Node head, Node tail) {
        if (head == null) {
            return;
        }
        Node node = head;
        while (node.next != null) {
            node = node.next;
        }
        node.next = tail;
    }

    public static void main(String[] args) {
        // Общий хвост — ОДНИ И ТЕ ЖЕ объекты Node, присоединённые к обоим спискам
        Node commonTail = buildFromArray(new int[]{8, 4, 5});

        Node a1 = buildFromArray(new int[]{4, 1});
        attachTail(a1, commonTail);
        Node b1 = buildFromArray(new int[]{5, 6, 1});
        attachTail(b1, commonTail);
        check(getIntersectionNode(a1, b1), commonTail, "пересечение в середине, разная длина хвостов");

        Node commonTail2 = buildFromArray(new int[]{2, 4});
        Node a2 = buildFromArray(new int[]{1});
        attachTail(a2, commonTail2);
        Node b2 = commonTail2; // B состоит только из общего хвоста — пересечение с самой головы B
        check(getIntersectionNode(a2, b2), commonTail2, "пересечение сразу с головы B");

        Node a3 = buildFromArray(new int[]{2, 6, 4});
        Node b3 = buildFromArray(new int[]{1, 5});
        check(getIntersectionNode(a3, b3), null, "списки физически не пересекаются");

        check(getIntersectionNode(null, buildFromArray(new int[]{1})), null, "первый список пуст");
        check(getIntersectionNode(buildFromArray(new int[]{1}), null), null, "второй список пуст");
        check(getIntersectionNode(null, null), null, "оба списка пусты");

        Node onlyTail = buildFromArray(new int[]{99});
        Node a4 = buildFromArray(new int[]{1, 2, 3, 4, 5});
        attachTail(a4, onlyTail);
        Node b4 = onlyTail; // B состоит ТОЛЬКО из общего узла
        check(getIntersectionNode(a4, b4), onlyTail, "пересечение — единственный узел B целиком");

        System.out.println("Все проверки пройдены");
    }

    private static void check(Node actual, Node expected, String label) {
        if (actual != expected) {
            throw new AssertionError(label + ": ожидали узел " + describe(expected)
                    + ", получили " + describe(actual));
        }
        System.out.println("OK  " + label);
    }

    private static String describe(Node n) {
        return (n == null) ? "null" : ("Node(val=" + n.val + ")");
    }
}
