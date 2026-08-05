package remote_preparation;

abstract class Builder<T extends Builder<T>> {

    protected String name;

    @SuppressWarnings("unchecked")
    protected T self() {
        return (T) this;
    }

    public T name(String name) {
        this.name = name;
        return self();
    }
}