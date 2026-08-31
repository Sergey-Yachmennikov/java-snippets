package remote_preparation.multithreating.balancer.strategy;

import java.net.URI;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public final class RoundRobinStrategy implements BalancingStrategy {

    private final AtomicInteger counter = new AtomicInteger();

    @Override
    public URI select(List<URI> snapshot) {
        int index = Math.floorMod(counter.getAndIncrement(), snapshot.size());
        return snapshot.get(index);
    }

    @Override
    public String toString() {
        return "round-robin";
    }
}

