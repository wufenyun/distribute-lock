/**
 * Package: com.wufenyun.app.annotation.service
 * Description: 
 * Date: 2017年5月10日 下午4:36:42
 */

package com.learning.annotation.service;

/**
 * Title: RequestEvent 
 * Description:  
 * Date: 2017年5月10日 下午4:36:42
 * @author wufenyun 
 */
@Listener(eventType = "RequestEvent", value = "request")
public class RequestEvent {
	
	@Listener(eventType = "RequestEvent", value = "request")
	public void doevent() {
		System.out.println("doevent");
	}
}
