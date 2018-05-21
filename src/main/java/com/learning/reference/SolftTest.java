/**
 * Package: com.app.reference
 * Description: 
 */
package com.learning.reference;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

import org.junit.Test;

/**
 * Description:  
 * Date: 2017年8月23日 下午7:48:05
 * @author wufenyun 
 */
public class SolftTest {
    
    @Test
    public void test() {
        SoftReference<String> ref = new SoftReference<String>(new String("sdafd"));
        System.out.println(ref.get());
        System.gc();
        System.out.println(ref.get());
    }
    
    @Test
    public void testW() throws InterruptedException {
        WeakReference<String> ref = new WeakReference<String>(new String("sdafd"));
        System.out.println(ref.get());
        System.gc();
        System.gc();
        System.gc();
        System.gc();
        System.out.println(ref.get());
        Thread.sleep(100000);
    }
}
