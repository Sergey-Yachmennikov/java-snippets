package remote_preparation.multithreating.cache;

import java.util.Optional;

public interface Cache {
    void set(String key, String value);
    Optional<String> get(String key);
}