package com.javarush.island.pukhov.concurrent;

import com.javarush.island.pukhov.api.concurrent.Lockable;

import java.io.Serial;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DoubleLock extends ReentrantLock {
    @Serial
    private static final long serialVersionUID = 1905122041950251207L;
    private final transient Lock minLock;
    private final transient Lock maxLock;
    private final AtomicBoolean isLocked = new AtomicBoolean();

    public DoubleLock(Lockable object1, Lockable object2) {
           if (object1.hashCode() > object2.hashCode()) {
               maxLock = object1.getLock();
               minLock = object2.getLock();
           } else {
               maxLock = object2.getLock();
               minLock = object1.getLock();
           }
    }

    @Override
    public void lock() {
        maxLock.lock();
        minLock.lock();
        isLocked.set(true);
    }

    @Override
    public boolean tryLock() {
        if (maxLock.tryLock()) {
            if (minLock.tryLock()) {
                isLocked.set(true);
            } else {
                maxLock.unlock();
            }
        }
        return isLocked.get();
    }

    @Override
    public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {

        if (maxLock.tryLock(timeout, unit)) {
            if (minLock.tryLock(timeout,unit)) {
                isLocked.set(true);
            } else {
                maxLock.unlock();
            }
        }
        return isLocked.get();
    }

    @Override
    public void unlock() {
        if (isLocked()) {
            maxLock.unlock();
            minLock.unlock();
        }
    }

    @Override
    public boolean isLocked() {
        return isLocked.get();
    }
}
