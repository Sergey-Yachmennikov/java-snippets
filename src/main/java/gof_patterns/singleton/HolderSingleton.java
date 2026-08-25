package gof_patterns.singleton;

public class HolderSingleton {
    private HolderSingleton() {}

    private static class Holder { // класс-держатель НЕ загружается, пока не понадобится
        static final HolderSingleton INSTANCE = new HolderSingleton();
    }

    public static HolderSingleton getInstance() {
        return Holder.INSTANCE; // при первом обращении JVM загружает Holder — и только тогда создаёт INSTANCE
    }
}
