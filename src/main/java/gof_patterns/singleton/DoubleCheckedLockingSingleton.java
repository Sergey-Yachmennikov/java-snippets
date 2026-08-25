package gof_patterns.singleton;

public class DoubleCheckedLockingSingleton {
    private static volatile DoubleCheckedLockingSingleton instance; // volatile ОБЯЗАТЕЛЕН

    private DoubleCheckedLockingSingleton() {}

    public static DoubleCheckedLockingSingleton getInstance() {
        if (instance == null) {                  // первая проверка — без блокировки
            synchronized (DoubleCheckedLockingSingleton.class) {
                if (instance == null) {           // вторая проверка — уже внутри блокировки
                    instance = new DoubleCheckedLockingSingleton();
                }
            }
        }

        return instance;
    }
}
