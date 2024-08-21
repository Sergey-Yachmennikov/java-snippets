package data_structures.monad;

import java.util.function.Function;

public class Monad<T> {

    T value;

    public Monad(T value) {
        this.value = value;
    }

    public static <T> Monad<T> from(T value) {
        return new Monad<>(value);
    }

    public <U> Monad<U> flatMap(Function<T, Monad<U>> mapFn) {
        return mapFn.apply(value);
    }

    public <U> Monad<U> map(Function<T, U> mapFn) {
        return flatMap(v -> new Monad<>(mapFn.apply(v)));
    }

    public T unwrapValue() {
        return value;
    }
}
