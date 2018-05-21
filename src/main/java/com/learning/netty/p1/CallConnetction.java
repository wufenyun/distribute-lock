/**
 * Package: com.demo.netty.p1
 * Description: 
 */
package com.learning.netty.p1;

/**
 * Description:  
 * Date: 2017年12月26日 上午10:53:57
 * @author wufenyun 
 */
public interface CallConnetction {
    
    String getServerIp();
    
    Integer getServerPort();
    
    String getClientIp();
    
    Integer getClientPort();
    
    String getService();
    
    long getCallTimeout();
}
