package remote_preparation.stream;

import java.util.stream.IntStream;

public class EvenNumbers {

    public int sumOfFirstTenEvens() {
        return IntStream.iterate(2, n -> n + 2)
                .limit(10)
                .sum();
    }
}
