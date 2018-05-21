/**
 * Package: com.demo.jvm
 * Description: 
 */
package com.learning.jvm;

import org.junit.Test;

/**
 * Description:  
 * Date: 2017年9月12日 下午4:24:13
 * @author wufenyun 
 */
public class Options {
    
    @Test
    public void options() {
        System.out.println("Xmx:" + Runtime.getRuntime().maxMemory());
    }
}
