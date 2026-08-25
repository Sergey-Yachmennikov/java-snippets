package gof_patterns.singleton;

public class EagerSingleton {
    private static final EagerSingleton INSTANCE = new EagerSingleton();
    // статическая инициализация гарантирует потокобезопасность

    private EagerSingleton() {}

    public static EagerSingleton getInstance() {
        return INSTANCE;
    }
}
