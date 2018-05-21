package com.learning.netty.codec;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.lang.math.RandomUtils;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ClientEndpoint {
    
    public static void main(String[] args) {
        ClientEndpoint client = new ClientEndpoint();
        client.start();
    }
    
    public void start() {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup worker = new NioEventLoopGroup();
        bootstrap.group(worker)
        .channel(NioSocketChannel.class)
        .option(ChannelOption.TCP_NODELAY, true)
        .option(ChannelOption.SO_SNDBUF, 100)
        .option(ChannelOption.SO_RCVBUF, 100)
        .handler(new ClientChannelInitializer());
        try {
            Channel channel = bootstrap.connect("localhost", 8888).sync().channel();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while(true) {
                String msg = reader.readLine();
                if(msg == null) {
                    continue;
                } else {
                    Request req1 = new Request();
                    req1.setRid(RandomUtils.nextLong());
                    req1.setLength(msg.length());
                    req1.setData(msg);
                    channel.write(req1);
                    
                    Request req2 = new Request();
                    req2.setRid(RandomUtils.nextLong());
                    req2.setLength(msg.length());
                    req2.setData(msg);
                    channel.write(req2);
                    channel.flush();
                }
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        System.out.println("连接成功");
    }

    public static class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            ChannelPipeline pipeline = socketChannel.pipeline();
            //字符串解码和编码
            pipeline.addLast("simpleEncoder",new SimpleEncoder());
            pipeline.addLast("simpleDecoder",new SimpleDecoder());
            //客户端的逻辑
            pipeline.addLast("handler", new ClientHandler());
        }
    }
    
    public static class ClientHandler extends SimpleChannelInboundHandler{

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println("receive message:" + msg);
        }
        
    }
}
