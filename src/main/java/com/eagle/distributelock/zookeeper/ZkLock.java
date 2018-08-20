package com.eagle.distributelock.zookeeper;

import com.eagle.distributelock.Lock;
import com.eagle.distributelock.LockFailureException;
import com.eagle.distributelock.util.AssertUtil;
import com.eagle.distributelock.util.ZkPathUtil;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class ZkLock implements Lock,Watcher {
    private final static Logger logger = LoggerFactory.getLogger(ZkLock.class);
    private final static int sessionTimeout = 30000;
    public final static String SLASH = "/";

    private TreeSet<String> sortedNode = new TreeSet<>();
    private ZooKeeper zkClient;
    private String lockName;
    private String currentNodeName;

    private String rootNode;
    private CountDownLatch waitLockRelease;

    public ZkLock(String lockName, String zkConnection) {
        rootNode = SLASH + lockName;
        try {
            zkClient = new ZooKeeper(zkConnection, sessionTimeout,this);
            Stat stat = zkClient.exists(rootNode,false);
            if(null == stat) {
                zkClient.create(rootNode, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (IOException | KeeperException | InterruptedException e) {
            if(e instanceof ZkNodeExistsException) {
                //if existed,do nothing
            } else {
                throw new LockFailureException(e.getMessage());
            }
        }

        this.lockName = lockName;
    }

    @Override
    public void lock() {
        lock(-1);
    }

    @Override
    public void lock(long timeoutSeconds) {
        if(tryLock()) {
            logger.info(Thread.currentThread().getName() + " 当前线程获得锁");
            return;
        } else {
            logger.info(Thread.currentThread().getName() + " 等待锁");
            await(timeoutSeconds);
        }
    }

    @Override
    public void unlock() {
        try {
            logger.info(Thread.currentThread().getName() + "释放锁");
            zkClient.delete(currentNodePath(), 0);
            sortedNode.clear();
        } catch (InterruptedException | KeeperException e) {
            throw new LockFailureException(e.getMessage());
        } finally {
            try {
                zkClient.close();
            } catch (InterruptedException e) {
                throw new LockFailureException(e.getMessage());
            }
        }
    }

    private boolean tryLock() {
        String currentNodePath = null;
        try {
            currentNodePath = zkClient.create(rootNode + SLASH, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            List<String> competitorNodeNames = zkClient.getChildren(rootNode,false);

            AssertUtil.notEmpty(competitorNodeNames);
            competitorNodeNames.forEach(nodeName -> {
                sortedNode.add(nodeName);
            });
            currentNodeName = ZkPathUtil.getNodeNameByPath(currentNodePath);
            return currentNodeName.equals(getFirstNodeName());
        } catch (KeeperException | InterruptedException e) {
            throw new LockFailureException(e.getMessage());
        }
    }

    /**
     * 等待锁持有者释放锁
     * @param timeoutSeconds
     */
    private void await(long timeoutSeconds) {
        String prevNodePath = prevNodePath();
        try {
            Stat stat = zkClient.exists(prevNodePath, true);
            if(null != stat) {
                waitLockRelease = new CountDownLatch(1);
                if(timeoutSeconds < 0) {
                    waitLockRelease.await();
                    logger.info(Thread.currentThread().getName() + "获得锁");
                } else if(!waitLockRelease.await(timeoutSeconds, TimeUnit.SECONDS)) {
                    throw new LockFailureException("获取锁超时！");
                }
            }
        } catch (KeeperException | InterruptedException e) {
            throw new LockFailureException(e.getMessage());
        }
    }

    @Override
    public void process(WatchedEvent event) {
        if (this.waitLockRelease != null) {
            waitLockRelease.countDown();
        }
    }

    private String prevNodePath() {
        return rootNode + SLASH + sortedNode.lower(currentNodeName);
    }

    private String currentNodePath() {
        return rootNode + SLASH + currentNodeName;
    }

    private String getFirstNodeName() {
        return sortedNode.first();
    }

}
