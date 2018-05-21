package com.learning.guava;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class LocalCacheTest {

    @Test
    public void test() throws InterruptedException {
        Cache<Object, Object> cache = CacheBuilder.newBuilder().maximumSize(5) // 最多存放十个数据
                .expireAfterWrite(2, TimeUnit.SECONDS) // 缓存200秒
                .recordStats() // 开启 记录状态数据功能
                .build();
        
        cache.put("1", "v1");
        cache.put(2, "v2");
        cache.put(3.0, new Person());
        cache.put(4.01, new Person());
        cache.put(5, "v5");
        System.out.println("size:" + cache.size());
        System.out.println(cache.getIfPresent("1"));
        
        cache.put(6, "v6");
        System.out.println("size:" + cache.size());
        System.out.println(cache.getIfPresent("2"));
        System.out.println(cache.getIfPresent(6));
        
        Thread.sleep(4000);
        System.out.println("size:" + cache.size());
        cache.put(7, "v7");
        System.out.println(cache.getIfPresent(6));
        /*cache.invalidateAll();
        System.out.println("size:" + cache.size());*/
    }
}

class Person {
    private String name;
    private String address;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}