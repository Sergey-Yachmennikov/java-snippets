package remote_preparation.multithreating.loadbalancer;

import remote_preparation.multithreating.loadbalancer.exception.NoInstancesAvailableException;
import remote_preparation.multithreating.loadbalancer.exception.ReachedLimitOfServiceInstances;
import remote_preparation.multithreating.loadbalancer.exception.ServiceInstanceAlreadyExistsException;
import remote_preparation.multithreating.loadbalancer.strategy.LoadBalancingStrategy;
import remote_preparation.multithreating.loadbalancer.strategy.RandomStrategy;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LoadBalanceService {

    private static final int MAX_INSTANCES = 10;
    private final List<URI> serviceInstances = new ArrayList<>();
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final LoadBalancingStrategy strategy;

    public LoadBalanceService() {
        this(new RandomStrategy());
    }

    // Конструктор со стратегией
    public LoadBalanceService(LoadBalancingStrategy strategy) {
        if (strategy == null) {
            throw new IllegalArgumentException("Strategy cannot be null");
        }

        this.strategy = strategy;
    }

    public URI selectServiceInstance() throws NoInstancesAvailableException {
        lock.readLock().lock();
        try {
            if (serviceInstances.isEmpty()) {
                throw new NoInstancesAvailableException("No instances registered");
            }
            return strategy.selectInstance(serviceInstances);
        } finally {
            lock.readLock().unlock();
        }
    }

    public boolean registerServiceInstance(URI serviceInstance) throws ReachedLimitOfServiceInstances, ServiceInstanceAlreadyExistsException {
        if (serviceInstance == null) {
            throw new IllegalArgumentException("serviceInstance cannot be null");
        }

        lock.writeLock().lock();
        try {
            if (serviceInstances.size() >= MAX_INSTANCES) {
                throw new ReachedLimitOfServiceInstances("Reached limit of service instances");
            }

            if (serviceInstances.contains(serviceInstance)) {
                throw new ServiceInstanceAlreadyExistsException("Instance already registered: " + serviceInstance);
            }

            return serviceInstances.add(serviceInstance);

        } finally {
            lock.writeLock().unlock();
        }
    }
}
