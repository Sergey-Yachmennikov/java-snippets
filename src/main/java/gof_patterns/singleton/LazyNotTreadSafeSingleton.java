package gof_patterns.singleton;

public class LazyNotTreadSafeSingleton {
    private static LazyNotTreadSafeSingleton instance;

    private LazyNotTreadSafeSingleton() {}

    public static LazyNotTreadSafeSingleton getInstance() {
        if (instance == null) {
            instance = new LazyNotTreadSafeSingleton(); // НЕ потокобезопасно!
        }

        return instance;
    }
}
