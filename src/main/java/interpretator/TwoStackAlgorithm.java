package interpretator;

import basic_data_structures.SimpleStack;
import basic_data_structures.Stack;

public class TwoStackAlgorithm {

    private TwoStackAlgorithm() {}

    public static int calculate(String exp) {
        Stack<Character> operations = new SimpleStack<>();
        Stack<Integer> values = new SimpleStack<>();

        for (int i = 0; i < exp.length(); i++) {
            if (exp.charAt(i) == '(' || exp.charAt(i) == ' ') {
            } else if (
                    exp.charAt(i) == '+' ||
                    exp.charAt(i) == '-' ||
                    exp.charAt(i) == '/' ||
                    exp.charAt(i) == '*'
            ) {
                operations.push(exp.charAt(i));
            } else if (exp.charAt(i) == ')') {
                char op = operations.pop();
                int v = values.pop();

                if (op == '+') v = values.pop() + v;
                else if (op == '-') v = values.pop() - v;
                else if (op == '/') v = values.pop() / v;
                else if (op == '*') v = values.pop() * v;
                values.push(v);

            } else {
                values.push(Character.digit(exp.charAt(i), 10));
            }
        }

        return values.pop();
    }
}
