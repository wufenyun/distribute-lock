/**
 * Package: com.demo.proxy
 * Description: 
 */
package com.learning.proxy;

import org.junit.Test;

/**
 * Description:  
 * Date: 2017年9月27日 下午4:14:36
 * @author wufenyun 
 */
public class BizProxyTest {

    @Test
    public void jdkProxy() {
        BizImpl bizImpl = new BizImpl();
        BizProxy proxy = new BizProxy();
        Biz target = (Biz) proxy.getProxyObject(bizImpl);
        target.doservice("hello");
    }
    
    @Test
    public void cglibProxy() {
        BizImpl bizImpl = new BizImpl();
        BizCglibProxy cp = new BizCglibProxy();
        BizImpl target = (BizImpl) cp.getProxyObject(bizImpl);
        target.finalService("hi");
    }
}
