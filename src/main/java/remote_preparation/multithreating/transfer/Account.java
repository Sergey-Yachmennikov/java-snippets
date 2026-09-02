package remote_preparation.multithreating.transfer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Счёт. Один класс обслуживает обе стратегии, потому что сигнатура
 * transferMoney(Account, Account, ...) зафиксирована условием.
 *
 * Внутри есть и лок, и атомарная ссылка на баланс:
 *  - пессимистичная стратегия меняет баланс под локом (get + set);
 *  - оптимистичная работает через compareAndSetBalance без локов.
 *
 * В проде эти две модели стоило бы развести по разным типам:
 * держать неиспользуемый ReentrantLock в каждом объекте счёта — лишние
 * ~48 байт на объект, что заметно на миллионах счетов в памяти.
 *
 * Баланс — AtomicReference<BigDecimal>, а не volatile BigDecimal:
 * volatile даёт видимость, но не даёт атомарного read-modify-write,
 * без которого lock-free списание невозможно.
 */
public final class Account {

    public static final int SCALE = 2;

    private final long id;

    /** Неатомарные пути изменения баланса защищаются этим локом. */
    private final ReentrantLock lock = new ReentrantLock();

    private final AtomicReference<BigDecimal> balance;

    public Account(long id, BigDecimal initialBalance) {
        Objects.requireNonNull(initialBalance, "initialBalance");
        if (initialBalance.signum() < 0) {
            throw new IllegalArgumentException("Начальный баланс не может быть отрицательным");
        }
        this.id = id;
        this.balance = new AtomicReference<>(normalize(initialBalance));
    }

    public long id() {
        return id;
    }

    /** Одно чтение volatile-поля внутри AtomicReference. */
    public BigDecimal balance() {
        return balance.get();
    }

    ReentrantLock lock() {
        return lock;
    }

    /** Только для пессимистичного пути: вызывать строго под локом этого счёта. */
    void setBalance(BigDecimal newBalance) {
        balance.set(newBalance);
    }

    /**
     * Атомарная замена баланса.
     *
     * ВАЖНО: AtomicReference.compareAndSet сравнивает ссылки через ==, а НЕ через equals.
     * Для BigDecimal это принципиально: new BigDecimal("100.0").equals(new BigDecimal("100.00"))
     * возвращает false из-за разного scale, и опора на equals была бы ловушкой.
     * Сравнение по ссылке корректно ровно потому, что в expected всегда передаётся
     * тот же объект, который был только что прочитан из balance.get().
     */
    boolean compareAndSetBalance(BigDecimal expected, BigDecimal updated) {
        return balance.compareAndSet(expected, updated);
    }

    static BigDecimal normalize(BigDecimal value) {
        // HALF_EVEN (банковское округление) не смещает сумму систематически
        // в одну сторону, в отличие от HALF_UP
        return value.setScale(SCALE, RoundingMode.HALF_EVEN);
    }

    @Override
    public String toString() {
        return "Account{id=" + id + ", balance=" + balance.get() + "}";
    }
}
