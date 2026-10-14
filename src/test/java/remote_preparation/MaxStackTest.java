package remote_preparation;

import org.junit.jupiter.api.Test;

import java.util.EmptyStackException;

import static org.junit.jupiter.api.Assertions.*;

class MaxStackTest {

    @Test
    void push_increasingValues_maxTracksLastPushed() {
        MaxStack<Integer> stack = new MaxStack<>();

        stack.push(1);
        assertEquals(1, stack.max());

        stack.push(2);
        assertEquals(2, stack.max());

        stack.push(3);
        assertEquals(3, stack.max());
    }

    @Test
    void push_decreasingValues_maxStaysAtFirstPushed() {
        MaxStack<Integer> stack = new MaxStack<>();

        stack.push(3);
        stack.push(2);
        stack.push(1);

        assertEquals(3, stack.max());
    }

    @Test
    void pop_afterNewMax_restoresPreviousMax() {
        MaxStack<Integer> stack = new MaxStack<>();

        stack.push(1);
        stack.push(5);
        stack.push(3);

        assertEquals(5, stack.max());

        stack.pop(); // снимаем 3, максимум остаётся 5
        assertEquals(5, stack.max());

        stack.pop(); // снимаем 5, максимум возвращается к 1
        assertEquals(1, stack.max());
    }

    @Test
    void pop_returnsElementsInLifoOrder() {
        MaxStack<Integer> stack = new MaxStack<>();

        stack.push(10);
        stack.push(20);
        stack.push(30);

        assertEquals(30, stack.pop());
        assertEquals(20, stack.pop());
        assertEquals(10, stack.pop());
    }

    @Test
    void duplicateMaxValues_popOneDuplicate_maxStaysSame() {
        MaxStack<Integer> stack = new MaxStack<>();

        stack.push(5);
        stack.push(5);
        stack.push(2);

        assertEquals(5, stack.max());

        stack.pop(); // снимаем 2
        assertEquals(5, stack.max());

        stack.pop(); // снимаем один из дублей 5, второй остаётся максимумом
        assertEquals(5, stack.max());

        stack.pop(); // снимаем последний 5
        assertThrows(EmptyStackException.class, stack::max);
    }

    @Test
    void push_null_throwsIllegalArgumentException() {
        MaxStack<Integer> stack = new MaxStack<>();

        assertThrows(IllegalArgumentException.class, () -> stack.push(null));
    }

    @Test
    void max_onEmptyStack_throwsEmptyStackException() {
        MaxStack<Integer> stack = new MaxStack<>();

        assertThrows(EmptyStackException.class, stack::max);
    }

    @Test
    void pop_onEmptyStack_throwsEmptyStackException() {
        MaxStack<Integer> stack = new MaxStack<>();

        assertThrows(EmptyStackException.class, stack::pop);
    }

    @Test
    void singleElement_pushThenPop_leavesStackEmpty() {
        MaxStack<Integer> stack = new MaxStack<>();

        stack.push(42);
        assertEquals(42, stack.max());
        assertEquals(42, stack.pop());

        assertThrows(EmptyStackException.class, stack::max);
        assertThrows(EmptyStackException.class, stack::pop);
    }
}
