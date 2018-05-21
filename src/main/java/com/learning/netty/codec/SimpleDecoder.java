package com.learning.netty.codec;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class SimpleDecoder extends ByteToMessageDecoder {

    private final static int HEADER_LENGTH = 12;
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int readable = in.readableBytes();
        if(readable < HEADER_LENGTH) {
            return;
        }
        
        int dataLength = in.readInt();
        if(readable < (dataLength+HEADER_LENGTH)) {
            in.readerIndex(0);
            return;
        }
        
        long reqId = in.readLong();
        byte[] temp = new byte[(dataLength)];
        in.readBytes(temp);
        
        Request req = new Request();
        req.setRid(reqId);
        req.setLength(dataLength);
        req.setData(new String(temp));
        out.add(req);
    }
    
}
