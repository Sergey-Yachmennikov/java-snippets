package remote_preparation.multithreating.transfer;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Общая валидация для обеих стратегий. Вынесена отдельно,
 * чтобы правила были ровно в одном месте.
 */
final class Transfers {

    private Transfers() {}

    /**
     * @throws IllegalArgumentException при некорректных аргументах —
     *         это баг вызывающего, а не бизнес-отказ, поэтому исключение,
     *         а не false. Возврат false зарезервирован за одной ситуацией:
     *         на счёте не хватает средств.
     */
    static void validate(Account from, Account to, BigDecimal amount) {
        Objects.requireNonNull(from, "from");
        Objects.requireNonNull(to, "to");
        Objects.requireNonNull(amount, "amount");

        if (amount.signum() <= 0) {
            // Отрицательная сумма превратила бы перевод в списание в обратную сторону
            // в обход проверки достаточности средств
            throw new IllegalArgumentException("Сумма перевода должна быть положительной: " + amount);
        }
        if (amount.scale() > Account.SCALE) {
            throw new IllegalArgumentException(
                    "Сумма не может иметь больше " + Account.SCALE + " знаков после запятой: " + amount);
        }
        if (from != to && from.id() == to.id()) {
            // Иначе упорядочивание локов по id сломается и даст дедлок
            throw new IllegalStateException("Два разных объекта с одинаковым id: " + from.id());
        }
    }

    /**
     * Перевод самому себе — не ошибка аргументов, но и не операция.
     * Возвращаем false, чтобы такая попытка была заметна вызывающему,
     * а не молча проходила как успешная.
     */
    static boolean isSelfTransfer(Account from, Account to) {
        return from == to;
    }
}