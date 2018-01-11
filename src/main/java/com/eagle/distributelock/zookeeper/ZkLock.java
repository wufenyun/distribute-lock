/**
 * Package: com.eagle.distributelock.zookeeper
 * Description: 
 */
package com.eagle.distributelock.zookeeper;

import java.io.IOException;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eagle.distributelock.Lock;
import com.eagle.distributelock.LockFailureException;

/**
 * Description: 基于zookeeper实现的分布式锁 
 * Date: 2018年1月10日 下午3:38:52
 * @author wufenyun 
 */
public class ZkLock implements Lock,Watcher {
    
    private final static Logger logger = LoggerFactory.getLogger(ZkLock.class);
    
    private ZooKeeper client;
    /**
     * 当前节点
     */
    private Competitor currentNode;
    /**
     * 所有锁的竞争者 
     */
    private TreeSet<Competitor> competitors;
    /**
     * 闭锁等待锁竞争者释放
     */
    private CountDownLatch waitLockRelease;
    /**
     * 代表创建锁的根目录
     */
    private static String ROOT_LOCK;
    /**
     * zookeeper session超时时间
     */
    private final static int sessionTimeout = 30000;
    private final static String PREFIX = "/";
    
    public ZkLock(String lockName,String connectionStr) {
        try {
            ROOT_LOCK = PREFIX + lockName;
            client = new ZooKeeper(connectionStr, sessionTimeout, this);
            Stat stat = client.exists(ROOT_LOCK,false);
            if(null == stat) {
                client.create(ROOT_LOCK, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (IOException | KeeperException | InterruptedException e) {
            throw new LockFailureException(e.getMessage());
        }
    }
    
    @Override
    public void lock() {
        lock(-1);
    }
    
    @Override
    public void lock(long timeoutSeconds) {
        if(tryLock()) {
            logger.debug(Thread.currentThread().getName() + " 当前线程获得锁");
            return;
        } else {
            logger.debug(Thread.currentThread().getName() + " 等待锁");
            await(timeoutSeconds);
        }
    }
    
    private boolean tryLock() {
        try {
            client.create(currentNode(), null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        } catch (KeeperException | InterruptedException e) {
            throw new LockFailureException(e.getMessage());
        }
        setCompetitors();
        return currentNode.equals(firstCompetitor());
    }
    
    /** 
     * 等待锁持有者释放锁
     * @param timeoutSeconds
     */
    private void await(long timeoutSeconds) {
        try {
        Competitor prev = prevCompetitor();
        Stat stat;
            stat = client.exists(prev.zkPath(), true);
            if(null != stat) {
                waitLockRelease = new CountDownLatch(1);
                if(timeoutSeconds < 0) {
                    waitLockRelease.await();
                } else if(!waitLockRelease.await(timeoutSeconds, TimeUnit.SECONDS)) {
                    throw new LockFailureException("获取锁超时！");
                }
            }
        } catch (KeeperException | InterruptedException e) {
            throw new LockFailureException(e.getMessage());
        }
    }

    /** 
     * 从zk获取所有锁的竞争者,并保存在有序的TreeSet中
     */
    private void setCompetitors() {
        competitors = new TreeSet<>();
        List<String> nodes;
        try {
            nodes = client.getChildren(ROOT_LOCK, false);
            for(String cm:nodes) {
                if(cm.startsWith(currentNode.name)) {
                    currentNode.setSeriaNumber(cm.split("_")[1]);
                }
                Competitor c = new Competitor(cm);
                competitors.add(c);
            }
        } catch (KeeperException | InterruptedException e) {
            throw new LockFailureException(e.getMessage());
        }
    }
    
    /** 
     * 位于第一位的锁竞争者
     * @return
     */
    private Competitor firstCompetitor() {
        return competitors.first();
    }
    
    /** 
     * 当前节点的前一竞争节点
     * @return
     */
    private Competitor prevCompetitor() {
        return competitors.lower(currentNode);
    }
    
    private String currentNode() {
        if(currentNode == null) {
            currentNode = new Competitor();
            return currentNode.path();
        } else {
            throw new LockFailureException("同一JVM内多个线程不能共用一个锁");
        }
    }
    
    @Override
    public void unlock() {
        try {
            logger.debug(Thread.currentThread().getName() + "释放锁");
            client.delete(currentNode.zkPath(), 0);
        } catch (InterruptedException | KeeperException e) {
            throw new LockFailureException(e.getMessage());
        }
    }
    
    @Override
    public void process(WatchedEvent event) {
        if (this.waitLockRelease != null) {
            waitLockRelease.countDown();
        }
    }
    
    
    /**
     * 锁竞争者的抽象
     */
    public static class Competitor implements Comparable<Competitor>{
        
        /**
         * 标识竞争者的name
         */
        private String name;
        /**
         * zk生成有序队列时自动添加的序号
         */
        private String seriaNumber;
        
        public Competitor() {
            this.name = System.nanoTime() + "";
        }
        
        public Competitor(String info) {
            this.name = info.split("_")[0];
            this.seriaNumber = info.split("_")[1];
        }
        
        public String getRelativePath() {
            return PREFIX + name;
        }
        
        public String path() {
            return ROOT_LOCK + PREFIX + name + "_";
        }
        
        public String zkPath() {
            return ROOT_LOCK + PREFIX + name + "_" + seriaNumber;
        }
        
        @Override
        public String toString() {
            return name;
        }

        @Override
        public int compareTo(Competitor o) {
            return seriaNumber.compareTo(o.seriaNumber);
        }
        
        @Override
        public boolean equals(Object o) {
            if(o instanceof Competitor) {
                return name.equals(((Competitor)o).name);
            } else {
                return false;
            }
        }
        
        @Override
        public int hashCode() {
            return name.hashCode();
        }
        
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        public String getSeriaNumber() {
            return seriaNumber;
        }

        public void setSeriaNumber(String seriaNumber) {
            this.seriaNumber = seriaNumber;
        }
    }

}
