/**
 * Package: com.demo.proxy
 * Description: 
 */
package com.learning.proxy;

/**
 * Description:  
 * Date: 2017年9月27日 下午4:02:44
 * @author wufenyun 
 */
public class BizImpl implements Biz {

    @Override
    public void doservice(String args) {
        System.out.println("doservice is doing:args" + args);
    }
    
    public final void finalService(String args) {
        System.out.println("finalService is doing:args" + args);
    }

}
