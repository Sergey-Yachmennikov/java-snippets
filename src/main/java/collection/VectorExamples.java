package collection;

import ch.qos.logback.core.encoder.JsonEscapeUtil;

import java.util.Vector;

public class VectorExamples {

    private VectorExamples() {}

    public static void create() {
        Vector<Integer> vector = new Vector<>(5, 3);
        vector.add(1);
        vector.add(2);
        vector.add(3);
        vector.add(4);
        vector.add(5);
        vector.add(6);
        System.out.println(vector.size());
        System.out.println(vector.capacity());
    }
}
