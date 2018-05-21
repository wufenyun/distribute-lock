/**
 * Package: com.demo.concurrent
 * Description: 
 */
package com.learning.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

/**
 * Description:  
 * Date: 2017年9月20日 下午5:33:41
 * @author wufenyun 
 */
public class ExecutorDemo {
    
    @Test
    public void test() {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        executor.submit(new FutureTaskA("1"));
    }
}
