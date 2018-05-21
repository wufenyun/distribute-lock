/**
 * Package: com.demo.jvm
 * Description: 
 */
package com.learning.jvm;

import org.junit.Test;

/**
 * Description:  
 * Date: 2017年9月21日 上午11:37:22
 * @author wufenyun 
 */
public class GCDemo {
    
    private final static int M1= 1*1024*1024;
    
    @Test
    public void gc() {
        Ala a = new Ala();
        Ala b = new Ala();
        a.setInstance(b);
        b.setInstance(a);
        
        a = null;
        b = null;
        System.gc();
    }
    
    @Test
    public void minorgc() {
        byte[] memo1 = new byte[2*M1];
        byte[] memo2 = new byte[2*M1];
        byte[] memo3 = new byte[2*M1];
        byte[] memo4 = new byte[4*M1];
    }
    
    @Test
    public void bigObjectGc() {
        byte[] memo4 = new byte[4*M1];
    }
}

class Ala {
    private int size = 1024*1024;
    private byte[] memo = new byte[size];
    private Ala instance;
    
    public Ala getInstance() {
        return instance;
    }
    public void setInstance(Ala instance) {
        this.instance = instance;
    }
    public byte[] getMemo() {
        return memo;
    }
    public void setMemo(byte[] memo) {
        this.memo = memo;
    }
    
}