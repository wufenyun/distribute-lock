/**
 * Package: com.eagle.distributelock
 * Description: 
 */
package com.eagle.distributelock;

/**
 * Description:  
 * Date: 2018年1月11日 下午2:51:16
 * @author wufenyun 
 */
@SuppressWarnings("serial")
public class LockFailureException extends RuntimeException {
    
    private final static String info = "锁获取失败。";
    
    public LockFailureException(){
        super(info);
    }

    public LockFailureException(String message){
        super(info + message);
    }

    public LockFailureException(String message, Throwable cause){
        super(info + message, cause);
    }
}
