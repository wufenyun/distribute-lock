/**
 * Package: com.demo.guava
 * Description: 
 */
package com.learning.guava;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * Description:  
 * Date: 2017年10月9日 下午4:54:23
 * @author wufenyun 
 */
@Service
public class LocalCacheService {
    
    private LoadingCache<String, Object> loadingCache = null;
    
    @PostConstruct
    public void init() {
        loadingCache = CacheBuilder.newBuilder().maximumSize(100).build(new CacheLoader<String,Object>() {
            @Override
            public Object load(String key) throws Exception {
                return null;
            }
        });
    }
    
    public void fresh() {
        
    }
}
