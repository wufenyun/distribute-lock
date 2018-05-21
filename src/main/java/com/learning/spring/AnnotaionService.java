/**
 * Package: com.wufenyun.app.spring
 * Description: 
 * Date: 2017年5月11日 下午4:04:45
 */

package com.learning.spring;

import com.learning.annotation.service.InterceptorAnnotation;

/**
 * Title: AnnotaionService 
 * Description:  
 * Date: 2017年5月11日 下午4:04:45
 * @author wufenyun 
 */
@InterceptorAnnotation(needLogin = false)
public class AnnotaionService {
	
	public void print() {
		System.out.println("hello");
	}
}
