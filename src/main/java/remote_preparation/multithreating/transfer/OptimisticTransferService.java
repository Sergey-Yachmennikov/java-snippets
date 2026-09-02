package remote_preparation.multithreating.transfer;


import java.math.BigDecimal;

/**
 * Оптимистичная (lock-free) стратегия на CAS-циклах.
 *
 * ГЛАВНОЕ ОГРАНИЧЕНИЕ, КОТОРОЕ НУЖНО ПОНИМАТЬ
 * CAS атомарен для ОДНОЙ ячейки памяти. Перевод затрагивает ДВЕ.
 * Значит, между успешным списанием и зачислением существует окно,
 * в котором деньги не находятся ни на одном счёте, и параллельный
 * читатель увидит нарушенный инвариант "сумма балансов постоянна".
 *
 * Строго атомарного lock-free перевода двух счетов через обычный CAS
 * не существует. Корректные варианты:
 *   1) принять окно и компенсировать сбой зачисления (реализовано здесь);
 *   2) multi-word CAS через дескриптор транзакции, который другие потоки
 *      "дочитывают и помогают завершить" — корректно, но это уровень STM;
 *   3) взять лок (см. PessimisticTransferService).
 *
 * Итоговая согласованность здесь есть, немедленной — нет. Для денег это
 * означает, что отчёт о суммарном балансе, снятый в произвольный момент,
 * может не сойтись. Именно поэтому в реальных банковских ядрах для перевода
 * берут пессимистику или транзакцию БД.
 *
 * ПРО ABA
 * Здесь ABA безвреден: нас интересует ЗНАЧЕНИЕ баланса, а не его история.
 * Если баланс успел стать 50 и вернуться к 100, арифметика от этого не портится.
 * Если бы бизнес-логика зависела от отсутствия промежуточных операций
 * (например, лимит "не более N списаний в день"), потребовался бы
 * AtomicStampedReference со счётчиком версии.
 */
public final class OptimisticTransferService {

    /** Порог, после которого спин переходит в уступку планировщику. */
    private static final int SPIN_LIMIT = 64;

    private OptimisticTransferService() {}

    /**
     * @return true — перевод выполнен; false — недостаточно средств
     *         или перевод самому себе
     */
    public static boolean transferMoney(Account from, Account to, BigDecimal amount) {
        Transfers.validate(from, to, amount);
        if (Transfers.isSelfTransfer(from, to)) {
            return false;
        }

        if (!withdraw(from, amount)) {
            return false;
        }

        // ОКНО НЕСОГЛАСОВАННОСТИ: деньги уже списаны, но ещё не зачислены
        try {
            deposit(to, amount);
        } catch (RuntimeException e) {
            // Компенсация. Здесь она надёжна, потому что deposit — чистое
            // сложение без предусловий и упасть может только на OOM/прерывании.
            // Как только у зачисления появятся условия (счёт закрыт, лимит),
            // компенсация сама станет способна упасть, и понадобится
            // журнал незавершённых операций с ручной сверкой.
            deposit(from, amount);
            throw e;
        }
        return true;
    }

    /**
     * Списание: CAS-цикл с перепроверкой достаточности средств на каждой итерации.
     *
     * Проверку нельзя вынести за цикл: между чтением и CAS другой поток мог
     * списать средства, и повторная попытка обязана работать с новым балансом.
     * Именно перечитывание внутри цикла делает check-then-act безопасным.
     */
    private static boolean withdraw(Account account, BigDecimal amount) {
        int spins = 0;
        while (true) {
            BigDecimal current = account.balance();
            if (current.compareTo(amount) < 0) {
                return false;
            }
            BigDecimal updated = Account.normalize(current.subtract(amount));

            // current — ровно та ссылка, что была прочитана выше;
            // compareAndSet сравнивает по ==, поэтому это корректно
            if (account.compareAndSetBalance(current, updated)) {
                return true;
            }
            backoff(++spins);
        }
    }

    /**
     * Зачисление: цикл без условий выхода по бизнес-логике —
     * сложение всегда допустимо, поэтому рано или поздно CAS пройдёт.
     */
    private static void deposit(Account account, BigDecimal amount) {
        int spins = 0;
        while (true) {
            BigDecimal current = account.balance();
            BigDecimal updated = Account.normalize(current.add(amount));
            if (account.compareAndSetBalance(current, updated)) {
                return;
            }
            backoff(++spins);
        }
    }

    /**
     * Стратегия отступления при конкуренции.
     *
     * onSpinWait — подсказка процессору (PAUSE на x86): снижает нагрузку
     * на конвейер и энергопотребление при коротком ожидании.
     * После SPIN_LIMIT итераций уступаем квант: длинный спин на
     * перегруженной машине только отбирает CPU у потока, который
     * как раз и мешает нам завершиться.
     */
    private static void backoff(int spins) {
        if (spins < SPIN_LIMIT) {
            Thread.onSpinWait();
        } else {
            Thread.yield();
        }
    }
}
