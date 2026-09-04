package remote_preparation.algos.queue;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;

public class MyQueue {

    /**
     * Два стека: in — принимает push, out — отдаёт pop/peek.
     * Deque<Integer> используется как стек через addLast/removeLast/peekLast —
     * идиоматичный способ в Java (java.util.Stack — устаревший класс
     * на synchronized-методах, ArrayDeque быстрее и без лишней блокировки).
     *
     * ИДЕЯ: стек — это LIFO, очередь — FIFO. Один стек разворачивает
     * порядок на 180°. ДВА стека подряд разворачивают порядок ДВАЖДЫ,
     * что возвращает исходный порядок — это и даёт FIFO из LIFO-примитивов.
     *
     * Push всегда идёт в inStack — O(1), тривиально.
     *
     * Pop/Peek читают из outStack. Если outStack пуст, ВСЁ содержимое
     * inStack перекладывается в outStack по одному элементу — при этом
     * порядок разворачивается: самый первый добавленный (самый глубокий
     * в inStack) оказывается НАВЕРХУ outStack, то есть ровно там,
     * где и должен быть следующий на выход элемент FIFO-очереди.
     */
    private final Deque<Integer> inStack = new ArrayDeque<>();
    private final Deque<Integer> outStack = new ArrayDeque<>();

    public void push(int val) {
        inStack.addLast(val);
    }

    public int pop() {
        moveIfNeeded();
        if (outStack.isEmpty()) {
            throw new NoSuchElementException("Очередь пуста");
        }
        return outStack.removeLast();
    }

    public int peek() {
        moveIfNeeded();
        if (outStack.isEmpty()) {
            throw new NoSuchElementException("Очередь пуста");
        }
        return outStack.peekLast();
    }

    public boolean isEmpty() {
        return inStack.isEmpty() && outStack.isEmpty();
    }

    /**
     * Перекладывание происходит ТОЛЬКО когда outStack опустел —
     * это и есть ключ к амортизированной O(1), см. подробное
     * объяснение в комментарии к классу и в тексте ответа ниже.
     */
    private void moveIfNeeded() {
        if (outStack.isEmpty()) {
            while (!inStack.isEmpty()) {
                outStack.addLast(inStack.removeLast());
            }
        }
    }

    public static void main(String[] args) {
        MyQueue q = new MyQueue();

        check(q.isEmpty(), true, "новая очередь пуста");

        q.push(1);
        q.push(2);
        q.push(3);
        check(q.peek(), 1, "peek после трёх push -> самый первый добавленный");
        check(q.pop(), 1, "pop -> 1 (FIFO, не LIFO)");
        check(q.pop(), 2, "pop -> 2");

        q.push(4); // push ПОСЛЕ частичного pop, пока outStack ещё не пуст
        q.push(5);
        check(q.pop(), 3, "остаток из outStack выходит раньше новых push (сохранён порядок)");
        check(q.pop(), 4, "pop -> 4");
        check(q.pop(), 5, "pop -> 5");
        check(q.isEmpty(), true, "очередь снова пуста");

        // Чередование push/pop вперемешку — самая частая ошибка
        // случается именно при взаимодействии двух стеков в динамике
        q.push(10);
        check(q.pop(), 10, "push-pop сразу же");
        q.push(20);
        q.push(30);
        check(q.peek(), 20, "peek не удаляет элемент");
        check(q.peek(), 20, "повторный peek даёт то же значение");
        check(q.pop(), 20, "pop после peek возвращает то же значение, что показывал peek");
        q.push(40);
        check(q.pop(), 30, "порядок сохраняется несмотря на push между pop-ами");
        check(q.pop(), 40, "последний элемент");
        check(q.isEmpty(), true, "снова пуста");

        try {
            q.pop();
            throw new AssertionError("ожидали исключение на pop() из пустой очереди");
        } catch (NoSuchElementException e) {
            System.out.println("OK  pop() из пустой очереди бросает исключение");
        }

        try {
            q.peek();
            throw new AssertionError("ожидали исключение на peek() из пустой очереди");
        } catch (NoSuchElementException e) {
            System.out.println("OK  peek() из пустой очереди бросает исключение");
        }

        System.out.println("Все проверки пройдены");
    }

    private static void check(int actual, int expected, String label) {
        if (actual != expected) {
            throw new AssertionError(label + ": ожидали " + expected + ", получили " + actual);
        }
        System.out.println("OK  " + label);
    }

    private static void check(boolean actual, boolean expected, String label) {
        if (actual != expected) {
            throw new AssertionError(label + ": ожидали " + expected + ", получили " + actual);
        }
        System.out.println("OK  " + label);
    }
}
