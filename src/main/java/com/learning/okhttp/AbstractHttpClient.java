/**
 * Package: com.demo.okhttp
 * Description: 
 */
package com.learning.okhttp;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.ConnectionPool;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Description: Date: 2017年11月27日 上午11:31:15
 * 
 * @author wufenyun
 */
public abstract class AbstractHttpClient implements HttpClient {

    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String ContentTypeHeader = "Content-Type";
    public static final String DefaultMime = "application/octet-stream";
    public static final String JsonMime = "application/json";
    public static final String FormMime = "application/x-www-form-urlencoded";

    private OkHttpClient client = new OkHttpClient();

    public AbstractHttpClient() {
        init(this.client);
    }

    @Override
    public Response post(String url, String postBody) {
        Request request = new Request.Builder().url(url).post(RequestBody.create(MEDIA_TYPE_JSON, postBody)).build();

        try {
            Response response = client.newCall(request).execute();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void asyPost(String url, String postBody) {
        Request request = new Request.Builder().url(url).post(RequestBody.create(MEDIA_TYPE_JSON, postBody)).build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //Resource resource = JSON.parseObject(response.body().string(), Resource.class);
                System.out.println("回调成功");
            }

            @Override
            public void onFailure(Call call, IOException e) {
            }
        });
    }

    public void poolMsg() {
        ConnectionPool connectionPool = this.client.connectionPool();
        // connectionPool.evictAll();
        System.out.println("connectionCount:" + connectionPool.connectionCount() + "   " 
        + "idleConnectionCount:" + connectionPool.idleConnectionCount());
    }

    public void evictAll() {
        ConnectionPool connectionPool = this.client.connectionPool();
        connectionPool.evictAll();
        System.out.println("connectionCount:" + connectionPool.connectionCount());
        System.out.println("idleConnectionCount:" + connectionPool.idleConnectionCount());
    }

    public abstract void init(OkHttpClient client);
}
