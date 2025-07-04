package com.javarush.island.pukhov.concurrent;

import com.javarush.island.pukhov.api.concurrent.Lockable;
import com.javarush.island.pukhov.exception.ApplicationException;

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
        try {
            return tryLock(0, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ApplicationException(e);
        }
    }


    @Override
    public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
        boolean maxLocked = false;
        boolean minLocked = false;
        try {
            maxLocked = maxLock.tryLock(timeout, unit);
            if (!maxLocked) {
                return false;
            }
            minLocked = minLock.tryLock(timeout, unit);
            if (!minLocked) {
                return false;
            }
            isLocked.set(true);
            return true;
        } finally {
            if (!isLocked.get()) {
                if (minLocked) {
                    minLock.unlock();
                }
                if (maxLocked) {
                    maxLock.unlock();
                }
                isLocked.set(false);
            }
        }
    }

    @Override
    public void unlock() {
        if (isLocked()) {
            maxLock.unlock();
            minLock.unlock();
            isLocked.set(false);
        }
    }

    @Override
    public boolean isLocked() {
        return isLocked.get();
    }
}
