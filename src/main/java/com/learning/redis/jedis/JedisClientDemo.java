package com.learning.redis.jedis;

import com.zbj.cache.spring.support.redis.cachecloud.exi.cluster.ClusterRedisCacheEntry;

import redis.clients.jedis.JedisCluster;

public class JedisClientDemo {

    public void connect() {
        ClusterRedisCacheEntry clouster = new ClusterRedisCacheEntry();
        clouster.setAppId(1234);
        clouster.afterPropertiesSet();
        JedisCluster jedisClouster = clouster.getRedisCluster();
        
        jedisClouster.hgetAll("aaa");
    }
}
