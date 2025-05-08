package miltithreading;

import java.util.concurrent.locks.Lock;

public class CountThread implements Runnable {

    CommonResource resource;
    Lock locker;

    public CountThread(CommonResource resource, Lock lock) {
        this.resource = resource;
        this.locker = lock;
    }

    @Override
    public void run() {
        locker.lock();
        try {
            resource.x = 1;
            for (int i = 0; i < 5; i++) {
                System.out.printf("%s %d \n", Thread.currentThread().getName(), resource.x);
                resource.x++;
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } finally {
            locker.unlock();
        }
    }
}
