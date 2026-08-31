package remote_preparation.multithreating.loadbalancer.strategy;

import java.net.URI;
import java.util.List;
import java.util.Random;

public class RandomStrategy implements LoadBalancingStrategy {

    private final Random random;

    public RandomStrategy() {
        this(new Random());
    }

    // Для тестов
    public RandomStrategy(Random random) {
        this.random = random;
    }

    @Override
    public URI selectInstance(List<URI> instances) {
        if (instances.isEmpty()) {
            throw new IllegalStateException("No instances available");
        }

        int index = random.nextInt(instances.size());
        return instances.get(index);
    }
}
