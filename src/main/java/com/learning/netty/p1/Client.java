/**
 * Package: com.demo.netty.p1
 * Description: 
 */
package com.learning.netty.p1;

/**
 * Description:  
 * Date: 2017年12月26日 上午11:02:40
 * @author wufenyun 
 */
public interface Client {
    
    void connect(CallConnetction cc);
    
    void send(Object message);
    
    Object receive();
}
