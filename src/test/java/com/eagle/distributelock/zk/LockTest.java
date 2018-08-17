package com.eagle.distributelock.zk;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.junit.Test;

import com.eagle.distributelock.zookeeper.ZkLock;

import java.util.UUID;


public class LockTest {
    
    @Test
    public void test() throws InterruptedException {
        final String connection = "127.0.0.1:2181";
        
        Runnable r = new Runnable() {
            @Override
            public void run() {
                ZkLock lock = new ZkLock("test",connection);
                lock.lock();
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
        
        Thread.sleep(60000);
    }

    @Test
    public void uuid() throws InterruptedException {
        System.out.println(UUID.randomUUID());
        final String connection = "127.0.0.1:2181";

        ZkClient client = new ZkClient(connection);
        //client.createPersistent("/lock");
        String a = client.createEphemeralSequential("/lock/lock",null);
        System.out.println(a);
        client.subscribeDataChanges("/lock/lock", new IZkDataListener() {

            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                System.out.println(dataPath + " deleted");
            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {

            }
        });
        client.delete("/lock/lock");
        client.createEphemeralSequential("/lock/a",null);
        client.createEphemeralSequential("/lock/a",null);
        client.createEphemeralSequential("/lock/a",null);

        client.getChildren("/lock").forEach(v ->{
            System.out.println(v);
        });

        Thread.sleep(100000);
        client.close();
    }
    
}
