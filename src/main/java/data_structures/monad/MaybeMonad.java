package data_structures.monad;

import java.util.function.Function;

public class MaybeMonad <T> {
    private static MaybeMonad<?> empty = new MaybeMonad(null);

    T value;

    private MaybeMonad(T value) {
        this.value = value;
    }

    public static <T> MaybeMonad<T> from(T value) {
        return value != null ? new MaybeMonad<>(value) : (MaybeMonad<T>) empty;
    }

    public <U> MaybeMonad<U> map(Function<T, U> mapFunc) {
        return value != null ? new MaybeMonad<U>(mapFunc.apply(value)) : (MaybeMonad<U>) empty;
    }

    public T unwrapValue() {
        return value != null ? value : null;
    }
}
