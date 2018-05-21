/**
 * Package: com.demo.okhttp
 * Description: 
 */
package com.learning.okhttp;

import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;

/**
 * Description:  
 * Date: 2017年11月27日 上午10:25:54
 * @author wufenyun 
 */
public class SingleHttpClient extends AbstractHttpClient {
    
    public SingleHttpClient() {
        super();
    }

    @Override
    public void init(OkHttpClient client) {
        Builder builder = client.newBuilder();
        ConnectionPool connectionPool = new ConnectionPool(4, 20, TimeUnit.SECONDS);
        builder.connectionPool(connectionPool);
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(1);
        dispatcher.setMaxRequestsPerHost(1);
        builder.dispatcher(dispatcher);
    }

}
