/**
 * Package: com.demo.proxy
 * Description: 
 */
package com.learning.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Description:  
 * Date: 2017年9月27日 下午4:05:13
 * @author wufenyun 
 */
public class BizProxy implements InvocationHandler {

    private Object target;
    
    //通过反射机制，创建一个代理类对象实例并返回。用户进行方法调用时使用
    //创建代理对象时，需要传递该业务类的类加载器（用来获取业务实现类的元数据，在包装方法是调用真正的业务方法）、接口、handler实现类
    public Object getProxyObject(Object target) {
        this.setTarget(target);
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }
    
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
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
