package com.eagle.distributelock.zk;

import com.eagle.distributelock.zookeeper.ZkLock;
import org.junit.Test;


public class ZkLockTest {
    final String connection = "127.0.0.1:2181";

    @Test
    public void test() throws InterruptedException {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                ZkLock lock = new ZkLock("orderlock",connection);
                lock.lock();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.unlock();
            }
        };

        for(int i=1;i<=10;i++) {
            Thread t = new Thread(r);
            t.start();
        }

        Thread.sleep(60000);
    }

}
