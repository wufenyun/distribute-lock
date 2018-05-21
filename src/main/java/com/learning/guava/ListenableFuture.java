/**
 * Package: com.app.guava
 * Description: 
 */
package com.learning.guava;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.junit.Test;

import com.google.common.util.concurrent.ListenableFutureTask;
import com.learning.concurrent.FutureTaskA;

/**
 * Description:  
 * Date: 2017年8月22日 上午11:23:06
 * @author wufenyun 
 */
public class ListenableFuture {
    
    @Test
    public void call() {
        Executor ex = Executors.newCachedThreadPool();
        ListenableFutureTask<String> fu = ListenableFutureTask.create(new FutureTaskA("aa"));
        try {
            ex.execute(fu);
            //fu.addListener(listener, exec);
            System.out.println(fu.get());
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("执行异常");
            e.printStackTrace();
        }
    }
}
