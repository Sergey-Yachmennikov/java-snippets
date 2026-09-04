package remote_preparation.algos.string;

import java.util.Stack;

// @complicated
public class DecodeString {

    public static void main(String[] args) {
        String s1 = "3[a]2[bc]";
        String s2 = "3[a2[c]]";
        String s3 = "2[abc]3[cd]ef";

        System.out.println(decodeString(s1));
        System.out.println(decodeString(s2));
        System.out.println(decodeString(s3));
    }

    public static String decodeString(String s) {
        Stack<Integer> numStack = new Stack<>();
        Stack<StringBuilder> strStack = new Stack<>();

        StringBuilder current = new StringBuilder();
        int num = 0;

        for (char ch : s.toCharArray()) {
            if (Character.isDigit(ch)) {
                num = num * 10 + (ch - '0');
            } else if (ch == '[') {
                numStack.push(num);
                strStack.push(current);

                current = new StringBuilder();
                num = 0;
            } else if (ch == ']') {
                int count = numStack.pop();
                StringBuilder decoded = strStack.pop();

                for (int i = 0; i < count; i++) {
                    decoded.append(current);
                }

                current = decoded;
            } else {
                current.append(ch);
            }
        }

        return current.toString();
    }
}
