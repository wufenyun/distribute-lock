/**
 * Package: com.demo.okhttp
 * Description: 
 */
package com.learning.okhttp;

import okhttp3.Response;

/**
 * Description:  
 * Date: 2017年11月27日 上午10:18:21
 * @author wufenyun 
 */
public interface HttpClient {

    Response post(String url,String postBody);
    
    void asyPost(String url,String postBody);
    
}
