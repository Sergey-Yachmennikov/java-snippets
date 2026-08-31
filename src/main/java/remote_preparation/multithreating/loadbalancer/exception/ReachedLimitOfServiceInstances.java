package remote_preparation.multithreating.loadbalancer.exception;

/**
 * Выбрасывается при попытке зарегистрировать инстанс,
 * когда достигнут максимальный лимит
 */
public class ReachedLimitOfServiceInstances extends Exception {

    public ReachedLimitOfServiceInstances(String message) {
        super(message);
    }
}
