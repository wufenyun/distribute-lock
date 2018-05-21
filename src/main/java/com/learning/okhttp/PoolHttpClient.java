package com.learning.okhttp;

import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;

/**
 * Description:  
 * Date: 2017年11月27日 上午10:24:19
 * @author wufenyun 
 */
public class PoolHttpClient extends AbstractHttpClient {
    
    public PoolHttpClient() {
        super();
    }
    
    @Override
    public void init(OkHttpClient client) {
        Builder builder = client.newBuilder();
        ConnectionPool connectionPool = new ConnectionPool(20, 5, TimeUnit.MINUTES);
        builder.connectionPool(connectionPool);
    }
    
}
