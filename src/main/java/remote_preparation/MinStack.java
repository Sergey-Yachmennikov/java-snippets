package remote_preparation;

import java.util.EmptyStackException;
import java.util.Stack;

public class MinStack<T extends Comparable<T>> {

    private final Stack<T> stack;
    private final Stack<T> minStack;

    public MinStack() {
        stack = new Stack<>();
        minStack = new Stack<>();
    }

    public void push(T element) {
        if (element == null) {
            throw new IllegalArgumentException("element can not be null");
        }

        stack.push(element);
        if (minStack.isEmpty() || element.compareTo(minStack.peek()) <= 0) {
            minStack.push(element);
        }
    }

    public T min() {
        if (minStack.isEmpty()) {
            throw new EmptyStackException();
        }
        return minStack.peek();
    }

    public T pop() {
        if (stack.isEmpty()) {
            throw new EmptyStackException();
        }
        T popped = stack.pop();
        if (popped == minStack.peek()) {
            minStack.pop();
        }
        return popped;
    }
}
