package remote_preparation.multithreating.balancer;

import remote_preparation.multithreating.balancer.exceptions.NoAvailableInstanceException;
import remote_preparation.multithreating.balancer.exceptions.TooManyInstancesException;
import remote_preparation.multithreating.balancer.strategy.BalancingStrategy;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LoadBalancer {

    public static final int DEFAULT_MAX_INSTANCES = 10;

    private final int maxInstances;
    private final BalancingStrategy strategy;
    private final Object writeLock = new Object();

    private volatile List<URI> instances = List.of();

    public LoadBalancer(BalancingStrategy strategy) {
        this(strategy, DEFAULT_MAX_INSTANCES);
    }

    public LoadBalancer(BalancingStrategy strategy, int maxInstances) {
        if (maxInstances <= 0) {
            throw new IllegalArgumentException("maxInstances должен быть больше нуля");
        }
        this.strategy = Objects.requireNonNull(strategy, "strategy");
        this.maxInstances = maxInstances;
    }

    public URI get() {
        List<URI> snapshot = instances;

        if (snapshot.isEmpty()) {
            throw new NoAvailableInstanceException();
        }
        return strategy.select(snapshot);
    }

    public boolean register(URI instance) throws TooManyInstancesException {
        URI normalized = validateAndNormalize(instance);

        // Весь check-then-act — под одним локом. Разбить его нельзя:
        // иначе два потока одновременно увидят size == 9 и запишут 11-й инстанс.
        synchronized (writeLock) {
            List<URI> current = instances;

            // Порядок проверок важен: повторная регистрация уже известного инстанса
            // на полном балансировщике должна вернуть false, а не бросить исключение.
            if (current.contains(normalized)) {
                return false;
            }
            if (current.size() >= maxInstances) {
                throw new TooManyInstancesException(maxInstances);
            }

            List<URI> updated = new ArrayList<>(current.size() + 1);
            updated.addAll(current);
            updated.add(normalized);
            instances = List.copyOf(updated); // публикация нового снапшота
            return true;
        }
    }

    public boolean unregister(URI instance) {
        URI normalized = validateAndNormalize(instance);

        synchronized (writeLock) {
            List<URI> current = instances;
            if (!current.contains(normalized)) {
                return false;
            }
            List<URI> updated = new ArrayList<>(current);
            updated.remove(normalized);
            instances = List.copyOf(updated);
            return true;
        }
    }

    public List<URI> instances() {
        return instances;
    }

    public int size() {
        return instances.size();
    }

    public int maxInstances() {
        return maxInstances;
    }

    private static URI validateAndNormalize(URI instance) {
        Objects.requireNonNull(instance, "instance");

        if (!instance.isAbsolute()) {
            throw new IllegalArgumentException("URI должен быть абсолютным: " + instance);
        }

        if (instance.getHost() == null) {
            throw new IllegalArgumentException("URI должен содержать хост: " + instance);
        }

        return instance.normalize();
    }
}
