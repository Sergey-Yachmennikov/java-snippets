package remote_preparation.multithreating.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Базовый корректный вариант: одна HashMap под ReadWriteLock.
 *
 * Почему синхронизация обязательна: у HashMap при превышении load factor
 * происходит resize с перестроением таблицы. Конкурентная запись во время
 * resize в Java 7 могла закольцевать бакет и подвесить поток в get()
 * навсегда. В Java 8 порядок при переносе сохраняется и бесконечный цикл
 * ушёл, но потеря записей и чтение полуперестроенной таблицы остались.
 *
 * ReadWriteLock окупается только при read-heavy нагрузке И достаточно
 * долгой критической секции. Здесь секция — одно обращение к мапе,
 * а readLock() при этом делает CAS над общим счётчиком читателей,
 * то есть гоняет одну кэш-линию между всеми ядрами. На write-heavy
 * этот вариант проигрывает даже обычному synchronized.
 */
public final class LockedCache implements Cache {

    private final Map<String, String> map = new HashMap<>();
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public void set(String key, String value) {
        Objects.requireNonNull(key, "key");
        Objects.requireNonNull(value, "value");

        lock.writeLock().lock();
        try {
            map.put(key, value);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public Optional<String> get(String key) {
        Objects.requireNonNull(key, "key");

        lock.readLock().lock();
        try {
            return Optional.ofNullable(map.get(key));
        } finally {
            lock.readLock().unlock();
        }
    }
}
