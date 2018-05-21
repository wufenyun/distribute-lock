/**
 * Package: com.demo.spi
 * Description: 
 */
package com.learning.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * Description:  
 * Date: 2018年2月7日 上午11:43:33
 * @author wufenyun 
 */
public class ProtocoFactory {
    
    public static Protocol getProtocol() {
        Protocol protocol = null;
        ServiceLoader<Protocol> serviceLoader = ServiceLoader.load(Protocol.class);
        serviceLoader.forEach((p)->p.export());
        Iterator<Protocol> it = serviceLoader.iterator();
        while(it.hasNext()) {
            protocol = it.next();
            protocol.refer();
        }
        return protocol;
    }
}
