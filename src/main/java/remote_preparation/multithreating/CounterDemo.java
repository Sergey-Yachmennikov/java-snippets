package remote_preparation.multithreating;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CounterDemo {

    public interface Counter {
        void increment();
        long get();
        String name();
    }

    // unsave
    public static final class UnsafeCounter implements Counter {
        private long value;

        public void increment() { value++; }
        public long get()       { return value; }
        public void set(long v) { value = v; }   // для явной демонстрации гонки
        public String name()    { return "1. Без синхронизации (long)"; }
    }

    // volatile
    public static final class VolatileCounter implements Counter {
        private volatile long value;

        public void increment() { value++; }   // всё ещё read-modify-write!
        public long get()       { return value; }
        public String name()    { return "2. volatile (тоже сломано!)"; }
    }

    // synchronized
    public static final class SynchronizedCounter implements Counter {
        private long value;

        public synchronized void increment() { value++; }
        public synchronized long get()       { return value; }
        public String name()                 { return "3. synchronized"; }
    }

    // explicit locking
    public static final class ReentrantLockCounter implements Counter {
        private final ReentrantLock lock = new ReentrantLock();
        private long value;

        public void increment() {
            lock.lock();
            try { value++; } finally { lock.unlock(); }
        }
        public long get() {
            lock.lock();
            try { return value; } finally { lock.unlock(); }
        }
        public String name() { return "4. ReentrantLock"; }
    }

    // Read Write Locking
    public static final class ReadWriteLockCounter implements Counter {
        private final ReentrantReadWriteLock rw = new ReentrantReadWriteLock();
        private long value;

        public void increment() {
            rw.writeLock().lock();
            try { value++; } finally { rw.writeLock().unlock(); }
        }

        public long get() {
            rw.readLock().lock();
            try { return value; } finally { rw.readLock().unlock(); }
        }
        public String name() { return "6. ReadWriteLock (антипаттерн)"; }
    }

    public static final class AtomicLongCounter implements Counter {
        private final AtomicLong value = new AtomicLong();

        public void increment() { value.incrementAndGet(); }
        public long get()       { return value.get(); }
        public String name()    { return "9. AtomicLong"; }
    }


    public static final class LongAdderCounter implements Counter {
        private final LongAdder value = new LongAdder();

        public void increment() { value.increment(); }
        public long get()       { return value.sum(); }
        public String name()    { return "10. LongAdder"; }
    }

}
