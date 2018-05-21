package com.learning.spi;

import com.alibaba.dubbo.common.extension.ExtensionLoader;

public class SpiDemo {

    public static void main(String[] args) {
        /*Protocol protocol = ProtocoFactory.getProtocol();
        protocol.export();
        protocol.refer();*/
        
        ExtensionLoader<Protocol> protocolLoader = ExtensionLoader.getExtensionLoader(Protocol.class);
        Protocol  protocol=protocolLoader.getExtension("protocol");
        protocol.export();
    }

}
