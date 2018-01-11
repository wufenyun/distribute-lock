/**
 * Package: com.eagle.distributelock
 * Description: 
 */
package com.eagle.distributelock;

/**
 * Description:  
 * Date: 2018年1月10日 下午3:10:57
 * @author wufenyun 
 */
public interface Lock {
    
    void lock();
    
    void lock(long timeoutSeconds);
    
    void unlock();
}
