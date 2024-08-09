package objects;

import java.util.List;

public class IncrementExample {
    private int index;
    private List<Integer> digits = List.of(1,2,3,4,5);

    public int next() {
        return digits.get(index++); // get value then increment index
    }

    public int nextSecond() {
        return digits.get(++index); // then increment index then get value
    }

    public int getIndex() {
        return index;
    }
}
