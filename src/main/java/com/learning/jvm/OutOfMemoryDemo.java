/**
 * Package: com.demo.jvm
 * Description: 
 */
package com.learning.jvm;

import org.junit.Test;

/**
 * Description:  
 * Date: 2017年9月21日 上午9:21:01
 * @author wufenyun 
 */
public class OutOfMemoryDemo {
    
    @Test
    public void heap() throws InterruptedException {
        int size = 9*1024*1024;
        byte[] memo = new byte[size];
        System.out.println(memo.length);
    }
    
    @Test
    public void stack() {
        StackDemo stackDemo = new StackDemo();
        try {
            stackDemo.count();
        } catch (Error e) {
            System.out.println("stack deep" + stackDemo.getI());
        }
    }
}


class StackDemo {
    
    private int i = 0;
    
    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public void count() {
        i++;
        count();
    }
}
