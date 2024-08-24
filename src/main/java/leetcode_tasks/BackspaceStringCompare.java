package leetcode_tasks;

/**
 * @topic String
 */
public class BackspaceStringCompare {

    private BackspaceStringCompare() {}

    public static boolean backspaceCompare(String str1, String str2) {
        int S_pointer = str1.length() - 1;
        int T_pointer = str2.length() - 1;

        int S_skips = 0;
        int T_skips = 0;

        while (S_pointer >= 0 || T_pointer >= 0) {

            while (S_pointer >= 0) {
                if (str1.charAt(S_pointer) == '#') {
                    S_skips += 1;
                    S_pointer -= 1;
                } else if (S_skips > 0) {
                    S_pointer -= 1;
                    S_skips -=1;
                } else {
                    break;
                }
            }

            while (T_pointer >= 0) {
                if (str2.charAt(T_pointer) == '#') {
                    T_skips += 1;
                    T_pointer -= 1;
                } else if (S_skips > 0) {
                    T_pointer -= 1;
                    T_skips -=1;
                } else {
                    break;
                }
            }

            if (S_skips >= 0 && T_skips >= 0 && str1.charAt(S_pointer) != str2.charAt(T_pointer)) {
                return false;
            }

            if ((S_pointer >= 0) != (T_pointer >= 0)) {
                return false;
            }
        }

        return true;
    }
}
