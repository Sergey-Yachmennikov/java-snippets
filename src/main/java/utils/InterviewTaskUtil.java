package utils;

import java.util.ArrayList;

public class InterviewTaskUtil {

    private InterviewTaskUtil() {}

    public static void changeArrayListInLoop() {
        ArrayList<String> list = new ArrayList<>();
        list.add("one");
        list.add("two");
        list.add("three");
        list.forEach(s -> {
            System.out.println(s);
            list.add("new");
        });
    }
}
