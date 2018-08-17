package com.eagle.distributelock;

public interface ReadWriteLock {

    Lock readLock();

    Lock writeLock();
}
