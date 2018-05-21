package com.learning.j2ee.zookeeper;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class ZKclientDemo {
    
    private static final int timeout = 3000;
    private static final String localCs = "localhost:2181";
    private static final String testEnvCs = "172.26.5.60:2181";
    private static final String changelessPath = "/root_node";
    
    private ZkClient client;
    
    private void connect() {
        client = new ZkClient(localCs, timeout, timeout);
    }
    
    private void connect(String connectionString) {
        client = new ZkClient(connectionString, timeout, timeout);
    }
    
    @Test
    public void createRoot() {
        connect(); 
        client.createPersistent(changelessPath, "i'm wu");
        System.out.println(client.getCreationTime(changelessPath));
    }
    
    @Test
    public void createChildren() {
        connect();
        String path = changelessPath + "/node1_1";
        client.createEphemeral(path, "i'm node1_1");
        System.out.println(client.getCreationTime(path));
    }
    
    @Test
    public void getData() {
        connect();
        Object obj = client.readData(changelessPath);
        System.out.println(obj);
    }
    
    @Test
    public void writeData() {
        connect();
        client.writeData(changelessPath, "i'm root");
        Object obj = client.readData(changelessPath);
        System.out.println(obj);
    }
    
    @Test
    public void delete() {
        connect();
        String path = changelessPath + "/node1_temp";
        client.createEphemeral(path, "i'm node1_temp");
        System.out.println(client.getCreationTime(path));
        System.out.println(client.delete(path));
    }
    
    @Test
    public void listen() throws InterruptedException {
        connect();
        String path = changelessPath + "/node1_temp";
        client.createPersistent(path, "i'm node1_temp");
        
        client.subscribeDataChanges(path, new IZkDataListener() {
            
            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                System.out.println("data deleted" + dataPath);
            }
            
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                System.out.println("data handleDataChange" + dataPath);
            }
        });
        
        client.subscribeChildChanges(path, new IZkChildListener() {
            
            @Override
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                System.out.println("变化列表" + JSON.toJSONString(currentChilds));
            }
        });
        
        TimeUnit.SECONDS.sleep(5);
        client.createPersistent(path+"/node2_temp", "i'm node2_temp");
        TimeUnit.SECONDS.sleep(5);
        
        client.createPersistent(path+"/node2_temp2", "i'm node2_temp2");
        
        TimeUnit.SECONDS.sleep(5);
        
        System.out.println(client.deleteRecursive(path));
        TimeUnit.SECONDS.sleep(5);
        
    }
    
    @Test
    public void outAllNode() throws FileNotFoundException {
        connect();
        OutputStream output = new FileOutputStream("d:/test.txt");
        client.showFolders(output);
    }
    
    @Test
    public void getDubboData() throws InterruptedException {
        connect();
        //String path = "/dubbo/com.zbj.newcategory.category.CategoryService/providers";
        //System.out.println(client.exists(path));
        //Object obj = client.readData(path, false);
        //System.out.println(obj);
        String path = "/dubbo/com.zhubajie.fileupload.api.service.ResourceManager/providers";
        System.out.println(client.getChildren(path));
        client.subscribeChildChanges(path, new IZkChildListener() {
            
            @Override
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                System.out.println("child changes" + currentChilds);
            }
        });
        
        client.subscribeDataChanges(path, new IZkDataListener() {
            
            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                System.out.println("data deleted" + dataPath);
            }
            
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                System.out.println("data handleDataChange" + dataPath);
            }
        });
        while(true) {
            Thread.sleep(20000);
        }
    }
    
    @Test
    public void consumerData() {
        connect(testEnvCs);
        String path = "/dubbo/com.zhubajie.fileupload.api.service.ResourceManager/consumers";
        System.out.println(client.getChildren(path));
    }
    
    @Test
    public void createEpm() {
        connect();
        //client.createEphemeral("/node1_emp", "epm");
        System.out.println(client.getCreationTime("/node1_emp"));
        
    }
}
