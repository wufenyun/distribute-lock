/**
 * Package: com.app.concurrent
 * Description: 
 */
package com.learning.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Description:  
 * Date: 2017年8月4日 下午4:44:07
 * @author wufenyun 
 */
public class ArrayBlockQueue<E> implements SimpleBlockQueue<E> {
    
    private E items[];
    private int count;
    private int currentIndex;
    private final ReentrantLock lock;
    private final Condition addCondition;
    private final Condition subCondition;
    
    public ArrayBlockQueue() {
        lock = new ReentrantLock();
        addCondition = lock.newCondition();
        subCondition = lock.newCondition();
    }
    
    @Override
    public void add(E e) {
        lock.lock();
        
        try {
            while(count == items.length) {
                addCondition.await();
            }
            
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    
    private void insert(E e) {
        
    }
}
