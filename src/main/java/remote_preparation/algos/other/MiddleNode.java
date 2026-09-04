package remote_preparation.algos.other;

public class MiddleNode {

    static class Node {
        int val;
        Node next;

        Node(int val) {
            this.val = val;
        }
    }

    /**
     * Тот же каркас двух указателей, что и в задаче 39 (HasCycle),
     * но здесь fast не гоняется за slow ради встречи, а служит просто
     * "линейкой": пока fast проходит весь список, slow проходит ровно
     * половину пути — за счёт того, что скорость slow вдвое меньше.
     *
     * Когда fast упирается в конец списка, slow гарантированно
     * стоит на середине: fast сделал вдвое больше шагов, чем slow,
     * значит slow прошёл ровно половину того расстояния, которое
     * прошёл fast, то есть половину списка.
     *
     * УСЛОВИЕ ЦИКЛА fast != null && fast.next != null — то же самое,
     * что в задаче 39, и по той же причине: fast читает fast.next.next,
     * поэтому оба, fast и fast.next, обязаны быть проверены на null
     * ДО этого чтения, иначе NullPointerException.
     *
     * ВЫБОР СЕРЕДИНЫ ПРИ ЧЁТНОЙ ДЛИНЕ — здесь есть развилка, и её
     * стоит проговорить явно, а не оставлять неявной. При чётном n
     * "середины" физически две (например, для [1,2,3,4] это узлы
     * 2 и 3). Условие "fast != null && fast.next != null" даёт
     * ВТОРУЮ из двух середин — это стандартная конвенция (LeetCode 876).
     * Если бы условие было "fast != null && fast.next != null && fast.next.next != null"
     * (проверка на ШАГ ВПЕРЁД до сдвига), результатом стала бы ПЕРВАЯ
     * середина. Обе конвенции корректны, важно лишь не молчать
     * о том, какая выбрана, потому что тесты жёстко фиксируют одну из них.
     *
     * O(n) по времени за ОДИН проход (в отличие от наивного варианта
     * "сначала посчитать длину, потом пройти len/2 раз" — тот тоже O(n),
     * но требует ДВА прохода по списку), O(1) по памяти.
     */
    static Node middleNode(Node head) {
        Node slow = head;
        Node fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    // --- Вспомогательные функции для тестов ---

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

    /** Собирает значения узлов от head до конца в строку вида "1,2,3" — для наглядных сообщений. */
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
        check(middleNode(null), null, "пустой список");
        check(middleNode(buildLinear(1)), "1", "один узел");
        check(middleNode(buildLinear(2)), "2", "два узла: вторая из двух середин");
        check(middleNode(buildLinear(3)), "2,3", "нечётная длина: середина однозначна");
        check(middleNode(buildLinear(4)), "3,4", "чётная длина: конвенция — вторая середина");
        check(middleNode(buildLinear(5)), "3,4,5", "нечётная длина 5");
        check(middleNode(buildLinear(6)), "4,5,6", "чётная длина 6: вторая середина");
        check(middleNode(buildLinear(7)), "4,5,6,7", "нечётная длина 7");

        System.out.println("Все проверки пройдены");
    }

    private static void check(Node actual, String expectedFromHere, String label) {
        String actualStr = (actual == null) ? null : toValueString(actual);
        String expected = expectedFromHere; // null допустим как валидное ожидание
        boolean ok = (actualStr == null) ? (expected == null) : actualStr.equals(expected);
        if (!ok) {
            throw new AssertionError(label + ": ожидали \"" + expected + "\", получили \"" + actualStr + "\"");
        }
        System.out.println("OK  " + label + " -> " + actualStr);
    }
}
