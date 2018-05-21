/**
 * Package: com.demo.concurrent
 * Description: 
 */
package com.learning.concurrent;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

/**
 * Description:  
 * Date: 2017年9月27日 上午10:05:35
 * @author wufenyun 
 */
public class CompletionServiceTest {
    
    @Test
    public void execute() throws InterruptedException {
        Executor executor = Executors.newFixedThreadPool(10);
        CompletionService<String> cs = new ExecutorCompletionService<>(executor);
        for(int i=0;i<10;i++) {
            cs.submit(new FutureTaskA(""+i));
        }
        try {
            while(true) {
                Future<String> fu = cs.take();
                System.out.println("result: " + fu.get());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
