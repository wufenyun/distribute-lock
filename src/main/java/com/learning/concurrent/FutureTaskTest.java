/**
 * Package: com.app.concurrent
 * Description: 
 */
package com.learning.concurrent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.junit.Test;

/**
 * Description:  
 * Date: 2017年8月3日 下午8:00:03
 * @author wufenyun 
 */
public class FutureTaskTest {

    @Test
    public void test() {
        FutureTask<String> task = new FutureTask<String>(new FutureTaskA("2"));
        new Thread(task).start();
        try {
            System.out.println(task.isDone());
            String result = task.get();
            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void test2() {
        ExecutorService service = Executors.newFixedThreadPool(10);
        FutureTask<String> task = new FutureTask<>(new FutureTaskA("3"));
        service.submit(task);
        try {
            System.out.println(task.isDone());
            String result = task.get();
            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void test3() {
        ExecutorService service = Executors.newFixedThreadPool(10);
        Future<String> task =service.submit(new FutureTaskA("4"));
        try {
            System.out.println(task.isDone());
            String result = task.get();
            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void ttt(){
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        java.util.Date date = new Date(1514273325719l);  
        String str = sdf.format(date);  
        System.out.println(str);
    }
}
