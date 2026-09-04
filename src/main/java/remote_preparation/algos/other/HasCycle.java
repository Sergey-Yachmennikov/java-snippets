package remote_preparation.algos.other;

public class HasCycle {

    /** Узел односвязного списка — аналог Go-структуры *Node. */
    static class Node {
        int val;
        Node next;

        Node(int val) {
            this.val = val;
        }
    }

    /**
     * Алгоритм Флойда ("черепаха и заяц").
     *
     * Два указателя стартуют с head: slow идёт на 1 шаг за итерацию,
     * fast — на 2 шага. Если цикла нет, fast первым дойдёт до null
     * (или до узла без next) и обход завершится.
     *
     * ЕСЛИ ЦИКЛ ЕСТЬ, ОБА УКАЗАТЕЛЯ РАНО ИЛИ ПОЗДНО ВСТРЕТЯТСЯ.
     * Интуиция: как только slow заходит в цикл, fast уже находится
     * где-то внутри цикла (он попал туда раньше, так как быстрее).
     * Дальше это эквивалентно бегу по кругу: на каждом шаге расстояние
     * между fast и slow (по циклу) сокращается ровно на 1, потому что
     * fast приближается на 2, slow на 1, значит относительная скорость
     * сближения — 1 шаг за итерацию. Расстояние не может быть больше
     * длины цикла, значит встреча гарантированно произойдёт не позже,
     * чем за (длина цикла) итераций — то есть алгоритм завершается
     * за конечное число шагов в любом случае.
     *
     * УСЛОВИЕ ОСТАНОВА ПРОВЕРЯЕТ И fast, И fast.next:
     * fast движется на 2 узла, поэтому перед чтением fast.next.next
     * нужно убедиться, что оба — и fast, и fast.next — не null.
     * Если проверить только fast != null, а fast.next окажется null,
     * обращение к fast.next.next бросит NullPointerException.
     *
     * Сложность: O(n) по времени (доказывается через тот же аргумент
     * про сокращение расстояния на 1 за шаг), O(1) по памяти —
     * в этом всё преимущество перед альтернативой "запоминать
     * посещённые узлы в HashSet", которая тоже O(n) по времени,
     * но O(n) по памяти.
     */
    static boolean hasCycle(Node head) {
        Node slow = head;
        Node fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                // Сравнение ССЫЛОК (==), а не значений: узлы с одинаковым val,
                // но РАЗНЫЕ объекты, не должны считаться совпадением
                return true;
            }
        }
        return false;
    }

    // --- Вспомогательные конструкторы списков для тестов ---

    /** Строит список 1 -> 2 -> ... -> n -> null (без цикла). */
    private static Node buildLinear(int n) {
        if (n == 0) {
            return null;
        }
        Node head = new Node(1);
        Node tail = head;
        for (int i = 2; i <= n; i++) {
            tail.next = new Node(i);
            tail = tail.next;
        }
        return head;
    }

    /**
     * Строит список из n узлов, где хвост замыкается на узел
     * с индексом cycleStart (0-based). cycleStart = -1 означает "без цикла".
     */
    private static Node buildWithCycle(int n, int cycleStart) {
        if (n == 0) {
            return null;
        }
        Node head = new Node(1);
        Node tail = head;
        Node cycleEntry = (cycleStart == 0) ? head : null;

        for (int i = 2; i <= n; i++) {
            tail.next = new Node(i);
            tail = tail.next;
            if (i - 1 == cycleStart) {
                cycleEntry = tail;
            }
        }

        if (cycleStart >= 0) {
            tail.next = cycleEntry;
        }
        return head;
    }

    public static void main(String[] args) {
        check(hasCycle(null), false, "пустой список (head == null)");
        check(hasCycle(new Node(1)), false, "один узел без цикла");

        Node selfLoop = new Node(1);
        selfLoop.next = selfLoop;
        check(hasCycle(selfLoop), true, "один узел, замкнутый сам на себя");

        check(hasCycle(buildLinear(5)), false, "линейный список из 5 узлов без цикла");
        check(hasCycle(buildLinear(1000)), false, "длинный линейный список без цикла");

        check(hasCycle(buildWithCycle(5, 0)), true, "цикл на весь список (хвост -> голова)");
        check(hasCycle(buildWithCycle(5, 2)), true, "цикл начинается в середине");
        check(hasCycle(buildWithCycle(5, 4)), true, "цикл длины 1 в конце (последний узел на себя)");

        Node twoNodeCycle = new Node(1);
        Node second = new Node(2);
        twoNodeCycle.next = second;
        second.next = twoNodeCycle;
        check(hasCycle(twoNodeCycle), true, "цикл из двух узлов");

        System.out.println("Все проверки пройдены");
    }

    private static void check(boolean actual, boolean expected, String label) {
        if (actual != expected) {
            throw new AssertionError(label + ": ожидали " + expected + ", получили " + actual);
        }
        System.out.println("OK  " + label);
    }
}
