package data_structures.monad;

import java.util.function.Function;
import java.util.function.Supplier;

public class LazyMonad<T> {
    T value;

    Supplier<T> supplier;

    private LazyMonad(T value) {
        this.value = value;
    }

    private LazyMonad(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public static <T> LazyMonad<T> from(T value) {
        return new LazyMonad<>(value);
    }

    public T get() {
        if (value == null) {
            value = supplier.get();
        }
        return value;
    }

    public <U> LazyMonad<U> map(Function<T, U> mapFunc) {
        return new LazyMonad<>(() -> mapFunc.apply(get()));
    }

    public <U> LazyMonad<U> flatMap(Function<T, LazyMonad<U>> mapFunc) {
        return mapFunc.apply(value);
    }
}
