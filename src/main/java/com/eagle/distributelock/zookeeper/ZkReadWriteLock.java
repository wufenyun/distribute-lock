package com.eagle.distributelock.zookeeper;

import com.eagle.distributelock.Lock;
import com.eagle.distributelock.LockFailureException;
import com.eagle.distributelock.ReadWriteLock;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class ZkReadWriteLock implements ReadWriteLock {
    private final static Logger logger = LoggerFactory.getLogger(ZkReadWriteLock.class);
    /**
     * zookeeper session超时时间
     */
    private final static int sessionTimeout = 30000;
    private final static String PREFIX = "/";
    /**
     * 代表创建锁的根目录
     */
    private static String ROOT_LOCK;
    private ZkClient zkClient;

    private ReadLock readLock;
    private WriteLock writeLock;

    public ZkReadWriteLock(String lockName,String zkConnection) {
        ROOT_LOCK = PREFIX + lockName;
        zkClient = new ZkClient(zkConnection, sessionTimeout);
        if(!zkClient.exists(ROOT_LOCK)) {
            zkClient.createPersistent(ROOT_LOCK);
        }

        readLock = new ReadLock(lockName,lockName);
        writeLock = new WriteLock(zkClient);
    }

    @Override
    public Lock readLock() {
        return this.readLock;
    }

    @Override
    public Lock writeLock() {
        return this.writeLock;
    }

    public static class ReadLock extends AbstractZkLock {

        public ReadLock(String lockName,String zkConnection) {
            super(lockName,lockName);
        }

        @Override
        public void lock() {
            zkClient.createEphemeralSequential("","");
        }

        @Override
        public void lock(long timeoutSeconds) {

        }

        @Override
        public void unlock() {

        }
    }

    public static class WriteLock implements Lock {
        private ZkClient zkClient;

        public WriteLock(ZkClient zkClient) {
            zkClient = zkClient;
        }

        @Override
        public void lock() {

        }

        @Override
        public void lock(long timeoutSeconds) {

        }

        @Override
        public void unlock() {

        }
    }
}
