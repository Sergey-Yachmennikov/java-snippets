package remote_preparation.multithreating.loadbalancer.strategy;


import java.net.URI;
import java.util.List;

public interface LoadBalancingStrategy {
    /**
     * Выбирает инстанс из списка
     * @param instances неизменяемый список доступных инстансов
     * @return выбранный инстанс
     */
    URI selectInstance(List<URI> instances);
}
