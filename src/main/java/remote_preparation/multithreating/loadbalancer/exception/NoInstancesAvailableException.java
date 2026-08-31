package remote_preparation.multithreating.loadbalancer.exception;

/**
 * Выбрасывается при попытке выбрать инстанс,
 * когда не зарегистрировано ни одного
 */
public class NoInstancesAvailableException extends Exception {

    public NoInstancesAvailableException(String message) {
        super(message);
    }
}