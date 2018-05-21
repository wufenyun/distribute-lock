/**
 * Package: com.demo.concurrent
 * Description: 
 */
package com.learning.concurrent;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;

/**
 * Description:  
 * Date: 2017年12月1日 下午1:57:33
 * @author wufenyun 
 */
public class LockDemo {
    
    @Test
    public void test() {
        /*ReentrantLock lock = new ReentrantLock();
        lock.lock();
        lock.unlock();
        ThreadPoolExecutor t = new ThreadPoolExecutor(0, 0, 0, null, null);
        t.submit(task);*/
        System.out.println(si());
    }
    
    private int si() {
        Short s = new Short((short) 10000);
        return s.intValue();
    }
}
