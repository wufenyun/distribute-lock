package com.learning.netty.p1;

public interface Server {
    
    void send(Object message);

    Object receive();
}
