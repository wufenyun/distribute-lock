/**
 * Package: com.demo.datastructure
 * Description: 
 */
package com.learning.datastructure;

/**
 * Description:  
 * Date: 2018年1月8日 下午2:11:47
 * @author wufenyun 
 */
public interface Cache {
    
    int put(Object key,Object object);
    
    Object get(Object key);
    
    void clean(Object key);
    
    void cleanAll();
    
    long getSize();
    
    long getMaxSize();
}
