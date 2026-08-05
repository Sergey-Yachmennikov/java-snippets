package remote_preparation;

/**
 * Функциональный интерфейс для преобразования объекта одного типа в другой.
 * @param <T> Входной тип
 * @param <R> Выходной тип
 */
@FunctionalInterface
public interface Transformer<T, R> {

    R apply (T input);

    //  Producer Extends — если объект производит данные (выходной тип), используй ? extends
    //  Consumer Super — если объект потребляет данные (входной тип), используй ? super
    default <V> Transformer<T, V> andThen(Transformer<? super R, ? extends V> after) {
        return input -> after.apply(this.apply(input));
    }

    static <T> Transformer<T, T> identity() {
        return input -> input;
    }
}
