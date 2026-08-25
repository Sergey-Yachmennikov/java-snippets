package gof_patterns.singleton;

import java.util.HashMap;
import java.util.Map;

public enum EnumSingleton {
    INSTANCE;

    private final Map<String, String> settings = new HashMap<>();

    void set(String key, String value) { settings.put(key, value); }
    String get(String key) { return settings.get(key); }
}
