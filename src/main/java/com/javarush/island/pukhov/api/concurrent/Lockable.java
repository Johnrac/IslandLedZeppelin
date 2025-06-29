package com.javarush.island.pukhov.api.concurrent;

import java.util.concurrent.locks.Lock;

public interface Lockable {
    public Lock getLock();
}
