package com.eagle.distributelock.zk;

import org.junit.Test;

import com.eagle.distributelock.zookeeper.ZkLock;


public class LockTest {
    
    @Test
    public void test() throws InterruptedException {
        final String connection = "127.0.0.1:2181";
        
        Runnable r = new Runnable() {
            @Override
            public void run() {
                ZkLock lock = new ZkLock("test",connection);
                lock.lock(10);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.unlock();
            }
        };
        
        for(int i=1;i<11;i++) {
            Thread t = new Thread(r);
            t.start();
        }
        
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
}
