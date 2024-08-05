package statitics;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

public final class Statistics {
    private final List<Double> list;
    private final DoubleSummaryStatistics dss;

    public Statistics(List<Double> list) {
        this.list = list;
        dss = list.stream().collect(Collectors.summarizingDouble(d -> d));
    }

    public double sum() {
        return dss.getSum();
    }

    // Найдите среднее значение (mean)
    public double mean() {
        return dss.getAverage();
    }

    // Найдите сумму дисперсии ((Xi — среднее) ^ 2) / N
    public double variance() {
        double mean = mean();
        return list.stream().mapToDouble(x -> Math.pow((x - mean), 2)).average().getAsDouble();
    }

    // Найдите стандартное отклонение sqrt (дисперсия)
    public double std() {
        return Math.sqrt(variance());
    }

    // Преобразование элементов в соответствующие z-значения (формула z-score = (x-mean) / std)
    public List<Double> zscored() {
        double mean = mean();
        double std = std();

        return list.stream().map(x -> std != 0 ? ((x - mean) / std) : 0.0).toList();
    }

    public double max() {
        return dss.getMax();
    }

    public double min() {
        return dss.getMin();
    }
}
