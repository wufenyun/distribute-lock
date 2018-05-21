package com.learning.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class SimpleEncoder extends MessageToByteEncoder<Request>{

    @Override
    protected void encode(ChannelHandlerContext ctx, Request msg, ByteBuf out) throws Exception {
        if(null == msg) {
            throw new NullPointerException();
        }
        out.writeInt(msg.getLength());
        out.writeLong(msg.getRid());
        out.writeBytes(msg.getData().getBytes());
    }

}
