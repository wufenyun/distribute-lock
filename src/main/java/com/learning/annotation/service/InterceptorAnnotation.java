/**
 * Package: com.wufenyun.app.annotation.service
 * Description: 
 * Date: 2016年10月8日 下午5:55:54
 */

package com.learning.annotation.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Title: CombileAnnotation 
 * Description:  
 * Date: 2016年10月8日 下午5:55:54
 * @author wufenyun 
 */
@Target({ElementType.TYPE,ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface InterceptorAnnotation {
	
	boolean needLogin();
}
