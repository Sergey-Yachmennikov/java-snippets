package gof_patterns.singleton;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnumSingletonTest {

    @Test
    void get() {
        String key = "key";
        String value = "value";
        EnumSingleton.INSTANCE.set(key, value);
        assertEquals(value, EnumSingleton.INSTANCE.get(key));
    }
}