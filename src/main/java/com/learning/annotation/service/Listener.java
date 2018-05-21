/**
 * Package: com.wufenyun.app.annotation.service
 * Description: 
 * Date: 2017年5月10日 下午4:15:43
 */

package com.learning.annotation.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * Title: Fileter 
 * Description:  
 * Date: 2017年5月10日 下午4:15:43
 * @author wufenyun 
 */
@Target({ElementType.TYPE,ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface Listener {
	String value() default "";
	String eventType() default ""; 
}
