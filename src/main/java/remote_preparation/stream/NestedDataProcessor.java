package remote_preparation.stream;

import java.util.Arrays;
import java.util.List;

public class NestedDataProcessor {
    public static void main(String[] args) {
        List<String> data = List.of("1,2,3", "4,5,6", "7,8,9");

        int sum = data.stream()
                .flatMapToInt(line -> Arrays.stream(line.split(","))
                        .peek(v -> System.out.println(((Object) v).getClass().getPackageName()))
                        .mapToInt(Integer::parseInt))
                .sum();
    }
}