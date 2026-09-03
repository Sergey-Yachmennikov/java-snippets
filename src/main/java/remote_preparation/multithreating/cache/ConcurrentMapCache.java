package remote_preparation.multithreating.cache;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class ConcurrentMapCache implements Cache {

    private final ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

    @Override
    public void set(String key, String value) {
        // CHM не допускает null ни в ключе, ни в значении: иначе get() == null
        // было бы неотличимо от "ключа нет", а в конкурентной среде
        // перепроверить это через containsKey атомарно невозможно
        Objects.requireNonNull(key, "key");
        Objects.requireNonNull(value, "value");
        map.put(key, value);
    }

    @Override
    public Optional<String> get(String key) {
        Objects.requireNonNull(key, "key");
        return Optional.ofNullable(map.get(key));
    }
}
