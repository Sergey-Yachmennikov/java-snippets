package remote_preparation.multithreating.balancer.strategy;

import java.net.URI;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class RandomStrategy implements BalancingStrategy {

    @Override
    public URI select(List<URI> snapshot) {
        return snapshot.get(ThreadLocalRandom.current().nextInt(snapshot.size()));
    }

    @Override
    public String toString() {
        return "random";
    }
}

