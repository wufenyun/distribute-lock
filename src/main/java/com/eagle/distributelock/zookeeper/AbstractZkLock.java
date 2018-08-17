package com.eagle.distributelock.zookeeper;

import com.eagle.distributelock.Lock;
import com.eagle.distributelock.LockFailureException;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public abstract class AbstractZkLock  implements Lock {
    private final static Logger logger = LoggerFactory.getLogger(AbstractZkLock.class);
    /**
     * zookeeper session超时时间
     */
    private final static int sessionTimeout = 30000;
    private final static String PREFIX = "/";
    /**
     * 代表创建锁的根目录
     */
    private static String ROOT_LOCK;
    protected ZkClient zkClient;
    protected String lockName;
    private TreeSet<String> sortedNode = new TreeSet<>();

    public AbstractZkLock(String lockName,String zkConnection) {
        ROOT_LOCK = PREFIX + lockName;
        zkClient = new ZkClient(zkConnection, sessionTimeout);
        if(!zkClient.exists(ROOT_LOCK)) {
            zkClient.createPersistent(ROOT_LOCK);
        }

        this.lockName = lockName;
    }

    @Override
    public void lock() {
        if(tryLock()) {
            logger.debug(Thread.currentThread().getName() + " 当前线程获得锁");
            return;
        } else {
            logger.debug(Thread.currentThread().getName() + " 等待锁");
            //await(timeoutSeconds);
        }
    }

    @Override
    public void lock(long timeoutSeconds) {

    }

    /**
     * 等待锁持有者释放锁
     * @param timeoutSeconds
     */
    private void await(long timeoutSeconds) {
       /* try {
            ZkLock.Competitor prev = prevCompetitor();
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
        }*/
    }
    private boolean tryLock() {
        String ephemeralPath = zkClient.createEphemeralSequential(currentNode(),null);
        List<String> compitors = zkClient.getChildren(ROOT_LOCK);
        String currentNode = ephemeralPath.substring(6);
        return currentNode.equals(getFirstNode());
        //String prevNode = getPrevNode(compitors,ephemeralPath.substring(6));
    }

    private String getFirstNode() {
        return sortedNode.first();
    }

    private String getPrevNode(List<String> list,String currentNode) {
        list.forEach(node -> {
            sortedNode.add(node.split(":")[1]);
        });
        return sortedNode.higher(currentNode);
    }

    private String currentNode() {
        return ROOT_LOCK + PREFIX + lockName + ":";
    }

    @Override
    public void unlock() {

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
            this.name = "competitor" + System.nanoTime();
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
