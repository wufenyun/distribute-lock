package com.eagle.distributelock.zk;

import org.junit.Test;

import com.eagle.distributelock.zookeeper.ZkLock;


public class LockTest {
    
    @Test
    public void test() throws InterruptedException {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                ZkLock lock = new ZkLock("test");
                lock.lock();
                System.out.println(Thread.currentThread().getName() + "获取到锁");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.unlock();
                System.out.println(Thread.currentThread().getName() + "释放锁");
            }
        };
        
        for(int i=0;i<3;i++) {
            Thread t = new Thread(r);
            t.start();
        }
        
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
