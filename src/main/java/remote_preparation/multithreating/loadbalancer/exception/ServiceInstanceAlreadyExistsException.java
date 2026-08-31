package remote_preparation.multithreating.loadbalancer.exception;

/**
 * Выбрасывается при попытке зарегистрировать уже существующий инстанс
 */
public class ServiceInstanceAlreadyExistsException extends Exception {

    public ServiceInstanceAlreadyExistsException(String message) {
        super(message);
    }
}