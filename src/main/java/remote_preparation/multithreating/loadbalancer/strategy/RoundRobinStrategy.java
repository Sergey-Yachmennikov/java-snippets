package remote_preparation.multithreating.loadbalancer.strategy;

import java.net.URI;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobinStrategy implements LoadBalancingStrategy {

    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public URI selectInstance(List<URI> instances) {
        // Math.floorMod, а не %, — иначе после переполнения int индекс станет
        // отрицательным и пойдёт IndexOutOfBoundsException (любимый вопрос на ревью)
        int index = Math.floorMod(counter.getAndIncrement(), instances.size());
        return instances.get(index);
    }
}