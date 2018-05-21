/**
 * Package: com.demo.proxy
 * Description: 
 */
package com.learning.proxy;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

/**
 * Description:  
 * Date: 2017年9月27日 下午4:40:25
 * @author wufenyun 
 */
public class BizCglibProxy implements MethodInterceptor {

    private Object target;
    
    public Object getProxyObject(Object target) {
        this.setTarget(target);
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }
    
    @Override
    public Object intercept(Object arg0, Method method, Object[] args, MethodProxy arg3) throws Throwable {
        System.out.println("----预处理操作----");
        Object result = method.invoke(target, args);
        System.out.println("----后续处理操作----");
        return result;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

}
