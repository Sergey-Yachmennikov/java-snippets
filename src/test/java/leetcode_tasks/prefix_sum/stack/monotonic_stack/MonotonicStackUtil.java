package leetcode_tasks.prefix_sum.stack.monotonic_stack;

import java.util.HashMap;
import java.util.Stack;

public class MonotonicStackUtil {

    private MonotonicStackUtil() {}

    public static int[] nextGreaterElement(int[] n1, int[] n2) {
        Stack<Integer> stack = new Stack<>();
        HashMap<Integer, Integer> map = new HashMap<>();
        for(int i = n2.length - 1; i >= 0; i--) {
            int num = n2[i];
            while(!stack.isEmpty() && stack.peek() <= num) stack.pop();
            if (stack.isEmpty()) map.put(num, -1);
            else map.put(num, stack.peek());

            stack.push(num);
        }

        for (int i = 0; i < n1.length; i++) {
            n1[i] = map.getOrDefault(n1[i], -1);
        }
        return n1;
    }

    public static int[] dailyTemperatures(int[] temperatures) {
        Stack<Integer> st = new Stack<>();
        int[] answer = new int[temperatures.length];

        for(int i = 0; i < temperatures.length; i++) {
            while(!st.isEmpty() && temperatures[st.peek()] < temperatures[i]) {
                int top = st.pop();
                answer[top] = i - top;
            }
            st.push(i);
        }

        return answer;
    }
}
