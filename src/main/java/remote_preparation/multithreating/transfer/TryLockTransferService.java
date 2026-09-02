package remote_preparation.multithreating.transfer;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * Альтернатива упорядоченному захвату: tryLock с таймаутом и полным откатом.
 *
 * Зачем, если упорядочивание проще и надёжнее: глобальный порядок можно
 * задать не всегда. Если локи берутся в разных подсистемах или приходят
 * извне, единой шкалы для сортировки не существует. tryLock не требует
 * ничего знать о других локах — он просто не ждёт бесконечно.
 *
 * Цена: возможен ЛАЙВЛОК — два потока синхронно захватывают, синхронно
 * отступают и повторяют бесконечно. Поэтому пауза между попытками
 * обязательно случайная (джиттер), а не фиксированная.
 */
public final class TryLockTransferService {

    private static final long LOCK_TIMEOUT_MS = 50;
    private static final int MAX_ATTEMPTS = 100;

    private TryLockTransferService() {}

    /**
     * @return false в том числе если не удалось захватить локи за MAX_ATTEMPTS попыток —
     *         вызывающий сам решает, ретраить ли на своём уровне
     */
    public static boolean transferMoney(Account from, Account to, BigDecimal amount) {
        Transfers.validate(from, to, amount);
        if (Transfers.isSelfTransfer(from, to)) {
            return false;
        }

        try {
            for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
                if (from.lock().tryLock(LOCK_TIMEOUT_MS, TimeUnit.MILLISECONDS)) {
                    try {
                        if (to.lock().tryLock(LOCK_TIMEOUT_MS, TimeUnit.MILLISECONDS)) {
                            try {
                                BigDecimal fromBalance = from.balance();
                                if (fromBalance.compareTo(amount) < 0) {
                                    return false;
                                }
                                from.setBalance(Account.normalize(fromBalance.subtract(amount)));
                                to.setBalance(Account.normalize(to.balance().add(amount)));
                                return true;
                            } finally {
                                to.lock().unlock();
                            }
                        }
                    } finally {
                        // Второй лок не взялся — обязаны отпустить первый,
                        // иначе получим тот же дедлок, от которого уходили
                        from.lock().unlock();
                    }
                }
                // Случайная пауза разводит потоки во времени и ломает лайвлок
                Thread.sleep(ThreadLocalRandom.current().nextLong(1, 10));
            }
            return false;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }
}
