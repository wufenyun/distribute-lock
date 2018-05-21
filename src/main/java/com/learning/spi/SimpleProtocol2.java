/**
 * Package: com.demo.spi
 * Description: 
 */
package com.learning.spi;

/**
 * Description:  
 * Date: 2018年2月7日 下午2:12:13
 * @author wufenyun 
 */
public class SimpleProtocol2 implements Protocol {

    @Override
    public void refer() {
        System.out.println("SimpleProtocol2 refer");
    }

    @Override
    public void export() {
        System.out.println("SimpleProtocol2 export");
    }

}
