package remote_preparation.multithreating.balancer.exceptions;

public class NoAvailableInstanceException extends RuntimeException {

    public NoAvailableInstanceException() {
        super("Нет зарегистрированных инстансов");
    }
}

