/**
 * Package: com.demo.j2ee.zookeeper
 * Description: 
 */
package com.learning.j2ee.zookeeper;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

/**
 * Description:  
 * Date: 2017年12月11日 下午2:13:52
 * @author wufenyun 
 */
public class ZookeeperDemo {
    
    private static final int sessionTimeout = 3000;
    private static final String connectString = "localhost:2181";
    
    private CountDownLatch csLatch = new CountDownLatch(2);
    private ZooKeeper zk;
    
    @Test
    public void createSession() throws IOException, InterruptedException {
        System.out.println("first connection");
        ZooKeeper zk = new ZooKeeper(connectString, sessionTimeout, new CreateSessionWatcher());
        
        System.out.println("second connection");
        long sessionId = zk.getSessionId();
        byte[] sessionPasswd = zk.getSessionPasswd();
        zk = new ZooKeeper(connectString, sessionTimeout, new CreateSessionWatcher(), sessionId+1, sessionPasswd);
        csLatch.await();
        System.out.println("over");
    }
    
    @Test
    public void createPath() throws IOException, InterruptedException, KeeperException {
        
        createZk();
        
        if(zk.exists("/znede1", true) == null) {
            String znode1 = zk.create("/znode1", "node1".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println("节点创建成功：" + znode1);
        }
        
        String znode11 = zk.create("/znode1/znode11", "node11".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("节点创建成功：" + znode11);
    }
    
    @Test
    public void getChildren() throws IOException, InterruptedException, KeeperException {
        createZk();
        CountDownLatch chLatch = new CountDownLatch(1);
        String path = "/znode1";
        List<String> children = zk.getChildren(path,new Watcher() {

            @Override
            public void process(WatchedEvent event) {
                System.out.println("子列表发生变化");
                chLatch.countDown();
            }
        });
        System.out.println(JSON.toJSONString(children));
        
        String znode12 = zk.create("/znode1/znode12", "znode12".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("节点创建成功：" + znode12);
        chLatch.await();
        System.out.println(zk.getChildren(path,false));
        
    }
    
    @Test
    public void getData() throws IOException, InterruptedException, KeeperException {
        createZk();
        CountDownLatch chLatch = new CountDownLatch(1);
        String path = "/znode1";
        //zk.setData(path, data, version)
        byte[] data = zk.getData(path, false, null);
        System.out.println(new String(data));
    }
    
    private void createZk() throws IOException, InterruptedException {
        CountDownLatch cnLatch = new CountDownLatch(1);
        zk = new ZooKeeper(connectString, sessionTimeout, new Watcher(){

            @Override
            public void process(WatchedEvent event) {
                cnLatch.countDown();
                System.out.println("监听到事件,事件状态:" + event.getState());
            }
        });
        
        cnLatch.await();
        System.out.println("连接成功");
    }
    
    public class CreateSessionWatcher implements Watcher {

        @Override
        public void process(WatchedEvent event) {
            csLatch.countDown();
            System.out.println("监听到事件,事件状态:" + event.getState());
        }
        
    }
    
    
    
    
    
    
    @Test
    public void test() throws Exception{
        /*ZooKeeper zooKeeper = new ZooKeeper(HOST, TIME_OUT, null);
        String result = zooKeeper.create("/znode1", "helloworld".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(result);
        
        zooKeeper.delete("/znode1", -1);
        
        Stat stat = zooKeeper.exists("/znode1", false);
        System.out.println(JSON.toJSONString(stat));
        
        byte[] data = zooKeeper.getData("/znode1", null, null);
        System.out.println(new String(data));
        
        stat = zooKeeper.setData("/znode1", "hello world".getBytes(), -1);
        System.out.println(JSON.toJSONString(stat));
        data = zooKeeper.getData("/znode1", null, null);
        System.out.println(new String(data));
        
        System.out.println(zooKeeper.getSessionId());*/
        /*ZooKeeper zookeeper = new ZooKeeper(HOST, TIME_OUT, null);
        System.out.println("=========创建节点===========");
        if(zookeeper.exists("/test", false) == null)
        {
            zookeeper.create("/test", "znode1".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        System.out.println("=============查看节点是否安装成功===============");
        System.out.println(new String(zookeeper.getData("/test", false, null)));
         
        System.out.println("=========修改节点的数据==========");
        String data = "zNode2";
        zookeeper.setData("/test", data.getBytes(), -1);
         
        System.out.println("========查看修改的节点是否成功=========");
        System.out.println(new String(zookeeper.getData("/test", false, null)));
         
        System.out.println("=======删除节点==========");
        zookeeper.delete("/test", -1);
         
        System.out.println("==========查看节点是否被删除============");
        System.out.println("节点状态：" + zookeeper.exists("/test", false));
         
        zookeeper.close();*/
    } 
    
}
