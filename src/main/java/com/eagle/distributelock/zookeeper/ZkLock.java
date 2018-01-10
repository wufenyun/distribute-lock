/**
 * Package: com.eagle.distributelock.zookeeper
 * Description: 
 */
package com.eagle.distributelock.zookeeper;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.eagle.distributelock.Lock;
import com.eagle.distributelock.zookeeper.ZkLock.Competitor;

/**
 * Description:  
 * Date: 2018年1月10日 下午3:38:52
 * @author wufenyun 
 */
public class ZkLock implements Lock,Watcher {
    
    private final static Logger logger = LoggerFactory.getLogger(ZkLock.class);
    
    private String connection = "127.0.0.1:2181";
    private ZooKeeper client;
    private static String ROOT_LOCK;
    private Competitor currentNode;
    private Competitor prevNode;
    private TreeSet<Competitor> competitors;
    private final static String PREFIX = "/";
    private CountDownLatch notify = new CountDownLatch(1);
    
    public ZkLock(String lockName) {
        ROOT_LOCK = PREFIX+lockName;
        try {
            client = new ZooKeeper(connection, 20000, null);
            Stat stat = client.exists(ROOT_LOCK,false);
            if(null == stat) {
                client.create(ROOT_LOCK, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (IOException | KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
        
    }
    
    @Override
    public void lock() {
        try {
            client.create(currentNode(), null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            //logger.debug("创建当前竞争者节点：" + currentNode);
            setCompetitors();
            if(currentNode.equals(firstCompetitor())) {
                //logger.debug("获取到锁");
                return;
            }
            
            Competitor prev = prevCompetitor();
            Stat stat = client.exists(prev.getName(), true);
            if(null != stat) {
                notify.await(2000, TimeUnit.SECONDS);
            }
        } catch (InterruptedException | KeeperException e) {
            e.printStackTrace();
        }
    }

    private void setCompetitors() {
        competitors = new TreeSet<>();
        List<String> competitors;
        try {
            competitors = client.getChildren(ROOT_LOCK, false);
            for(String cm:competitors) {
                Competitor c = new Competitor(cm);
                competitors.add(cm);
            }
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    private Competitor firstCompetitor() {
        return competitors.first();
    }
    
    private Competitor prevCompetitor() {
        return competitors.higher(currentNode);
    }
    
    private String currentNode() {
        if(currentNode == null) {
            currentNode = new Competitor();
            return currentNode.info();
        } else {
            throw new RuntimeException("同一JVM内多个线程不能共用一个锁");
        }
    }
    
    @Override
    public void unlock() {
        try {
            client.delete(currentNode.info(), 0);
        } catch (InterruptedException | KeeperException e) {
            e.printStackTrace();
        }
        //logger.debug("释放锁");
    }
    
    @Override
    public void process(WatchedEvent event) {
        notify.countDown();
    }
    
    public static class Competitor implements Comparable<String>{
        private String name;
        
        public Competitor() {
            this.name = "" + System.nanoTime();
        }
        
        public Competitor(String name) {
            this.name = name;
        }
        
        public String info() {
            return ROOT_LOCK + PREFIX + name;
        }
        
        @Override
        public String toString() {
            return name;
        }
        
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public int compareTo(String o) {
            return name.compareTo(o);
        }
    }

}
