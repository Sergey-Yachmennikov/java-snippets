package remote_preparation.multithreating.transfer;

import java.math.BigDecimal;

/**
 * Пессимистичная стратегия: захватываем оба счёта, затем меняем балансы.
 *
 * ПРОБЛЕМА ДЕДЛОКА И ЕЁ РЕШЕНИЕ
 * Наивный захват "сначала from, потом to" гарантированно даёт дедлок:
 *   поток 1: transfer(A -> B) держит lock(A), ждёт lock(B)
 *   поток 2: transfer(B -> A) держит lock(B), ждёт lock(A)
 *
 * Лечится глобальным порядком захвата: локи берутся всегда по возрастанию id,
 * независимо от направления перевода. Это разрывает условие циклического
 * ожидания (одно из четырёх условий Коффмана), поэтому дедлок становится
 * невозможен по построению, а не маловероятен.
 *
 * ГАРАНТИИ
 * Операция полностью атомарна: промежуточное состояние, где деньги списаны,
 * но не зачислены, не наблюдаемо ни одним потоком, который берёт те же локи.
 * Это главное преимущество перед lock-free версией.
 */
public final class PessimisticTransferService {

    private PessimisticTransferService() {}

    /**
     * @return true — перевод выполнен; false — недостаточно средств
     *         или перевод самому себе
     * @throws IllegalArgumentException при некорректных аргументах
     */
    public static boolean transferMoney(Account from, Account to, BigDecimal amount) {
        Transfers.validate(from, to, amount);
        if (Transfers.isSelfTransfer(from, to)) {
            return false;
        }

        // Порядок захвата определяется id, а НЕ ролью счёта в переводе
        Account first = from.id() < to.id() ? from : to;
        Account second = from.id() < to.id() ? to : from;

        first.lock().lock();
        try {
            second.lock().lock();
            try {
                return doTransfer(from, to, amount);
            } finally {
                second.lock().unlock();
            }
        } finally {
            // finally обязателен: иначе исключение оставит лок захваченным навсегда
            // и заблокирует все последующие операции по счёту
            first.lock().unlock();
        }
    }

    /** Вызывается строго под обоими локами. */
    private static boolean doTransfer(Account from, Account to, BigDecimal amount) {
        BigDecimal fromBalance = from.balance();
        if (fromBalance.compareTo(amount) < 0) {
            return false;
        }
        // compareTo, а не equals: у BigDecimal equals учитывает scale,
        // поэтому 100.0 и 100.00 оказались бы "разными"
        from.setBalance(Account.normalize(fromBalance.subtract(amount)));
        to.setBalance(Account.normalize(to.balance().add(amount)));
        return true;
    }
}
