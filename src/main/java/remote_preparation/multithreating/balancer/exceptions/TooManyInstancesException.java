package remote_preparation.multithreating.balancer.exceptions;


public class TooManyInstancesException extends Exception {

    private final int limit;

    public TooManyInstancesException(int limit) {
        super("Достигнут лимит инстансов: " + limit);
        this.limit = limit;
    }

    public int getLimit() {
        return limit;
    }
}

