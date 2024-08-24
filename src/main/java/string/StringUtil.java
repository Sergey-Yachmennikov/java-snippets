package string;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;

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

    @SuppressWarnings("deprecation")
    public static String messageFormat() {
        int planet = 7;
        Date date = new Date(1, Calendar.JANUARY,1);
        String event = "a disturbance in the Force";

        return MessageFormat.format(
                "At {0, time, medium} on {0, date}, there was {1} on planet {2, number, integer}.",
                date, event, planet);
    }

    public static String messageFormat2() {
        int fileCount = 1273;
        String diskName = "MyDisk";
        Object[] testArgs = {fileCount, diskName};

        MessageFormat form = new MessageFormat("The disk \"{1}\" contains {0} file(s).");

        return form.format(testArgs);
    }

    public static int[] codePoints() {
        String temp = "abcd";
        return temp.codePoints().toArray(); // unicode codes of symbol
    }

    public static int codePointCount() {
        String temp = "abcd";
        return temp.codePointCount(0, 3);
    }

    public static int codePointAt() {
        String temp = "abcd";
        return temp.codePointAt(0);
    }
}
