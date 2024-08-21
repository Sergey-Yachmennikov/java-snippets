package string;

public class StringUtil {

    private StringUtil() {}

    public static String toLowerCase(String s) {
        StringBuilder sb = new StringBuilder(s.length());
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (Character.isUpperCase(ch)) {
                ch = (char) (ch + 32);
            }
            sb.append(ch);
        }

        return sb.toString();
    }
}
