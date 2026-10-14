package remote_preparation;

import java.util.EmptyStackException;
import java.util.Stack;

public class MaxStack<T extends Comparable<? super T>> {

    private final Stack<T> stack;
    private final Stack<T> maxStack;

    public MaxStack() {
        stack = new Stack<>();
        maxStack = new Stack<>();
    }

    public void push(T element) {
        if (element == null) {
            throw new IllegalArgumentException("element can not be null");
        }

        stack.push(element);
        if (maxStack.isEmpty() || element.compareTo(maxStack.peek()) >= 0) {
            maxStack.push(element);
        }
    }

    public T max() {
        if (maxStack.isEmpty()) {
            throw new EmptyStackException();
        }
        return maxStack.peek();
    }

    public T pop() {
        if (stack.isEmpty()) {
            throw new EmptyStackException();
        }
        T popped = stack.pop();
        if (popped == maxStack.peek()) {
            maxStack.pop();
        }
        return popped;
    }
}
