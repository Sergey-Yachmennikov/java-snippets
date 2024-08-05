package neural_network;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.DoubleUnaryOperator;

public class Network<T> {
    private List<Layer> layers = new ArrayList<>();

    public Network(int[] layerStructure, double learningRate, DoubleUnaryOperator activationFunction,
                   DoubleUnaryOperator derivativeActivationFunction) {
        if (layerStructure.length < 3) {
            throw new IllegalArgumentException("Error: Should be at least 3 layers (1 input, 1 hidden, 1 output).");
        }

        // входной слой
        Layer inputLayer = new Layer(Optional.empty(), layerStructure[0],
                learningRate, activationFunction, derivativeActivationFunction);
        layers.add(inputLayer);

        // скрытые слои и выходной слой
        for (int i = 1; i < layerStructure.length; i++) {
            Layer nextLayer = new Layer(Optional.of(layers.get(i - 1)), layerStructure[i], learningRate,
                    activationFunction, derivativeActivationFunction);
            layers.add(nextLayer);
        }
    }

    // Помещает входные данные на первый слой, затем выводит их
    // с первого слоя и подает на второй слой в качестве входных данных,
    // со второго — на третий и т. д.
    private double[] outputs(double[] input) {
        double[] result = input;
        for (Layer layer : layers) {
            result = layer.outputs(result);
        }

        return result;
    }

    // Определяет изменения каждого нейрона на основании ошибок
    // выходных данных по сравнению с ожидаемым выходом
    private void backpropagate(double[] expected) {
        // вычисление дельты для нейронов выходного слоя
        int lastLayer = layers.size() - 1;
        layers.get(lastLayer).calculateDeltasForOutputLayer(expected);
        // вычисление дельты для скрытых слоев в обратном порядке
        for (int i = lastLayer - 1; i >= 0; i--) {
            layers.get(i).calculateDeltasForHiddenLayer(layers.get(i + 1));
        }
    }

    //    Сама функция backpropagate() не изменяет веса
    //    Функция update_weights использует дельты, вычисленные в backpropagate(),
    //    чтобы действительно изменить веса
    private void updateWeights() {
        for (Layer layer : layers.subList(1, layers.size())) {
            for (Neuron neuron : layer.neurons) {
                for (int w = 0; w < neuron.weights.length; w++) {
                    neuron.weights[w] = neuron.weights[w] + (neuron.learningRate
                            * layer.previousLayer.get().outputCache[w] * neuron.delta);
                }
            }
        }
    }

    // Функция train() использует результаты выполнения функции outputs()
    // для нескольких входных данных, сравнивает их с ожидаемыми результатами
    // и передает полученное функциям backpropagate() и updateWeights()
    public void train(List<double[]> inputs, List<double[]> expecteds) {
        for (int i = 0; i < inputs.size(); i++) {
            double[] xs = inputs.get(i);
            double[] ys = expecteds.get(i);
            outputs(xs);
            backpropagate(ys);
            updateWeights();
        }
    }
}