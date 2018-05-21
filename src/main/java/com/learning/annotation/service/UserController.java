/**
 * Package: com.wufenyun.app.annotation.service
 * Description: 
 * Date: 2016年10月9日 上午10:41:44
 */

package com.learning.annotation.service;

/**
 * Title: UserController 
 * Description:  
 * Date: 2016年10月9日 上午10:41:44
 * @author wufenyun 
 */
@InterceptorAnnotation(needLogin=false)
public class UserController {
	
	@InterceptorAnnotation(needLogin=true)
	private String url;
	
	@InterceptorAnnotation(needLogin=true)
	public String userCenter() {
		return null;
	}
}
