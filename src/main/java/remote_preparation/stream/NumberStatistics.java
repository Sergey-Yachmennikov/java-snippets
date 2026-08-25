package remote_preparation.stream;

import java.util.IntSummaryStatistics;
import java.util.List;

public class NumberStatistics {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(10, 15, 5, 30, 25, 45);

        IntSummaryStatistics stats = numbers.stream()
                .mapToInt(Integer::intValue)
                .summaryStatistics();

        System.out.println("Минимум: " + stats.getMin());
        System.out.println("Максимум: " + stats.getMax());
        System.out.println("Среднее: " + stats.getAverage());
    }
}
