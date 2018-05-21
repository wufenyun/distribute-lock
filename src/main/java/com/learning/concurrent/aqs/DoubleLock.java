package com.learning.concurrent.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class DoubleLock implements Lock {
    
    final Sync sync;
    
    public DoubleLock() {
        sync = new Sync();
    }
    
    @Override
    public void lock() {
        sync.lock();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        System.out.println("unlock");
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return null;
    }
    
    
    static final class Sync extends AbstractQueuedSynchronizer {
        
        /**
         */
        private static final long serialVersionUID = 1L;

        final void lock() {
            acquire(1);
        }
        
        protected final boolean tryAcquire(int acquires) {
            final Thread current = Thread.currentThread();
            int c = getState();
            if (c == 0 || c == 1) {
                if (!hasQueuedPredecessors() &&
                    compareAndSetState(c, acquires+c)) {
                    //setExclusiveOwnerThread(current);
                    return true;
                }
            }
            /*else if (current == getExclusiveOwnerThread()) {
                int nextc = c + acquires;
                if (nextc < 0)
                    throw new Error("Maximum lock count exceeded");
                setState(nextc);
                return true;
            }*/
            return false;
        }
        
        protected final boolean tryRelease(int releases) {
            int c = getState() - releases;
            /*if (Thread.currentThread() != getExclusiveOwnerThread())
                throw new IllegalMonitorStateException();*/
            boolean free = false;
            if (c == 0) {
                free = true;
            }
            setState(c);
            return free;
        }
    }
}
