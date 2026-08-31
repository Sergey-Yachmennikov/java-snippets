package remote_preparation.multithreating.loadbalancer.strategy;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class WeightedRoundRobinStrategy implements LoadBalancingStrategy {

    private final Map<URI, Integer> weights;
    private final AtomicInteger counter = new AtomicInteger(0);

    public WeightedRoundRobinStrategy(Map<URI, Integer> weights) {
        this.weights = new HashMap<>(weights);
    }

    @Override
    public URI selectInstance(List<URI> instances) {
        // Создаем "взвешенный" список
        List<URI> weightedInstances = new ArrayList<>();
        for (URI instance : instances) {
            int weight = weights.getOrDefault(instance, 1);
            for (int i = 0; i < weight; i++) {
                weightedInstances.add(instance);
            }
        }

        int index = counter.getAndIncrement() % weightedInstances.size();
        return weightedInstances.get(index);
    }
}