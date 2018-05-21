package com.learning.netty.p2;

import org.junit.Test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

public class ByteBufDemo {
    
    @Test
    public void read() {
        byte[] msg = "hello".getBytes();
        ByteBuf buf = Unpooled.copiedBuffer(msg);
        System.out.println(buf.maxCapacity());
        System.out.println(buf.isReadable());
        System.out.println(buf.readerIndex());
        System.out.println(buf.writerIndex());
        System.out.println((char)buf.readByte());
        System.out.println(buf.readerIndex());
        buf.writeBytes("hei".getBytes());
        //buf.writeDouble(11111);
        System.out.println(buf.capacity());
    }
    
    @Test
    public void test() {
        byte[] msg = "hello".getBytes();
        ByteBuf buf = Unpooled.unmodifiableBuffer(Unpooled.copiedBuffer(msg));
        //buf.writeDouble(111);
        
        System.out.println(ByteBufUtil.hexDump(buf));
        //Channel c
    }
}
