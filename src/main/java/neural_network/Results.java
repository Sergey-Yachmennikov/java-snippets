package neural_network;

import java.util.List;
import java.util.function.Function;

public class Results<T> {
    public final int correct;
    public final int trials;
    public final double percentage;

    public Results(int correct, int trials, double percentage) {
        this.correct = correct;
        this.trials = trials;
        this.percentage = percentage;
    }

    // Для параметризованных результатов, которые требуют классификации,
    // эта функция возвращает правильное количество попыток
    // и процентное отношение по сравнению с общим количеством
    public Results validate(List<double[]> inputs, List<T> expecteds,
                            Function<double[], T> interpret) {
        int correct = 0;
        for (int i = 0; i < inputs.size(); i++) {
            double[] input = inputs.get(i);
            T expected = expecteds.get(i);
            T result = interpret.apply(outputs(input));
            if (result.equals(expected)) {
                correct++;
            }
        }
        double percentage = (double) correct / (double) inputs.size();
        return new Results(correct, inputs.size(), percentage);
    }

    public double[] outputs(double[] input) {
        return null; // not implemented yet
    }
}