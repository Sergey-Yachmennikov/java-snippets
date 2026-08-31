package remote_preparation.multithreating.balancer.strategy;


import java.net.URI;
import java.util.List;

@FunctionalInterface
public interface BalancingStrategy {
    URI select(List<URI> snapshot);
}


