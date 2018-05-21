/**
 * Package: com.demo.proxy.mapper
 * Description: 
 */
package com.learning.proxy.mapper;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Description:  
 * Date: 2017年11月7日 下午2:50:47
 * @author wufenyun 
 */
public class MapperProxy<T> implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("proxy do");
        return 1;
    }

}
